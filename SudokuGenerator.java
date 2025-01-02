import java.util.*;

public class SudokuGenerator {
    private static final int SIZE = 9; // Size of the Sudoku grid (9x9)
    private static final int SUBGRID_SIZE = 3; // Size of the subgrid (3x3)
    private static final int EMPTY = 0; // Empty cell value

    private int[][] board; // Sudoku board

    public SudokuGenerator() {
        board = new int[SIZE][SIZE];
        generateFullBoard();
        removeNumbersForPuzzle();
    }

    // Generate a fully solved Sudoku board
    private boolean generateFullBoard() {
        return fillBoard(0, 0);
    }

    // Recursive backtracking algorithm to fill the Sudoku board
    private boolean fillBoard(int row, int col) {
        if (row == SIZE) {
            return true; // Reached the end of the board
        }

        // int nextRow = (col == SIZE - 1) ? row + 1 : row;
        // int nextCol = (col + 1) % SIZE;

        int nextRow, nextCol;

        if (col == SIZE - 1) {
            nextRow = row + 1;
            nextCol = 0;
        } else {
            nextRow = row;
            nextCol = col + 1;
        }

        // Shuffle numbers 1-9 for randomness
        List<Integer> numbers = generateShuffledNumbers();
        for (int num : numbers) {
            if (isValidPlacement(row, col, num)) {
                board[row][col] = num;
                if (fillBoard(nextRow, nextCol)) {
                    return true;
                }
                board[row][col] = EMPTY; // Backtrack
            }
        }

        return false; // No valid number found, backtrack
    }

    // Check if placing 'num' in (row, col) is valid
    private boolean isValidPlacement(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInSubgrid(row, col, num);
    }

    // Check if 'num' is in the same row
    private boolean isInRow(int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Check if 'num' is in the same column
    private boolean isInCol(int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    // Check if 'num' is in the same 3x3 subgrid
    private boolean isInSubgrid(int row, int col, int num) {
        int subgridRowStart = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int subgridColStart = (col / SUBGRID_SIZE) * SUBGRID_SIZE;
        for (int r = subgridRowStart; r < subgridRowStart + SUBGRID_SIZE; r++) {
            for (int c = subgridColStart; c < subgridColStart + SUBGRID_SIZE; c++) {
                if (board[r][c] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    // Shuffle numbers 1-9 randomly
    private List<Integer> generateShuffledNumbers() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }

    // Remove numbers from the solved board to create a puzzle
    private void removeNumbersForPuzzle() {
        int cellsToRemove = 40; // Adjust this for difficulty
        Random rand = new Random();
        while (cellsToRemove > 0) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (board[row][col] != EMPTY) {
                board[row][col] = EMPTY;
                cellsToRemove--;
            }
        }
    }

    // Print the Sudoku board
    public void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SudokuGenerator generator = new SudokuGenerator();
        System.out.println("Generated Sudoku Puzzle:");
        generator.printBoard();
    }
}

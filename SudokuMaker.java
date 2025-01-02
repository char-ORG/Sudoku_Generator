import java.util.*;

public class SudokuMaker {
    private int[][] board = new int[9][9];

    private ArrayList<Integer> shuffle(){
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=1;i<=9;i++){
            result.add(i);
        }
        Collections.shuffle(result);
        return result;
    }

    private boolean isSafe(int row,int col,int num){
        //row
        for(int i=0;i<9;i++){
            if(board[row][i]==num){
                return false;
            }
        }

        //col
        for(int i=0;i<9;i++){
            if(board[i][col]==num){
                return false;
            }
        }

        //grid
        int rowStart = (row/3)*3;
        int colStart = (col/3)*3;
        for(int i= rowStart;i<rowStart + 3;i++){
            for(int j= colStart;j<colStart + 3;j++){
                if(board[i][j]==num){
                    return false;
                }
            }
        }


        return true;
    }

    private void makeFullBoard(){
        fillBoard(0,0);
        removeFew();
    }

    private boolean fillBoard(int row,int col){

        //base case
        if(row == 8 && col==8){
            ArrayList<Integer> numbers = shuffle();
            for (int num : numbers) {
                if (isSafe(row, col, num)) {
                    board[row][col] = num;
                    return true;
                }
            }
            return false;
        }
        int nextrow = row;
        int nextcol = col;
        if(col==8){
            nextcol = 0;
            nextrow +=1;
        }
        else{
            nextcol +=1;
        }

        ArrayList<Integer> numbers = shuffle();
        for(int num: numbers){
            if(isSafe(row,col,num)){
                board[row][col] = num;
                if(fillBoard(nextrow, nextcol)){
                    return true;
                }
                board[row][col] = 0; 
            }
        }

        return false;
    }

    private void removeFew(){
        Random random = new Random();
        //removing n elements
        int n=30;
        while(n!=0){
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if(board[row][col]!=0){
                board[row][col]=0;
                n--;
            }
        }
    }

    private void printBoard(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }



    public static void main(String[] args) {
        SudokuMaker sudokuMaker = new SudokuMaker();
        sudokuMaker.makeFullBoard();
        sudokuMaker.printBoard();
    }
}
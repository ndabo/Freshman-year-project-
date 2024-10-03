package tetris;
/**
 * this is board class it has all the aspect of the board
 * it has a method that check if its component squares are full
 * a method that clear full rows,
 * a method that update the score
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Board {
    private Square[][] board;
    private Pane gamepane;
    private Color borderColor;
    private int score;

    public Board(Pane gamepane) {
        this.gamepane = new Pane();
        this.board = new Square[Constants.NUM_OF_ROW][Constants.NUM_OF_COL];
        this.borderColor = Color.GRAY;
        this.score = 0;
        for (int row = 0; row < Constants.NUM_OF_ROW; row++) {
            for (int col = 0; col < Constants.NUM_OF_COL; col++) {
                if(row == 0 || row == Constants.NUM_OF_ROW - 1 || col == 0 || col == Constants.NUM_OF_COL - 1){
                this.board[row][col] =
                        new Square(col*Constants.SQUARE_WIDTH, row*Constants.SQUARE_WIDTH, gamepane,this.borderColor);}
                else{
                    this.board[row][col] = new Square(col*Constants.SQUARE_WIDTH, row*Constants.SQUARE_WIDTH, gamepane,Color.BLACK);
                }
            }
        }
    }

    /**
     * check if my board square is empty by checking their colors
     * @param row
     * @param col
     * @return true if board square is black
     */
    public boolean isEmpty(int row, int col) {
        if (row < 0 || row >= Constants.NUM_OF_ROW || col < 0 || col >= Constants.NUM_OF_COL) {
            // Index out of bounds, consider it as not empty
            return false;
        }
        Color color = this.board[row][col].getColor();
        return color.equals(Color.BLACK);
    }

    /**
     * add all the full lines in a list and clear them all
     */
    public void cleanLines() {
        ArrayList<Integer> fullLines = new ArrayList<>();
        // Check for full lines from top to bottom
         for (int row = 1; row < Constants.NUM_OF_ROW - 1; row++) {
            if (this.isLineFull(row)) {
                fullLines.add(row);
            }
        }
        // Clear all full lines
        for (int fullRow : fullLines) {
            this.clear(fullRow);
    }
    }

    /**
     * this method clear a single line
     * @param row
     */
    private void clear(int row) {
        // Shift lines down from the cleared line
        for (int r = row; r > 0; r--) {
            for (int col = 1; col < Constants.NUM_OF_COL - 1; col++) {
                this.board[r][col].setColor(this.board[r - 1][col].getColor());
            }
        }

         //Clear the row
        for (int col = 1; col < Constants.NUM_OF_COL - 1; col++) {
            this.board[1][col].setColor(Color.BLACK);
        }
        this.score+=10; // everytime a line is clear it add ten to the score
        if(this.isTetris()){
            this.score+=100; // add 100 to the score
        }
    }

    /**
     * check if the line if full by check the color of the row
     * @param row
     * @return true if the line is full
     */
    private boolean isLineFull(int row) {
        for (int col = 1; col < Constants.NUM_OF_COL - 1; col++) {
            if (this.isEmpty(row, col) || this.board[row][col].getColor().equals(Color.BLACK)) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if 4 lines is clear at the same time
     * @return true if fall lines is clear and
     */
    private boolean isTetris(){
        int fullRows = 0;
        for (int row = Constants.NUM_OF_ROW - 2; row >= 0; row--) {
            if (this.isLineFull(row)) {
                fullRows++;
            }
        }
        return fullRows >= 4;
    }

    /**
     * getter method
     * @return get the score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * getter method
     * @return the board
     */
    public Square[][] getBoard() {
        return this.board;
    }
}

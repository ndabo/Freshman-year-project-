package tetris;
/**
 * this is my pieces class, it is a one D array that use a two D arrrays to generate different type of pieces
 * it has a method that allows the pieces to move left, down, right and rotate.
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TetrisPieces {
    //private Rectangle piece;
    private Square[] piecesArray;
    private Boolean canRotate;
    private Board myBoard;


    public TetrisPieces(Pane gamePane,Color color,int[][] coords,boolean canRotate, Board board ){
        this.myBoard = board;
        this.piecesArray = new Square[4];
        this.generatePieces(gamePane,coords,color);
        this.canRotate = canRotate;

    }

    /**
     * create pieces based on their coordinates and color
     * @param pane is the pane of the game
     * @param coords the coordinate of the piece
     * @param color the color of the piece
     */
    public void generatePieces(Pane pane, int[][] coords,Color color){
        for(int i=0; i<this.piecesArray.length; i++){
            int x = coords[i][0];
            int y = coords[i][1];
            this.piecesArray[i] = new Square(x,y,pane,color);
        }

    }

    /**
     * check if the piece can move down by checking the color of the board
     * @return
     */
    public boolean canMoveDown() {
        for (Square square : this.piecesArray) {
            int row = (int) (square.getY() / Constants.SQUARE_WIDTH) + 1; // Calculate the next row
            int col = (int) (square.getX() / Constants.SQUARE_WIDTH);

            if (row >= Constants.NUM_OF_ROW || !this.myBoard.isEmpty(row, col)) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if the piece can move left
     * @return true if yes
     */
    public boolean canMoveLeft() {
        for (Square square : this.piecesArray) {
            int row = (int) (square.getY() / Constants.SQUARE_WIDTH);
            int col = (int) (square.getX() / Constants.SQUARE_WIDTH) - 1;

            if (col < 0 || !this.myBoard.isEmpty(row, col)) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if the piece can move right
     * @return true if the piece can move right
     */
    public boolean canMoveRight() {
        for (Square square : this.piecesArray) {
            int row = (int) (square.getY() / Constants.SQUARE_WIDTH);
            int col = (int) (square.getX() / Constants.SQUARE_WIDTH) + 1;

            if (col >= Constants.NUM_OF_COL || !this.myBoard.isEmpty(row, col)) {
                return false;
            }
        }
        return true;
    }

    /**
     * move the piece to the left
     */
    public void moveLeft(){
        if(this.canMoveLeft()){
            for(Square square: this.piecesArray){
                square.moveLeft();}}
    }

    /**
     * move the piece right if possible
     */
    public void moveRight(){
        if(this.canMoveRight()){
        for(Square square: this.piecesArray){
            square.moveRight();}
        }
    }

    /**
     * move the pieces down if possible
     */
    public void moveDown(){
        if(this.canMoveDown()){
        for(Square square: this.piecesArray){
            square.moveDown();}}else {
            this.addSquareToBoard();
        }
    }

    /**
     * drop the piece to the last reachable y pos.
     */
    public void drop(){
        while (this.canMoveDown()){
            this.moveDown();}
        }


    /**
     * check if the piece can rotate or not
      * @return true if the piece new position is within a bound to rotate
     */
    private boolean isValid(){
        if (!this.canRotate) {
            return false;
        }
        int centerX = (int) this.piecesArray[0].getX();
        int centerY = (int) this.piecesArray[0].getY();
        for (Square square : this.piecesArray) {
            int oldX = (int) square.getX();
            int oldY = (int) square.getY();

            int newX = centerX - centerY + oldY;
            int newY = centerY + centerX - oldX;

            if (!this.myBoard.isEmpty(newY / Constants.SQUARE_WIDTH, newX / Constants.SQUARE_WIDTH)) {
                return false;
            }
        }

        return true;
    }

    /**
     * rotate the piece around the first piece.
     */
    public void Rotate(){
        if(this.isValid()){
            //I am rotation my square around the first square
            int centerX = (int) this.piecesArray[0].getX();
            int centerY = (int) this.piecesArray[0].getY();

            for (Square square : this.piecesArray) {
                int oldX = (int) square.getX();
                int oldY = (int) square.getY();

                //new coordinates after rotation
                int newX = centerX - centerY + oldY;
                int newY = centerY + centerX - oldX;
                square.rotate(newX, newY);
            }
        }
    }


    /**
     * add pieces to the bord when they can move anymore by changing the color
     * of the boardSquare to
     */
    public void addSquareToBoard(){
        for(Square square: this.piecesArray) {
            int row = (int) (square.getY() / Constants.SQUARE_WIDTH);
            int col = (int) (square.getX() / Constants.SQUARE_WIDTH);
            if (row >= 0 && row < Constants.NUM_OF_ROW && col >= 0 && col < Constants.NUM_OF_COL) {
                this.myBoard.getBoard()[row][col].setColor(square.getColor());
                square.remove();
            }
        }
        //this.myBoard.cleanLines();
    }
}

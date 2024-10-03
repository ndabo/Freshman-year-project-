package pacman;
/**
 * this is my pacman class, it is made of a circle
 * it has a method that move it and handle the collision
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Pacman {
    private Circle pacman;
    private Board board;
    private Direction directionMoving;
    private Direction nextDirection;
    private double initialX;
    private double initialY;
    public Pacman(Pane myPane, Board myBoard){
        this.board = myBoard;
        this.directionMoving = Direction.LEFT;
        this.nextDirection = Direction.LEFT;
        this.pacman = new Circle(Constants.PACMAN_SIZE, Color.YELLOW);
        myPane.getChildren().add(this.pacman);
        this.initialX = this.pacman.getCenterX();
        this.initialY = this.pacman.getCenterY();
    }


    public void changeDirection(Direction dir){
        this.nextDirection = dir;
    }

    public void move() {
        int currRow = (int) (this.pacman.getCenterY() / Constants.SQ_SIZE);
        int currCol = (int) (this.pacman.getCenterX() / Constants.SQ_SIZE);


        this.directionMoving = this.nextDirection;
        int newRow = this.directionMoving.newRow(currRow);
        int newCol = this.directionMoving.newCol(currCol);
        if (!this.board.isWall(newRow, newCol)) {
            this.pacman.setCenterX(newCol * Constants.SQ_SIZE + Constants.SQ_SIZE / 2);
            this.pacman.setCenterY(newRow * Constants.SQ_SIZE + Constants.SQ_SIZE / 2);
        }
        this.board.handleCollision();
    }

    /**
     * get the current loc of the pac
     * @return
     */
    public BoardCoordinate getCurrentLocation() {
        int currRow = (int) (this.pacman.getCenterY() / Constants.SQ_SIZE);
        int currCol = (int) (this.pacman.getCenterX() / Constants.SQ_SIZE);
        return new BoardCoordinate(currRow, currCol, false);
    }

    public void toFront(){
        this.pacman.toFront();
    }

    public Circle getPacman() {
        return this.pacman;
    }


}

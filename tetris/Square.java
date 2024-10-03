package tetris;
/**
 * this is my square class
 * it is used to create pieces and make the board
 * it has a method that move the square
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square {
    private Rectangle square;
    private Pane myPane;


    public Square(int x, int y, Pane pane,Color color){
        this.square = new Rectangle(x, y, Constants.SQUARE_WIDTH,Constants.SQUARE_WIDTH);
        this.square.setStroke(Color.BLACK);
        this.square.setFill(color);
        this.myPane = pane;
        pane.getChildren().add(this.square);

    }

    public void moveRight(){this.square.setX(this.square.getX()+Constants.SQUARE_WIDTH);}

    public void moveLeft(){this.square.setX(this.square.getX()-Constants.SQUARE_WIDTH);}

    public void moveDown(){this.square.setY(this.square.getY() + Constants.SQUARE_WIDTH);}

    public void rotate(int x, int y){
        this.square.setX(x);
        this.square.setY(y);
    }

    /**
     * getter method
     * @return the x position of the square
     */
    public double getX(){
        return this.square.getX();
    }

    /**
     * getter method
     * @return the y position of the square
     */
    public double getY(){
        return this.square.getY();
    }

    /**
     * getter method
     * @return the color of the square
     */
    public Color getColor(){
        return (Color) this.square.getFill();
    }


    //public Rectangle getSquare(){return this.square;}

    /**
     * remove square from the pane
     */
    public void remove(){
        this.myPane.getChildren().remove(this.square);
    }

    /**
     * setter method that set the color of the square
     * @param color
     */
    public void setColor(Color color){
         this.square.setFill(color);
    }

}

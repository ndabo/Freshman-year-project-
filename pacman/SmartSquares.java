package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class SmartSquares {
    private Rectangle square;
    private ArrayList <Collidable> items;
    private Pane pane;
    private Color color;
    public SmartSquares(Pane myPane, Boolean isWall){
        this.pane = myPane;
        this.items = new ArrayList<>();
        this.square = new Rectangle(Constants.SQ_SIZE,Constants.SQ_SIZE);
        if(isWall){
            this.color = Color.DARKBLUE;
        }
        else{
            this.color = Color.BLACK;
        }
        this.reset();
        myPane.getChildren().add(this.square);
    }

    public void reset(){
        this.square.setFill(this.color);
    }
    public void addItems(Collidable item){
        this.items.add(item);
    }
    public boolean isEmpty(){
        return this.square.getFill().equals(Color.BLACK) && this.items == null;
    }
    public Rectangle getSquare() {
        return this.square;
    }

    public ArrayList<Collidable> getItems() {
        return this.items;
    }

    public int getRow(){
        return (int )this.square.getX();
    }

    public int getCol(){
        return (int) this.square.getY();
    }

}

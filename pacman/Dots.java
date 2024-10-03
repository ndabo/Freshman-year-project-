package pacman;
/**
 * this is my dot class, it is made of a circle it has a method that handle collision
 */

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dots implements Collidable {
    private Circle dots;
    private Pane pane;
    private Board board;

    public Dots(Pane myPane,int x, int y,Board myboard){
        this.dots = new Circle(Constants.DOT_SIZE, Color.WHITE);
        this.dots.setCenterX(x*Constants.SQ_SIZE + 10);
        this.dots.setCenterY(y * Constants.SQ_SIZE+ 10);
        this.pane = myPane;
        this.board = myboard;
        this.pane.getChildren().add(this.dots);
    }

    @Override
    public void onCollision() {
        this.pane.getChildren().remove(this.dots);
        this.board.getItems().remove(this);
    }

    @Override
    public int score() {
        return 10;
    }

    @Override
    public Bounds getBoundsInParent() {
        return this.dots.getBoundsInParent();
    }

}

package pacman;
/**
 * this is my energizer class. it is made of a circle and has methode that handle collision
 */

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Energizer implements Collidable{
    private Circle energizer;
    private Pane pane;
    private Board board;
    public Energizer(Pane myPane, int x, int y, Board myboard){
        this.pane = myPane;
        this.board = myboard;
        this.energizer = new Circle(Constants.ENERGIZER_SIZE, Color.WHITE);
        this.energizer.setCenterX(x*Constants.SQ_SIZE + 10);
        this.energizer.setCenterY(y*Constants.SQ_SIZE + 10);
        myPane.getChildren().add(this.energizer);
    }

    /**
     * this method handle what happen when the pac collide with the energizer
     */
    @Override
    public void onCollision() {
        this.pane.getChildren().remove(this.energizer);
        for (Ghosts ghost: this.board.getGhostsArray()){
            ghost.frightenMode();
            this.board.getItems().remove(this);
        }
    }

    @Override
    public int score() {
        return 100;
    }

    /**
     * @return Bounds of the circle
     */
    @Override
    public Bounds getBoundsInParent() {
        return this.energizer.getBoundsInParent();
    }
}


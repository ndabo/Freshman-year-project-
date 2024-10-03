package pacman;
/**
 * this is my pane organizer it handles the graphic of the game
 */

import javafx.scene.layout.BorderPane;


public class PaneOrganizer {
    private BorderPane root;


    public PaneOrganizer(){
        this.root = new BorderPane();
        this.root.setStyle(Constants.SCENE_COLOR);
        new Board(this.root);
    }

    public BorderPane getRoot() {
        return this.root;
    }
}

package tetris;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * this is my graphical class, it contains the pain for my game
 */
public class PaneOrganizer {
    private BorderPane root;
    private Game game;
    public PaneOrganizer(){
        this.root = new BorderPane();
        this.root.setStyle(Constants.WINDOW_BACKGROUND);
        this.game = new Game(this.root);
        this.createButtonPane();


    }

    /**
     * this is my button pane it has the quit and the reset button
     */
    private void createButtonPane(){
        HBox hbox = new HBox();
        hbox.setPrefSize(Constants.BTN_PANE_WIDTH, Constants.BTN_PANE_HEIGHT);
        hbox.setStyle(Constants.BTN_PANE_COLOR);
        Button btn = new Button("Quit");
        Button btn2 = new Button("Reset");
        btn.setOnAction((ActionEvent e) -> System.exit(0));
        btn2.setOnAction((ActionEvent e)-> this.reset());
        hbox.getChildren().addAll(btn,btn2);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        this.root.setBottom(hbox);
        hbox.setFocusTraversable(false);
        btn2.setFocusTraversable(false);
        btn.setFocusTraversable(false);
    }

    /**
     * reset the game by clearing the root and creating a new game
     */
    public void reset(){
        this.root.getChildren().clear();
        this.createButtonPane();
        new Game(this.root);

    }
    public BorderPane getRoot(){
        return this.root;
    }

}

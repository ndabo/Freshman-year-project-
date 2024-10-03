package pacman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is the App class where your Pacman game will start.
  * The main method of this application calls the start method. You
  * will need to fill in the start method to instantiate your game.
  *
  * Class comments here... 
  *
  */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        stage.setTitle("PACMAN");
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(),Constants.SCENE_WIDTH,Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}

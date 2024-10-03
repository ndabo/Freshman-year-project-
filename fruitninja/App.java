package fruitninja;

import cs15.prj.fruitNinjaSupport.CS15FruitNinjaFrame;
import cs15.prj.fruitNinjaSupport.CS15ScoreController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Here is the student-side of the Fruit Ninja implementation. Support code
 * should be pretty well documented in the current version
 *
 * @version beta
 */

public class App extends Application {

    /**
     * Student needs to add their top level class to the frame to emphasize that
     * in order for things to show up, you need to add things to a container
     */
    @Override
    public void start(Stage myStage) {
        CS15FruitNinjaFrame frame = new CS15FruitNinjaFrame(myStage);
        // Your code goes here!
        CS15ScoreController scoreController = new CS15ScoreController();
        Game game = new Game(scoreController);
        frame.addGame(game);
        frame.setTop(scoreController);
    }


    /*
     * This is called a mainline. This is the code that is executed when you run
     * the program from the command line. The outer graphical frame of
     * FruitNinja is a special type of graphical object that does not get garbage
     * collected until you close the application. This helps explain why the
     * application does not go out of scope!
     *
     * Calling launch below will cause the Fruit Ninja Frame to appear.
     *
     * Please, DO NOT CHANGE THIS CODE!
     *
     */
    public static void main(String[] argv) {
        launch(argv);
    }
}

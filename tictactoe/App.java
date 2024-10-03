package tictactoe;

import cs15.prj.ticTacToeSupport.CS15TicTacToeBoard;
import cs15.prj.ticTacToeSupport.CS15TicTacToeController;
import cs15.prj.ticTacToeSupport.CS15TicTacToeFrame;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    /**
     * Welcome to your Tic Tac Toe App class.
     *
     * After writing your top-level class, you should instantiate
     * it in the "start" method. Don't forget to add it to the frame!
     */
    @Override
    public void start(Stage stage) {
        CS15TicTacToeFrame frame = new CS15TicTacToeFrame(stage);
        TicTacToeGame ticTacToeGame = new TicTacToeGame();
        frame.addGame(ticTacToeGame);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

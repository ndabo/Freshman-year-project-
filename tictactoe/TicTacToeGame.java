package tictactoe;

import cs15.prj.ticTacToeSupport.CS15TicTacToeBoard;
import cs15.prj.ticTacToeSupport.CS15TicTacToeController;
import cs15.prj.ticTacToeSupport.CS15TicTacToeGame;
import javafx.scene.paint.Color;

/**
 * this is the TicTacToeGame class. it implements the CS15TicTacToe interface
 *it makes the game show in the CS15TicTacToeFrame. it has a controller,a board
 * two players and also has all the possible game scenario method
 */
public class TicTacToeGame implements CS15TicTacToeGame {
    //All my instance variables
    private CS15TicTacToeController controller;
    private CS15TicTacToeBoard board;
    private TicTacToePlayer player1;
    private TicTacToePlayer player2;

    public TicTacToeGame(){
        this.controller = new CS15TicTacToeController();
        this.board = new CS15TicTacToeBoard();
        this.controller.setControllerMessage("Select a square to start playing!");
        this.player1 = new TicTacToePlayer("X",this.board,this.controller);
        this.player2 = new TicTacToePlayer("O",this.board,this.controller);
        this.controller.addPlayer(this.player1);
        this.controller.addPlayer(this.player2);
    }

    /**
     * this a methode is called automatically when a player choose a square that is
     * already taken. it uses two parameters that represent the col and the row of
     * the already taken square
     * @param i
     * @param i1
     */
    @Override
    public void invalidSquareChosen(int i, int i1) {
        this.board.squareAt(i,i1).flashColor(Color.RED);
        //the line above indicate the taken square and highlight the background with the red color
        this.controller.setControllerMessage("Square chosen has already been taken! Try again.");
    }

    /**
     * this method get the winning player by display a message when
     * a player win and also highlight the winning Squares.
     */
    @Override
    public void playerWins() {
        TicTacToePlayer player = this.controller.getWinningPlayer();
        String mySymbol = player.getMySymbol();
        this.controller.setControllerMessage("Player " + mySymbol + " wins!");
        this.board.highlightWinningSquares(Color.GREEN);
    }

    /**
     * this method automatically restart the game when you press on the restart button.
     * it clears the board completely and display a message in the controller
     */
    @Override
    public void restartGame() {
        this.controller.playAgain();
        this.board.clearHighlights();
        this.board.clearSymbols();
        this.controller.setControllerMessage("Select a square to start playing!");
    }

    /**
     * this method display a message in the controller when all squares are taken and
     * there's no winner.
     */
    @Override
    public void noWinner() {
        this.controller.setControllerMessage("it's a tie!");
    }
}

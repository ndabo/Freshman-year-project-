package tictactoe;

import cs15.prj.ticTacToeSupport.CS15TicTacToeBoard;
import cs15.prj.ticTacToeSupport.CS15TicTacToeController;
import cs15.prj.ticTacToeSupport.CS15TicTacToePlayer;
import cs15.prj.ticTacToeSupport.CS15TicTacToeSquare;

/**
 * this is the player class. it implements the CS15TicTacToePlayer interface
 * it contain reference to a player, the board and controller they're going to use
 * the square they're able to chose and the symbol each will be able to use
 */
public class TicTacToePlayer implements CS15TicTacToePlayer {
    private CS15TicTacToeBoard myBoard;
    private CS15TicTacToeController myController;
    private String mySymbol;

    //this constructor let th player know about the symbol, the board and the controller of the game
    public TicTacToePlayer(String symbol, CS15TicTacToeBoard myBoard, CS15TicTacToeController myController) {
        this.myBoard = myBoard;
        this.myController = myController;
        this.mySymbol = symbol;
    }

    /**
     * this method allow the player to choose a square on the board,
     * let the second player know that the first player turn is over and
     * also alternate player turn
     * @param i
     * @param i1
     */
    @Override
    public void selectSquare(int i, int i1) {
        this.myBoard.squareAt(i,i1).markSquare(this.mySymbol);
        this.myController.setControllerMessage("Player " + this.mySymbol + " finished their turn!");
        this.myController.finishedTurn();
        //the line above Ends the current player's turn and moves to the other player's turn.
    }

    /**
     * this is an Accessor of the player symbol
     * it help the player to know about each other symbol
     * @return
     */
    public String getMySymbol(){
        return this.mySymbol;
    }
}

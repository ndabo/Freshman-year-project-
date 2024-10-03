package tetris;

/**
 * this is my game class, it has a board and pieces generated randomly
 */

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;



public class Game {
    private Board board;
    private Pane gamePane;
    private Timeline timeline;
    private TetrisPieces piece;
    private Boolean isPaused;
    private Boolean gameOver;
    private Label gameOverLabel;
    private Label scoreLabel;

    public Game(BorderPane myPane) {
        this.gamePane = new Pane();
        this.board = new Board(this.gamePane);
        myPane.setCenter(this.gamePane);
        this.piece = null;
        this.isPaused = false;
        this.gameOver = false;
        this.timeline = new Timeline();
        this.setUpTimeline();
        this.randomPieces();
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPresses(e));
        this.gamePane.setFocusTraversable(true);
        this.createScoreLabel();
    }

    public void setUpTimeline() {
        KeyFrame kf2 = new KeyFrame(Duration.seconds(0.5),
                (ActionEvent e) -> {
                    if (!this.isPaused) {
                        this.piece.moveDown();
                    }
                    this.generatePiece();
                    this.updateScore();
                });
        this.timeline.getKeyFrames().addAll(kf2);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * this toggle pausing in the game
     */
    private void pause() {
        this.isPaused = !this.isPaused;
        if (this.isPaused) {
            this.timeline.stop();
        } else {
            this.timeline.play();
        }
    }

    /**
     * handle user input
     * @param e
     */
    public void handleKeyPresses(KeyEvent e) {
        // check if the game is not pause or over before handling user input
        if(!this.isGameOver()){
            switch (e.getCode()) {
                case LEFT:
                    this.piece.moveLeft();
                    break;
                case RIGHT:
                    this.piece.moveRight();
                    break;
                case DOWN:
                    this.piece.moveDown();
                    break;
                case UP:
                    this.piece.Rotate();
                    break;
                case SPACE:
                    this.piece.drop();
                    break;
                case P:
                    this.pause();
                    break;
                default:
                    break;
            }
            e.consume();
        }
    }

    /**
     * create different pieces randomly based on their coordinate
     * @return  shape of piece
     */
    public TetrisPieces randomPieces() {
        int random = (int) (Math.random() * 7);
        switch (random) {
            case 0:
                this.piece = new TetrisPieces(this.gamePane, Color.DARKBLUE, Constants.C2_PIECE_COORDS, true, this.board);
                break;
            case 1:
                this.piece = new TetrisPieces(this.gamePane, Color.WHITE, Constants.C_PIECE_COORDS, true, this.board);
                break;
            case 2:
                this.piece = new TetrisPieces(this.gamePane, Color.GREEN, Constants.L2_PIECE_COORDS, true, this.board);
                break;
            case 3:
                this.piece = new TetrisPieces(this.gamePane, Color.YELLOW, Constants.L_PIECE_COORDS, true, this.board);
                break;
            case 4:
                this.piece = new TetrisPieces(this.gamePane, Color.ORANGE, Constants.T_PIECE_COORDS, true, this.board);
                break;
            case 5:
                this.piece = new TetrisPieces(this.gamePane, Color.PURPLE, Constants.SQ_PIECE_COORDS, false, this.board);
                break;
            case 6:
                this.piece = new TetrisPieces(this.gamePane, Color.RED, Constants.I_PIECE_COORDS, true, this.board);
                break;
            default:
                break;
        }

        return this.piece;
    }

    /**
     * generate new pieces everytime the current piece can move anymore
     */
    private void generatePiece() {
        if (!this.piece.canMoveDown()) {
            this.piece.addSquareToBoard();
            if (this.isGameOver()) {
                this.endGame();
            }
            this.piece = this.randomPieces();
        }
        this.board.cleanLines();
    }


    /**
     * tell us if the game is over or not by checking if the top row is occupied
     * @return true if the game is over
     */
    private boolean isGameOver() {
        for (int col = 1; col < Constants.NUM_OF_COL - 1; col++) {
            if (!this.board.isEmpty(1, col)) {
                return true;  // The top row is occupied, game over
            }
        }
        return false;  // The top row is empty, game continues
    }

    /**
     * this method end the game, by stop the timeline
     */
    private void endGame() {
        this.gameOver = true;
        this.timeline.stop();
        this.gameOverPane();
    }

    /**
     * create gameOver label and add to the gamepane
     */
    private void gameOverPane(){
        this.gameOverLabel = new Label("Game over ");
        this.gameOverLabel.relocate(1,1);
        this.gamePane.getChildren().add(this.gameOverLabel);
        this.gameOverLabel.setFont(Font.font(20));
    }

    /**
     * create the score label and add it to the gamePane
     */
    private void createScoreLabel(){
        this.scoreLabel = new Label("score: ");
        this.scoreLabel.relocate(Constants.SCENE_WIDTH - 60, 1);
        this.scoreLabel.setFont(Font.font(10));
        this.gamePane.getChildren().add(this.scoreLabel);
    }

    /**
     * update the score
     */
    private void updateScore(){
        this.scoreLabel.setText("score: " + this.board.getScore());
    }

}
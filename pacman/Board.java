package pacman;
/**
 * this is my board class
 * it handle all  the logical aspect of the game
 */

import cs15.fnl.pacmanSupport.CS15SquareType;
import cs15.fnl.pacmanSupport.CS15SupportMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private SmartSquares[][] board;
    private Pane gamePane;
    private Pacman pac;
    private int lives;
    private int releaseCounter;
    private int frightenCounter;
    private ArrayList <Collidable> items;
    private Label livesLabel;
    private Ghosts blinky;
    private Ghosts pinky;
    private Ghosts clyde;
    private Ghosts inky;
    private Queue<Ghosts> ghostsPen;
    private int score;
    private Timeline timeline;
    private Ghosts[] ghostsArray;
    private int counter;
    private Label scoreLabel;
    private Label gameOverLabel;

    public Board(BorderPane myPane){
        this.gamePane = new Pane();
        this.ghostsArray = new Ghosts[4];
        this.items = new ArrayList<>();
        this.score = 0;
        this.lives = 3;
        this.frightenCounter = 0;
        this.releaseCounter = 0;
        this.timeline = new Timeline();
        this.ghostsPen = new LinkedList<>();
        CS15SquareType[][] supportMap = CS15SupportMap.getSupportMap();
        this.board = new SmartSquares[Constants.NUM_row][Constants.NUM_COL];
        for (int i = 0; i < supportMap.length; i++) {
            for (int j = 0; j < supportMap[i].length; j++) {
                CS15SquareType currentSquareType = supportMap[i][j];
                this.board[i][j] = this.generateSquare(currentSquareType,j,i);
                this.board[i][j].getSquare().setX(j*Constants.SQ_SIZE);
                this.board[i][j].getSquare().setY(i*Constants.SQ_SIZE);
            }
        }
        this.pac.toFront();
        for(Ghosts phantom: this.ghostsArray){
            phantom.toFront();
        }
        myPane.setCenter(this.gamePane);
        this.createButtonPane(myPane);
        this.startGame();
    }

    /**
     * this is helper method
     * it generates the map and has all the key component of the game
     * @param squareType
     * @param x
     * @param y
     * @return
     */
    private SmartSquares generateSquare(CS15SquareType squareType,int x,int y){
        switch (squareType){
            case DOT:
                SmartSquares dotSquares = new SmartSquares(this.gamePane,false);
                Dots dots = new Dots(this.gamePane,x,y,this);
                this.items.add(dots);
                return dotSquares;
            case FREE:
                return new SmartSquares(this.gamePane,false);
            case WALL:
                return new SmartSquares(this.gamePane,true);
            case ENERGIZER:
                SmartSquares energizerSquare = new SmartSquares(this.gamePane,false);
                Energizer energizer = new Energizer(this.gamePane,x,y,this);
                this.items.add(energizer);
                return energizerSquare;

            case GHOST_START_LOCATION:
                SmartSquares ghostsLocation = new SmartSquares(this.gamePane,false);
                this.blinky = new Ghosts(Color.RED, this.gamePane,x,y-2,this);
                this.items.add(this.blinky);
                this.pinky = new Ghosts(Color.LIGHTPINK, this.gamePane,x-1,y,this);
                this.inky = new Ghosts(Color.BLUE, this.gamePane,x,y,this);
                this.items.add(this.pinky);
                this.items.add(this.inky);
                this.clyde = new Ghosts(Color.ORANGE, this.gamePane,x+1,y,this);
                this.items.add(this.clyde);
                this.ghostsArray[0] = this.inky;
                this.ghostsArray[1]= this.clyde;
                this.ghostsArray[2]= this.pinky;
                this.ghostsArray[3]= this.blinky;
                //red ghost in the wrong starting location
                for(Ghosts ghost: this.ghostsArray){
                    this.ghostsPen.add(ghost);
                }
                this.ghostsPen.remove(this.blinky);
                return ghostsLocation;
            case PACMAN_START_LOCATION:
                SmartSquares pacLocation = new SmartSquares(this.gamePane,false);
                this.pac = new Pacman(this.gamePane,this);
                this.pac.getPacman().setCenterY(y*Constants.SQ_SIZE + 10);
                this.pac.getPacman().setCenterX(x*Constants.SQ_SIZE + 10);
                return pacLocation;
            default:
                break;
        }
        return null;
    }

    /**
     * this method start the game using the timeline and keyframse
     */
    private void startGame(){
        this.gamePane.setOnKeyPressed((KeyEvent e)-> this.handleKeyPresses(e.getCode()));
        KeyFrame kf2 = new KeyFrame(Duration.millis(300),
                (ActionEvent e) -> this.update());
        this.timeline.getKeyFrames().add(kf2);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
        this.gamePane.setFocusTraversable(true);
    }

    /**
     * this method handle a lot of logique of the game
     * starting from handling when the ghost are supposed to be in chase mode, scartermode
     *when can the ghost can go out of the pen to handling the colision, updating the score, lives and
     * ending the game
     */
    private void update(){
        boolean fright = this.ghostsArray[0].getIsFrighten();
        this.pac.move();
        this.counter++;
        this.releaseCounter++;
        if (!fright) {
            for (Ghosts ghosts : this.ghostsArray) {
                if (this.counter < 20) {
                    ghosts.Chase();
                } else {
                    ghosts.scatterMode();
                }

                if (this.counter == 40) {
                    this.counter = 0;
                }

            }
        } else {
            this.frightenCounter++;
            for (Ghosts ghosts : this.ghostsArray) {
                Direction random = ghosts.getRandomDirection();
                ghosts.move(random);
            }

            if(this.frightenCounter >= 14){
                for (Ghosts ghosts : this.ghostsArray) {
                    ghosts.setIsFrighten(false);
                    ghosts.getGhost().setFill(ghosts.getOriginalColor());
                }
                this.frightenCounter = 0;
            }
        }
        if(this.releaseCounter % 7 == 0){
            this.releaseGhost();
            this.releaseCounter = 0;
        }

        this.handleCollision();
        this.updateScore();
        this.endGame();
    }

    /**
     * this method release the ghost by order of first in first off
     * it uses a queu to do so
     */
    public void releaseGhost(){
        if (!this.ghostsPen.isEmpty()) {
            Ghosts releasedGhost = this.ghostsPen.poll();
            releasedGhost.releasefromPen();
        }
    }


    public void resetPositions() {
        this.pac.getPacman().setCenterX(12*Constants.SQ_SIZE -10);
        this.pac.getPacman().setCenterY(18*Constants.SQ_SIZE -10);

        for (Ghosts ghost : this.ghostsArray) {
            ghost.resetPos();
        }
    }

    /**
     * this method handle the user input
     * @param code
     */
    private void handleKeyPresses(KeyCode code) {
        switch (code) {
            case UP:
                this.pac.changeDirection(Direction.UP);
                break;
            case DOWN:
                this.pac.changeDirection(Direction.DOWN);
                break;
            case LEFT:
                this.pac.changeDirection(Direction.LEFT);
                break;
            case RIGHT:
                this.pac.changeDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
    }

    /**
     * this method check is the smartsquare is a wall by checking it's collor
     * @param row
     * @param col
     * @return true is the color is dark blue and false when it's black
     */
    public boolean isWall(int row, int col){
        SmartSquares square = this.getSquareAt(row, col);
        return square != null && square.getSquare().getFill().equals(Color.DARKBLUE);
    }

    /**
     * this method handle the collision between the collidable and pacman
     */
    public void handleCollision(){
            for (int i = 0; i < this.items.size(); i++) {
                Collidable collidable = this.items.get(i);
                if (this.pac.getPacman().getBoundsInParent().intersects(collidable.getBoundsInParent())) {
                    collidable.onCollision();
                    this.score += collidable.score();
                    i++;
                }
            }
        }

    /**
     * this is my bottom pane, it has the score label, the live label and the quit button.
     * it takes in a Borderpane
      * @param root
     */
    private void createButtonPane(BorderPane root){
        HBox buttonPane = new HBox();
        this.scoreLabel = new Label("score: " );
        this.livesLabel = new Label("lives: ");
        Button btn1 = new Button("Quit");
        btn1.setAlignment(Pos.BOTTOM_RIGHT);
        btn1.setOnAction(actionEvent -> System.exit(0));
        this.scoreLabel.setAlignment(Pos.CENTER);
        this.livesLabel.setAlignment(Pos.BOTTOM_LEFT);
        btn1.setFocusTraversable(false);
        buttonPane.setFocusTraversable(false);
        buttonPane.setSpacing(50);
        buttonPane.getChildren().addAll(btn1,this.scoreLabel,this.livesLabel);
        buttonPane.setStyle( "-fx-background-color: #88D1F1");
        root.setBottom(buttonPane);
    }

    /**
     * this method update the score and the lives
     */
    public void updateScore(){
        this.scoreLabel.setText("score: " + this.score);
        this.livesLabel.setText("Lives " + this.lives);
    }

    /**
     * this method get the square at a current position
     * @param row
     * @param col
     * @return
     */
    public SmartSquares getSquareAt(int row, int col){
        if(row >= 0 && row < 23 && col >= 0 && col < 23){
            return this.board[row][col];
        }
        return null;
    }

    /**
     * this my game over pane, it is called when the pacman lives is equal to zero
     */
    private void gameOverPane(){
        this.gameOverLabel = new Label("Game over ");
        this.gameOverLabel.setStyle(String.valueOf(Color.WHITE));
        this.gameOverLabel.relocate(200,200);
        this.gamePane.getChildren().add(this.gameOverLabel);
        this.gameOverLabel.setFont(Font.font(20));
    }

    private Boolean isGameWon() {
        for (Collidable item : this.items) {
            if (item instanceof Dots || item instanceof Energizer) {
                return false;
            }
        }
        return true;
    }

    private void gameWinnerPane(){
        this.gameOverLabel = new Label("You won");
        this.gamePane.setStyle(String.valueOf(Color.WHITE));
        this.gameOverLabel.relocate(200,200);
        this.gamePane.getChildren().add(this.gameOverLabel);
        this.gameOverLabel.setFont(Font.font(20));
    }


    /**
     * this method end the game when the lives is equal to zero
     */
    private void endGame(){
        if(this.lives == 0){
            this.gameOverPane();
            this.timeline.stop();
        }
        if(this.isGameWon()){
            this.resetPositions();
            this.gameWinnerPane();
            this.timeline.stop();
        }
    }

    /**
     * getter mehtod
     * @return
     */
    public ArrayList<Collidable> getItems() {
        return this.items;
    }

    /**
     * getter method
     * @return
     */
    public Pacman getPac() {
        return this.pac;
    }

    /**
     * getter method
     * @return
     */
    public Ghosts[] getGhostsArray() {
        return this.ghostsArray;
    }

    /**
     * setter method
     * @param lives
     */
    public void setLives(int lives ) {
        this.lives = lives;
    }

    /**
     * getter method
     * @return
     */
    public Queue getGhostsPen() {
        return this.ghostsPen;
    }

}

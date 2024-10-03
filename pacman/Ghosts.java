package pacman;
/**
 * this is ghost class. it is made of a rectangle
 * it handles the ghost behavior and the lives.
 */

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ghosts implements Collidable{
    private Rectangle ghost;
    private int currentRow;
    private int currentCol;
    private int counter;
    private int initialCol;
    private int initialRow;
    private Direction[][] directions;
    private Board myBoard;
    private Queue<BoardCoordinate> queue;
    private Direction directionMoving;
    private Boolean isFrighten;
    private int lives;
    private BoardCoordinate target;
    private Color originalColor;


    public Ghosts(Color color, Pane myPane, int x, int y, Board board){
        this.myBoard = board;
        this.ghost = new Rectangle(Constants.GHOSTS_SIZE,Constants.GHOSTS_SIZE);
        this.originalColor = color;
        this.ghost.setFill(this.originalColor);
        myPane.getChildren().add(this.ghost);
        this.ghost.setY(y*Constants.SQ_SIZE);
        this.ghost.setX(x*Constants.SQ_SIZE);
        this.initialCol = x;
        this.initialRow = y;
        this.currentRow = y;
        this.currentCol = x;
        this.directionMoving = Direction.UP;
        this.isFrighten = false;
        this.lives = 3;

    }

    /**
     * handle the collision when collide with pacman
     */
    @Override
    public void onCollision() {
        if(this.isFrighten){
            this.myBoard.getGhostsPen().add(this);
            this.resetPosFrightenMode();
        }else{
            this.lives--;
            this.myBoard.setLives(this.lives);
            this.myBoard.getGhostsPen().add(this);
            this.myBoard.resetPositions();
            this.directionMoving = Direction.LEFT;
        }
    }

    @Override
    public int score() {
        if(this.isFrighten){
            return 200;}
        return 0;
    }

    @Override
    public Bounds getBoundsInParent() {
        return this.ghost.getBoundsInParent();
    }

    private void setTarget(BoardCoordinate myTarget){
        this.target = myTarget;
    }

    /**
     * this method put the ghost in front of the sqare
     */
    public void toFront(){
        this.ghost.toFront();
    }

    /**
     * search all the squares neighboring you, then expand and search the squares neighboring your neighbors,
     * then expand again, and so on, until the entire maze has been searched
     * @param target
     * @return a direction that the ghost will move to reach its target
     */
    public Direction mybfs(BoardCoordinate target){
        this.queue = new LinkedList<>();
        this.directions = new Direction[Constants.NUM_row ][Constants.NUM_COL];//store directions for each board coordinate.
        Direction dir = null;
        Double minDist = Double.POSITIVE_INFINITY;
        BoardCoordinate initialPos = new BoardCoordinate(this.currentRow,this.currentCol,false);
        this.addCheckVN(this.queue,this.directions,initialPos,true);
        while(!this.queue.isEmpty()){
            initialPos = this.queue.remove();
            Direction currDir = this.directions[initialPos.getRow()][initialPos.getColumn()];
            double distance = Math.hypot((initialPos.getColumn() -target.getColumn()),(initialPos.getRow()-target.getRow()));
            if (distance<minDist){
                minDist = distance;
                dir = currDir;
            }
            this.addCheckVN(this.queue,this.directions,initialPos,false);
        }
        this.directionMoving = dir;
        return dir;
    }


    /**
     * this method check the valid neighbor and add them to the Queeu
     * @param Q
     * @param map
     * @param point
     * @param isFirst
     */
    public void addCheckVN(Queue<BoardCoordinate> Q, Direction[][] map,BoardCoordinate point, Boolean isFirst){
        Direction oppositeD = this.directionMoving.opposite();
        Direction curDir = map[point.getRow()][point.getColumn()];

        for(Direction dir: Direction.values()){

                if(isFirst && dir == oppositeD){
                    continue;
                 } else{
                    int newRow = dir.newRow(point.getRow());
                    int newCol = dir.newCol(point.getColumn());

                    if(map[newRow][newCol]==null && this.isValid(newRow,newCol)){
                        Q.add(new BoardCoordinate(newRow,newCol,false));
                        if(isFirst){
                            map[newRow][newCol] =dir;
                        }else{
                            map[newRow][newCol] = curDir;
                        }
                    }
                }
            }
        }



    /**
     * helper method that check is the move is valid or not
     * @param row
     * @param col
     * @return
     */
    private boolean isValid(int row, int col){
        return row >= 0 && row < Constants.NUM_row &&
                col >= 0 && col < Constants.NUM_COL &&
                !this.myBoard.isWall(row, col);
    }

    /**
     * The targets in this method is relative to Pacman’s current location
     * The ghosts are all unique – this means they have different targets during chase mode and I use their color to
     * differentiate their target.
     * @return
     */
    public void Chase(){
        if(!this.isFrighten){
        BoardCoordinate pacmanLoc = this.myBoard.getPac().getCurrentLocation();
        if(this.getColor()== Color.RED){
            this.setTarget(pacmanLoc);
        }else if (this.getColor()== Color.LIGHTPINK){
            this.setTarget(new BoardCoordinate(pacmanLoc.getRow() + 1, pacmanLoc.getColumn() - 3, true));
        } else if (this.getColor()==Color.ORANGE) {
            this.setTarget(new BoardCoordinate(pacmanLoc.getRow() - 4, pacmanLoc.getColumn(), true));
        }else{
            this.setTarget(new BoardCoordinate(pacmanLoc.getRow(), pacmanLoc.getColumn() + 2, true));
        }

        this.move(this.mybfs(this.target));
       }

    }

    /**
     * this method use the bfs to move the ghost and the target is set to the corners of the screen
     * each ghost has its own corner to cover during this mode
     */
    public void scatterMode() {
        if(!this.isFrighten){
        BoardCoordinate scatterTarget;
        if (this.getColor() == Color.RED) {
            scatterTarget = new BoardCoordinate(0, 0, true); // Top-left corner
        } else if (this.getColor() == Color.LIGHTPINK) {
            scatterTarget = new BoardCoordinate(0, Constants.NUM_COL - 1, true); // Top-right corner
        } else if (this.getColor() == Color.ORANGE) {
            scatterTarget = new BoardCoordinate(Constants.NUM_row - 1, 0, true); // Bottom-left corner
        } else {
            scatterTarget = new BoardCoordinate(Constants.NUM_row - 1, Constants.NUM_COL - 1, true); // Bottom-right corner
        }

        this.move(this.mybfs(scatterTarget));
        }
    }

    public boolean getIsFrighten() {
        return this.isFrighten;
    }


    public void setIsFrighten(Boolean fright) {
        this.isFrighten = fright;
    }


    /**
     * this method is Ghosts will move randomly
     * At every intersection, a random direction is chosen for the ghost to move.
     */
    public void frightenMode() {
        this.isFrighten = true;
        this.ghost.setFill(Constants.FRIGHTENED_GHOST_COLOR);
    }

    public Color getOriginalColor() {
        return this.originalColor;
    }


    /**
     * this method return the random direction that the ghost will take in frighten mode
     * @return random diretions
     */
    public Direction getRandomDirection() {
        ArrayList<Direction> validDirections = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            int newRow = dir.newRow(this.currentRow);
            int newCol = dir.newCol(this.currentCol);

            if (this.isValid(newRow, newCol) && !dir.equals(this.directionMoving.opposite())) {
                validDirections.add(dir);
            }
        }

        if (!validDirections.isEmpty()) {
            int randomIndex = (int) (Math.random() * validDirections.size());

            return validDirections.get(randomIndex);
        }

        return null;
    }



    /**
     * this is move method, it uses the direction enum class to update the x and y coordinate of my ghost
     * whenever it is called
     * @param direction
     */
    public void move (Direction direction) {
        if(direction != null && !direction.equals(this.directionMoving.opposite())){
        int newRow = direction.newRow(this.currentRow);
        int newCol = direction.newCol(this.currentCol);
        if (this.isValid(newRow, newCol)) {
            // Move to the new position
            this.ghost.setX(newCol * Constants.SQ_SIZE);
            this.ghost.setY(newRow * Constants.SQ_SIZE);
            this.currentRow = newRow;
            this.currentCol = newCol;
            this.directionMoving = direction;
        }

        }
    }

    /**
     * this methode release update the ghost position, to free then from the ghost pen.
     */
    public void releasefromPen(){
        int releaseRow =this.initialRow - 2;
        int releaseCol = this.initialCol;
        this.getGhost().setX(releaseCol * Constants.SQ_SIZE);
        this.getGhost().setY(releaseRow * Constants.SQ_SIZE);
        this.currentRow = releaseRow;
        this.currentCol = releaseCol;

        this.Chase();
    }

    /**
     * this method reset the position of the ghost to their initial position
     */
    public void resetPosFrightenMode() {
        this.ghost.setX(this.initialCol*Constants.SQ_SIZE);
        this.ghost.setY(this.initialRow*Constants.SQ_SIZE);
        this.currentRow = this.initialRow;
        this.currentCol = this.initialCol;
    }


        public void resetPos() {
            this.ghost.setX(this.initialCol * Constants.SQ_SIZE);
            this.ghost.setY(this.initialRow * Constants.SQ_SIZE);
            this.currentRow = this.initialRow;
            this.currentCol = this.initialCol;

            this.directionMoving = Direction.UP;
            this.queue = null;

            this.isFrighten = false;
            this.ghost.setFill(this.originalColor);
            this.counter = 0;
            this.myBoard.getGhostsPen().add(this);
            this.Chase();
        }



    /**
     * this a getter method
     * @return a circle
     */
    public Rectangle getGhost() {
        return this.ghost;
    }

    public Color getColor(){
        return (Color) this.ghost.getFill();
    }



}

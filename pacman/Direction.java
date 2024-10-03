package pacman;

public enum Direction {
    UP,DOWN,LEFT,RIGHT;

    public Direction opposite(){
        switch (this){
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return LEFT;
        }
    }

    public int newRow(int currRow) {
        switch (this) {
            case UP:
                return currRow - 1;
            case DOWN:
                return currRow + 1;
            default:
                return currRow;
        }
    }

    public int newCol(int currCol) {
        switch (this) {
            case LEFT:
                if(currCol == 0){
                    return 22;
                }
                return currCol - 1;
            case RIGHT:
                if(currCol ==22){
                    return 0;
                }
                return currCol + 1;
            default:
                return currCol;
        }
    }
}

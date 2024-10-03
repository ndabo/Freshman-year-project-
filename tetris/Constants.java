package tetris;

public class Constants {

    // width of each square
    public static final int SQUARE_WIDTH = 30;

    // coordinates for squares in each tetris piece
    public static final int[][] I_PIECE_COORDS = {{120, 30}, {120, 2*SQUARE_WIDTH}, {120, 3 * SQUARE_WIDTH}, {120, 4 * SQUARE_WIDTH}};
    public static final int[][] SQ_PIECE_COORDS = {{120-SQUARE_WIDTH,30},{120-SQUARE_WIDTH,2*SQUARE_WIDTH}, {120,30},{120,2*SQUARE_WIDTH}};
    public static final int[][] L_PIECE_COORDS = {{120-SQUARE_WIDTH,30}, {120-SQUARE_WIDTH, 2*SQUARE_WIDTH}, {120,30}, {120 + SQUARE_WIDTH,30}};
    public static final int[][] L2_PIECE_COORDS ={{120 - SQUARE_WIDTH,30}, {120 + SQUARE_WIDTH, 2*SQUARE_WIDTH}, {120,30}, {120+SQUARE_WIDTH,30}};
    public static final int[][] C_PIECE_COORDS ={{120-SQUARE_WIDTH,30}, {120 - SQUARE_WIDTH,2*SQUARE_WIDTH}, {120, 2*SQUARE_WIDTH},{120,3*SQUARE_WIDTH}};
    public static final int[][] C2_PIECE_COORDS = {{120,30}, {120,2*SQUARE_WIDTH}, {120  - (SQUARE_WIDTH),2*SQUARE_WIDTH},{120-(SQUARE_WIDTH),3*SQUARE_WIDTH}};
    public static final int[][] T_PIECE_COORDS = {{120-SQUARE_WIDTH, 30}, {120-SQUARE_WIDTH, 2*SQUARE_WIDTH}, {120-SQUARE_WIDTH, 3 * SQUARE_WIDTH}, {120, 2*SQUARE_WIDTH}};
    public static final String WINDOW_BACKGROUND = "-fx-background-color: #000000";

    public static final int SCENE_WIDTH = 360;

    public static final int SCENE_HEIGHT = 700 ;
    public static final int BTN_PANE_WIDTH = 300;
    public static final int BTN_PANE_HEIGHT = 50;
    public static final String BTN_PANE_COLOR = "-fx-background-color: #88D1F1";
    public static final int NUM_OF_ROW = 22;
    public static final int NUM_OF_COL = 12;


}

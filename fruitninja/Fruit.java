package fruitninja;

import cs15.prj.fruitNinjaSupport.CS15Fruit;
import cs15.prj.fruitNinjaSupport.CS15ScoreController;
import cs15.prj.fruitNinjaSupport.Constants;
import javafx.scene.ImageCursor;

/**
 * this class inherited from the CS15Fruit Abstract class and also implement the Choppable interface.
 * it has an instance of the CS15Controller
 */
public class Fruit extends CS15Fruit implements Choppable {
    private CS15ScoreController myController;
    private int score;

    /**
    construct a new fruit with the given score and associate with the CS15controller
     */
    public Fruit(CS15ScoreController myController, int score){
        super();
        this.wash();
        this.ripen();
        this.myController = myController;
        this.score = score;
    }

    /**
     * this methode automatically move the fruits when it is called in the
     * updateChoppable
     */
    @Override
    public void launch() {
        this.moveFruit();
    }

    /**
     * this is the method that is called when the fruit intersect with the blade
     * it adds the score of each fruit in the controller.
     * this method is what happen to the fruit when they intersect with the blade.
     */
    @Override
    public void afterChopped() {
        this.chopGraphically();
        this.splash();
        this.myController.addToScore(this.score);
    }
}

package fruitninja;

import cs15.prj.fruitNinjaSupport.CS15ScoreController;
import cs15.prj.fruitNinjaSupport.Constants;

/**
 * this class inherited from the Fruit class.
 * it has all the methode of the fruit class and override the getImage method
 */
public class Apple extends Fruit {
    public Apple(CS15ScoreController myController, int score) {
        super(myController, score);
    }

    /**
     * this method overrides the fruit's getImage method to provide the image of the fruit Apple
     * @return the image of Apple
     */
    @Override
    public String getImage () {
        return Constants.APPLE_PATH;
    }
}

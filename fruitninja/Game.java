package fruitninja;

import cs15.prj.fruitNinjaSupport.CS15Blade;
import cs15.prj.fruitNinjaSupport.CS15FruitNinjaGame;
import cs15.prj.fruitNinjaSupport.CS15ScoreController;

import static cs15.prj.fruitNinjaSupport.Constants.*;

/**
 * this is the game class, it is a subclass of CS15FruitNinjaGame.
 * it has a score controller, a blade, bomb and fruit that are randomly launch
 */
public class Game extends CS15FruitNinjaGame {

    private CS15ScoreController scoreController;
    private CS15Blade blade;

    public Game(CS15ScoreController scoreController){
        super();
        this.startGame();
        this.scoreController = scoreController;
        this.blade = new CS15Blade("dabs");
        this.addBlade(this.blade);

    }

    /**
     * this method update the object position every millisecond in the game.
     * it also checks if the blade intersect with the fruits or the bomb
     * @param choppable
     */
    public void updateChoppable(Choppable choppable) {
        choppable.launch();
        if (this.blade.checkIntersection(choppable)) {
            choppable.afterChopped();
        }

    }

    /**
     * this method launch object randomly off the screen.
     * @return object
     */
    @Override
    public Object launchItem() {
        int number = (int) (Math.random() * 5); //this line return a random value from 0 to 4
        Choppable myChoppables = null;
        switch (number) {
            case 0:
                myChoppables = new Bomb();
                break;
            case 1:
                myChoppables = new Apple(this.scoreController, 5);
                break;
            case 2:
                myChoppables = new Peaches(this.scoreController, 2);
                break;
            case 3:
                myChoppables = new Pears(this.scoreController, 3);
                break;
            case 4:
                myChoppables = new Lemons(this.scoreController, 1);
                break;
            default:
                break;
        }
        return myChoppables;
    }

    /* Do not modify anything below this line! */
    @Override
    public void updateChoppableHelper() {
        this.updateChoppable(this.getCurrentItem());
    }
}

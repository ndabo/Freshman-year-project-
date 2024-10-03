package fruitninja;

import cs15.prj.fruitNinjaSupport.CS15Bomb;

/**
 * this class inherited from the CS15Bomb Abstract class and also implement the Choppable interface.
 */
public class Bomb extends CS15Bomb implements Choppable {

    /**
     * this methode automatically move the bomb when it is called in the
     * updateChoppable
     */
    @Override
    public void launch() {
        this.moveBomb();
    }

    /**
     * this is the method that is called when the bomb intersect with the blade
     * this method is what happen to the Bomb when they intersect with the blade.
     */
    @Override
    public void afterChopped() {
        this.explode();
    }
}

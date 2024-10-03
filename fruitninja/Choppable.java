package fruitninja;

/**
 * this is an interface, it declared two methods that will be implements in the
 * bomb and fruit class.
 */
public interface Choppable {

    /**
     * move the object in the game
     */
    void launch();

    /**
     * what happens to the object when they intersect with the blade
     */
    void afterChopped();
}

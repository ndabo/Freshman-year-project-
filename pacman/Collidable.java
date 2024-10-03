package pacman;

import javafx.geometry.Bounds;

public interface Collidable {
    void onCollision();
    int score();
    Bounds getBoundsInParent();

}

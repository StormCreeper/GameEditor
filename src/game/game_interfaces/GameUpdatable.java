package game.game_interfaces;


/**
 * Objects that can be updated in the game loop
 */
public interface GameUpdatable {

    /**
     * Is called before the draw method, to update the object's state
     * @param deltaTime time in seconds since last frame
     */
    public void update(double deltaTime);

}

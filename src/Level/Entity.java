package Level;

import org.jsfml.graphics.Sprite;

/**
 * Entity interface, something that can be drawn and move on gameboard
 */

public interface Entity {

    // Entity interface for all drawable things in game.
    public void draw(GameBoard window,double delta, double dT);
  
    public int update(GameBoard window,double dT);

    public Sprite getSprite();
}

package PowerUps;

import Level.GameBoard;
import Ships.PlayerShip;

/**
 * Interface for powerups
 */
public interface PowerUp {
    public void use(GameBoard window, PlayerShip ship);
}

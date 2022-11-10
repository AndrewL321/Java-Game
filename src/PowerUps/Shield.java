package PowerUps;

import java.util.Timer;

import Level.GameBoard;
import Ships.PlayerShip;

/**
 * Class for shield powerup. Adds shield to player that lasts a time or when health is 0
 */
public class Shield implements PowerUp {

    private int health;
    private int duration;

    /**
     * Sets shield heath and duration
     * @param level level this powerup is at
     */
    public Shield(int level) {
        health = 2 +level;
        duration = 4000 + (1000 * level);
    }
    /**
     * Called when used. Adds shield to ship, takes damage in place of ship for time
     * @param board this is part of
     * @param ship to be applied to
     */
    @Override
    public void use(GameBoard window, PlayerShip ship) {
        ship.setShield(this);
        ship.setShieldActive(true);
        Timer shieldTimer = new Timer();
        shieldTimer.schedule(
        new java.util.TimerTask() {
            @Override
            public void run() {
                ship.setShieldActive(false);
                shieldTimer.cancel();
            } 
        }, 
        duration);
    }

    /**
     * When shield takes damage
     * @return new hp
     */
    public int doDamage(){
        health --;
        return health;
    }
    
}

package PowerUps;

import java.util.Timer;

import Laser.PowerLaser;
import Level.GameBoard;
import Ships.PlayerShip;

/**
 * Laser power up. Creates laser that destroys anything it touches for short time
 */
public class StrongLaser implements PowerUp {

    private int duration;

    /**
     * Sets duration based off level of upgrade
     * @param level the level this is upgraded to
     */
    public StrongLaser(int level) {
        duration = 1000 + (500*level);
    }

    /**
     * Called when used. Creates image for laser and sets to position on ship
     * Creates timer to remove itself after duration
     * @param window gameboard this is part of
     * @ship ship that used this powerup
     */
    @Override
    public void use(GameBoard window, PlayerShip ship) {
        PowerLaser temp = new PowerLaser(window, ship);
        window.addLaser(temp);

        Timer laserTimer = new Timer();
            laserTimer.schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    window.removeLaser(temp);
                    laserTimer.cancel();
                } 
            }, 
            duration);
    }

    
}

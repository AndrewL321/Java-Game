package Ships;

import java.nio.file.Paths;


import Level.GameBoard;

/**
 * Stronger ship implemantation
 */
public class StrongShip extends PlayerShip {
    
    private int capacity = 2;

    
    /**
     * Sets all value
     * @param window to draw
     * @param barSize size of ui at top
     * @param l lives
     * @param u upgrade levels
     */
    public StrongShip(GameBoard window,int barSize,int l,int[] u){
        super(window,barSize,u);
        SHIP_ACCEL = 0.0000000000000012f;
        DRAG_MULTIPLIER = 0.0000000022f;

        shipSprite = loadImage(Paths.get("Images/strongShip.png"));

        shipSprite.setScale((float)0.7*window.getScale().x,(float)0.7*window.getScale().y);
         
        shipSprite.setPosition(shipSprite.getGlobalBounds().width,window.getSize().y/2);

        shipSprite.setColor(noGhost);
        lives = l +capacity;
        ammo = 3;
        fullAmmo = ammo;
        sizePerAmmo = maxAmmo.getScale().x/ammo;
    }

    /**
     * Returns extra capacity this ship has
     * @return int for this ships extra space
     */
    public int getCapacity(){
        return capacity;
    }

    
}

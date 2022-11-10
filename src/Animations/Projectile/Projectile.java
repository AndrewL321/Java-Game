package Animations.Projectile;
import Level.Entity;
import Level.GameBoard;
import org.jsfml.graphics.*;

import java.lang.System;

/**
 * Used for explosion effects and particles.
 */
public class Projectile extends Sprite implements Entity {

    private float objSpeedX;
    private float objSpeedY;
    private long flightLength;
    private long time;

    /**
     * Initializes a projectile.
     * @param x coordinate.
     * @param y coordinate.
     * @param window game window.
     * @param image projectile image.
     * @param speedMultX speed in x axis.
     * @param speedMultY speed in y axis.
     * @param flightLength the time the projectile flies.
     */
    public Projectile(int x, int y, GameBoard window, Texture image, float speedMultX, float speedMultY, double flightLength){
        this.flightLength = (long) flightLength;
        this.time = System.currentTimeMillis();
        this.setTexture(image);
        this.setPosition(x,y);
        this.setScale((float)0.6 * window.getScale().x,(float)0.6 * window.getScale().y);
        this.objSpeedX = speedMultX;
        this.objSpeedY = speedMultY;
    }

    @Override
    public void draw(GameBoard window, double delta, double dT) {
        window.draw(this);
    }

    @Override
    public int update(GameBoard window, double multiplier) {
        if((System.currentTimeMillis() - time) > flightLength){                            // Remove entity if off screen
            return 1;
        }
        float newX =(float) (this.getPosition().x + (objSpeedX * multiplier));
        float newY = (float)(this.getPosition().y + (objSpeedY * multiplier));
        if(objSpeedX < 2 || objSpeedY < 2){
            objSpeedX = (float) (objSpeedX * 0.97);
            objSpeedY = (float) (objSpeedY * 0.97);
        }
        else{
            objSpeedX = (float) (objSpeedX * 0.8);
            objSpeedY = (float) (objSpeedY * 0.8);
        }
        this.setPosition(newX, newY);
        return 0;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

}


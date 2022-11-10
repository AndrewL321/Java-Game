package Animations.Projectile;

import Level.GameBoard;
import Utility.GameSound;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Particle system for the game, makes explosions at set coordinates.
 */
public class Explosions {

    private GameSound gameSound = new GameSound();

    /**
     * Ship explosion particles.
     * @param gameWindow game window.
     * @param x coordinate on the x axis at which the explosion is set off.
     * @param y coordinate on the y axis at which the explosion is set off.
     */
    public void shipExplosion(GameBoard gameWindow, float x, float y){
        Random ranNum = new Random();
        Texture images[] = new Texture[2];
        Path path[] = new Path[2];
        path[0] = Paths.get("Images/pizza.png");
        path[1] = Paths.get("Images/pizzaSlice.png");



        images[0] = new Texture();
        images[1] = new Texture();
        try {
            images[0].loadFromFile(path[0]);
            images[1].loadFromFile(path[1]);
        } catch (IOException e) {
            System.out.println("Unable to load images for ship explosion.");
            e.printStackTrace();
        }

        gameSound.Sound("explosion", 110.f);

        for(int i = 0; i <= 40; i++){
        float possX = (float) ranNum.nextInt(100);
        float possY = (float) ranNum.nextInt(100);
        int direction = ranNum.nextInt(4);
        int image = ranNum.nextInt(2);
        if (direction == 0) {
            gameWindow.addProjectile(new Projectile((int) x, (int) y, gameWindow, images[image], (float) 0.000000008 * possX, (float) -0.000000008 * possY, 700));
        } else if (direction == 1) {
            gameWindow.addProjectile(new Projectile((int) x, (int) y, gameWindow, images[image], (float) 0.000000008 * possX, (float) 0.000000008 * possY, 700));
        } else if (direction == 2) {
            gameWindow.addProjectile(new Projectile((int) x, (int) y, gameWindow, images[image], (float) -0.000000008 * possX, (float) 0.000000008 * possY, 700));
        } else if (direction == 3) {
            gameWindow.addProjectile(new Projectile((int) x, (int) y, gameWindow, images[image], (float) -0.000000008 * possX, (float) -0.000000008 * possY, 700));
        }
        }
    }
}



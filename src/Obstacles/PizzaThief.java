package Obstacles;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import Utility.GameSound;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import Level.GameBoard;

/**
 * A class for a pizza thief enemy. Randomly moves and shoots at player
 * extends obstacle
 */

public class PizzaThief extends Obstacle {

    private float obSpeed;
    private Texture laser;
    private int hp =3;
    private RectangleShape maxHp;
    private RectangleShape curHp;
    private float scalePerHp;
    private GameSound gameSound = new GameSound();



    /**
     * Creates a pizza thief with hp bar 
     * @param image texture for this instance
     * @param speedMult speed multiplier for assigning difficulty
     * @param xScale x scale to adjust to
     * @param yScale y scale
     * @param sScale scale as vector
     */
    public PizzaThief(Texture image, float speedMult, Vector2f scale, Vector2f speedScale) {
        super(image, 0, scale,speedScale);
        obSpeed = -0.0000002f * speedMult * scale.y;
        objSpeed = 0;
        laser = loadTexture(Paths.get("Images/RedLaser.png"));
        obstacleSprite.setScale(1.6f*scale.x,1.6f*scale.y);

        maxHp = new RectangleShape(new Vector2f(10,80));
        curHp = new RectangleShape(new Vector2f(10,80));

        maxHp.setFillColor(Color.WHITE);
        curHp.setFillColor(Color.RED);

        maxHp.setOrigin(curHp.getSize().x/2,curHp.getGlobalBounds().top + curHp.getGlobalBounds().height);
        curHp.setOrigin(curHp.getSize().x/2,curHp.getGlobalBounds().top + curHp.getGlobalBounds().height);

        maxHp.setScale(scale);
        curHp.setScale(scale);

        scalePerHp = curHp.getScale().y / hp;


    }

    /**
     * Updates based off delta time. Moves left until in desired position
     * then up and down. Will shoot or change direction based off random generated number
     * Cannot move off play area
     * @param window to draw with
     * @param multiplier time step 1sec/tickrate as milliseconds
     * @return 0. This is used for determining if moved off screen, but this never will
     */
    @Override
    public int update(GameBoard window, double multiplier) {
        double ranNum = Math.random() * 400;

        if (ranNum >= 397){
            this.shoot(window);
        }
        else if(ranNum <= 2){
            obSpeed *= -1;
        }

        if (obstacleSprite.getPosition().x > window.getSize().x - (obstacleSprite.getGlobalBounds().width * 2)) {
            obstacleSprite.move((float) (-0.0000002f * multiplier), 0);
        }
        obstacleSprite.move(0, (float) (obSpeed * multiplier));
        if (obstacleSprite.getPosition().y >= window.getSize().y - obstacleSprite.getGlobalBounds().height) {
            obstacleSprite.setPosition(obstacleSprite.getPosition().x,
                    window.getSize().y - obstacleSprite.getGlobalBounds().height - (1 * window.getScale().x));
            obSpeed = obSpeed * -1;
        } else if (obstacleSprite.getPosition().y <= 5 + window.getUiSize()) {
            obstacleSprite.setPosition(obstacleSprite.getPosition().x, 5 + window.getUiSize());
            obSpeed = obSpeed * -1;
        }
        return 0;
    }

    /**
     * Draw method interpolation for smoother movement. Moves hp bar with ship
     * @param window to draw with
     * @param delta how far between timesteps 0-1
     * @param dT the target timestep
     */
    @Override
    public void draw(GameBoard window, double delta, double dT) {

        float newy = obstacleSprite.getPosition().y;

        obstacleSprite.setPosition(obstacleSprite.getPosition().x,
                (float) ((newy * (1 - delta)) + ((newy + obSpeed * dT) * delta))); // Set position with interpolation

        window.draw(obstacleSprite);

        maxHp.setPosition(obstacleSprite.getPosition().x + obstacleSprite.getGlobalBounds().width + (curHp.getGlobalBounds().width*2), obstacleSprite.getPosition().y + obstacleSprite.getGlobalBounds().height);
        curHp.setPosition(obstacleSprite.getPosition().x + obstacleSprite.getGlobalBounds().width + (curHp.getGlobalBounds().width*2), obstacleSprite.getPosition().y + obstacleSprite.getGlobalBounds().height);

        window.draw(maxHp);
        window.draw(curHp);

        obstacleSprite.setPosition(obstacleSprite.getPosition().x, newy); // Move back to position before interpolation
    }

    /**
     * Thief shoot method. Create laser obstacle and adds to baoard
     * @param gameWindow board to add to
     */
    private void shoot(GameBoard gameWindow) {
        EnemyLaser temp = new EnemyLaser(laser,5,gameWindow.getScale(),gameWindow.getScale());
        temp.getSprite().setPosition(obstacleSprite.getPosition().x,obstacleSprite.getPosition().y);
        gameSound.Sound("shoot", 35.f);
        gameWindow.addObstacle(temp);
    }

    /**
     * Loads texture from path
     * @param p path of texture
     * @return loaded texture
     */
    public Texture loadTexture(Path p) {
        Texture temp = new Texture();
        try {
            temp.loadFromFile(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }
    /**
     * called when thief takes damage
     * @return new hp value
     */
    @Override
    public int doDamage(){
        hp--;
        curHp.setScale(curHp.getScale().x,curHp.getScale().y - scalePerHp);
        return hp;
    }
    
}

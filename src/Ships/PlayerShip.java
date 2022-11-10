package Ships;

import Utility.GameSound;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import Level.*;
import PowerUps.*;
import Laser.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;

/**
 * Abstract class used for a player model
 * with methods for updating,moving and other ship functions
 */
public abstract class PlayerShip implements Entity {

    private Boolean up = false; // Used to store what directions are pressed
    private Boolean right = false;
    private Boolean left = false;
    private Boolean down = false;

    private double xSpeed = 0; // Current speed of ship
    private double ySpeed = 0;

    private Boolean visible = false;

    protected double DRAG_MULTIPLIER = 0.0000000018f; // How fast the ship loses speed. Also effects max speed
    protected double SHIP_ACCEL = 0.0000000000000016f; // How fast this ship gains speed

    protected Sprite flame;

    private int uiSize;

    protected Color ghost = new Color(252, 252, 252, 100);
    protected Color noGhost = new Color(252, 252, 252, 252);

    protected boolean isGhost = false;

    protected Sprite shipSprite;

    protected int ammo;
    protected int fullAmmo;

    protected int lives;

    protected GameSound gameSound = new GameSound();
    protected RectangleShape curAmmo;
    protected RectangleShape maxAmmo;
    protected float sizePerAmmo;

    protected int powerUpLevels[] = new int[3];

    protected PowerUp equipped = null;

    protected boolean shieldActive = false;
    protected Sprite shieldImage;
    protected Shield shield = null;

    /**
     * Creates a player ship instance. Loads sprite and extra sprites for flame and ammo bars
     * @param window The board this is used on
     * @param barSize the size of the ui. Used to stop it overlapping
     * @param u The unlock levels for upgrades
     */
    public PlayerShip(GameBoard window, int barSize,int[] u) {

        visible = true;
        powerUpLevels = u;

        flame = loadImage(Paths.get("Images/flame.png"));
        flame.setScale(window.getScale());
        shieldImage = loadImage(Paths.get("Images/shieldActive.png"));
        shieldImage.setScale(window.getScale().x * 0.25f,window.getScale().y * 0.25f);

        uiSize = barSize;
        curAmmo = new RectangleShape(new Vector2f(120f, 8f));
        maxAmmo = new RectangleShape(new Vector2f(120f, 8f));

        curAmmo.setScale(window.getScale());
        maxAmmo.setScale(window.getScale());

        curAmmo.setFillColor(Color.GREEN);
        maxAmmo.setFillColor(new Color(252,252,252));
    }

    /**
     * Updates players postion. Checks if direction is pressed and if so accelerates in that direction
     * @param gameWindow board to draw with
     * @param multiplier timestep as 1sec/tickrate in milliseconds
     */
    @Override
    public int update(GameBoard gameWindow, double multiplier) {         // Update ships position

        double shipAccX = 0;                                // Acceleration of ship
        double shipAccY = 0;

        // Accel rate * time per tick
        if(up){
            shipAccY = -SHIP_ACCEL * multiplier ;
        }
        if(down){
            shipAccY = SHIP_ACCEL * multiplier ;
            
        }
        if(left){
            shipAccX = -SHIP_ACCEL * multiplier ;
        }
        if(right){
            shipAccX = SHIP_ACCEL * multiplier ;

        }

        //Final speed - drag

        double pythag = Math.sqrt(shipAccX * shipAccX + (shipAccY * shipAccY));


        if(pythag != 0){
            if(shipAccX > 0){
                shipAccX = shipAccX * (shipAccX/pythag);
            }
            else{
                shipAccX = -shipAccX * (shipAccX/pythag);
            }
            if(shipAccY > 0){
                shipAccY = shipAccY * (shipAccY/pythag);
            }
            else{
                shipAccY = -shipAccY * (shipAccY/pythag);
            }
           
        }

        shipAccX = shipAccX - DRAG_MULTIPLIER * xSpeed * multiplier;
        shipAccY = shipAccY - DRAG_MULTIPLIER * ySpeed * multiplier;

        
        ySpeed += shipAccY * gameWindow.getScale().y ;
        xSpeed += shipAccX * gameWindow.getScale().x ;

        shipSprite.move((float)(xSpeed * multiplier),(float)(ySpeed * multiplier));             // Move to new position


                                                                        // If out of bounds move back in bounds

        if(shipSprite.getPosition().x >= gameWindow.getSize().x - shipSprite.getGlobalBounds().width){
            shipSprite.setPosition(gameWindow.getSize().x - shipSprite.getGlobalBounds().width - 1,shipSprite.getPosition().y);
            xSpeed = 0;
        }
        else if(shipSprite.getPosition().x <= 1){
            shipSprite.setPosition(1,shipSprite.getPosition().y);
            xSpeed = 0;
        }
        if(shipSprite.getPosition().y >= gameWindow.getSize().y - shipSprite.getGlobalBounds().height){
            shipSprite.setPosition(shipSprite.getPosition().x, gameWindow.getSize().y - shipSprite.getGlobalBounds().height - (1 * gameWindow.getScale().x));
            ySpeed = 0;
        }
        else if(shipSprite.getPosition().y <= 5 + uiSize){
            shipSprite.setPosition(shipSprite.getPosition().x, 5 + uiSize);
            ySpeed = 0;
        }
        return 0;
    }

                                            // All these used to store which key is pressed


    /**
     * sets up to pressed
     */
    public void setUp(){
        up = true;
    }
    /**
     * Sets right to pressed
     */
    public void setRight(){
        right = true;
    }
    /**
     * sets left to pressed
     */
    public void setLeft(){
        left = true;
    }
    /**
     * sets down to pressed
     */
    public void setDown(){
        down = true;
    }


    /**
     * sets up to not pressed
     */
    public void unSetUp(){
        up = false;
    }
    /**
     * sets right to not pressed
     */
    public void unSetRight(){
        right = false;
    }
    /**
     * sets left to not pressed
     */
    public void unSetLeft(){
        left = false;
    }
    /**
     * sets down to not pressed
     */
    public void unSetDown(){
        down = false;
    }

    /**
     * Draws ship and extra sprites(flame,ammo) based off its predicted position from time between timestep
     * @param window board to draw with
     * @param delta time between timesteps 0-1
     * @param dT target time step 1sec/tickrate in milliseconds
     */
    @Override
    public void draw(GameBoard window,double delta, double dT) {

        float newX = shipSprite.getPosition().x;
        float newY = shipSprite.getPosition().y;
        double tempX = 0 ;
        double tempY = 0 ;
        double tempXSpeed = xSpeed;
        double tempYSpeed = ySpeed;
           
        boolean moving = false;

        // Move to postition based off current time since last tick
        if(right){
             tempX = SHIP_ACCEL * delta * dT ;
             moving = true;
        }
        else if (left){
             tempX = -SHIP_ACCEL * delta * dT ;
            
        }
        if(up){
             tempY = -SHIP_ACCEL * delta * dT;
             moving = true;

        }
        else if (down){
             tempY = SHIP_ACCEL * delta * dT ;
             moving = true;
            
        }

        tempX = tempX - DRAG_MULTIPLIER * tempXSpeed * dT * delta;
        tempY = tempY - DRAG_MULTIPLIER * tempYSpeed * dT * delta;


        double pythag = Math.sqrt(tempX * tempX + (tempY * tempY));

        if(pythag != 0){
            if(tempX > 0){
                tempX = tempX * (tempX/pythag);
            }
            else{
                tempX = -tempX * (tempX/pythag);
            }
            if(tempY > 0){
                tempY = tempY * (tempY/pythag);
            }
            else{
                tempY = -tempY * (tempY/pythag);
            }
           
        }




        tempXSpeed += tempX * window.getScale().x;
        tempYSpeed += tempY * window.getScale().y;

        
        
        shipSprite.setPosition((float)((newX * (1 - delta)) + ((newX + (tempXSpeed *delta * dT)) * delta)),(float)((newY * (1 - delta)) + ((newY + (tempYSpeed *delta * dT)) * delta)));
       
        if(shieldActive){
            shieldImage.setPosition(shipSprite.getPosition().x + 100,shipSprite.getPosition().y - 30);
            window.draw(shieldImage);        }
        if(moving){
            flame.setPosition(shipSprite.getPosition().x - 60, shipSprite.getPosition().y+14);
            window.draw(flame);
        }
       
        window.draw(shipSprite);

        if(ammo < fullAmmo){

            curAmmo.setPosition(shipSprite.getPosition().x + 30*window.getScale().x, shipSprite.getPosition().y+shipSprite.getGlobalBounds().height + 15*window.getScale().y);
            maxAmmo.setPosition(shipSprite.getPosition().x + 30 *window.getScale().x, shipSprite.getPosition().y+shipSprite.getGlobalBounds().height + 15*window.getScale().y);

            window.draw(maxAmmo);
            window.draw(curAmmo);
        }
        

                                            // Set back to pre interpolation position
        shipSprite.setPosition(newX,newY);

    }
    /**
     * Set ship visible to this value
     * @param bool set to
     */
    public void setVisible(Boolean bool){visible = bool;}

    /**
     * Returns if currently visible
     * @return true for yes, false for no
     */
    public Boolean getVisible(){return visible;}

    /**
     * Resets this ship to default position and no speed
     * sets ship to ghost mode for 3 seconds
     */
    public void reset(GameBoard gameWindow){

        this.xSpeed = 0;
        this.ySpeed = 0;
        shipSprite.setPosition(100,gameWindow.getSize().y/2);
        this.setVisible(true);
        this.shipGhost(true,3000);
    }

    /**
     * Used to make ship transparent and take no damage for set time or unset this mode
     * @param set true for ghost mode, false to remove
     * @param time time to stay in shost mode before removing
     */
    public void shipGhost(boolean set, int time){
        if(set == true){
            shipSprite.setColor(ghost);
            isGhost = true;

            Timer ghostCount = new Timer();
            ghostCount.schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    shipGhost(false,0);
                    ghostCount.cancel();
                } 
            }, 
            time);
        }
        else{
            shipSprite.setColor(noGhost);
            isGhost = false;
        }
    }

    /**
     * Gets if ship is currently ghost mode
     * @return true for yes, false for no
     */
    public boolean isGhost(){
        return isGhost;
    }

    /**
     * Get the sprite for this ship
     * @return sprite used by this ship
     */
    public Sprite getSprite(){
        return shipSprite;
    }

    /**
     * Used to shoot a laser from ship. reduces ammo then increases back after 2 seconds
     * @param gameWindow board to add it to
     */
    public void shoot(GameBoard gameWindow){
        if(ammo > 0){
            curAmmo.setScale(curAmmo.getScale().x - sizePerAmmo, curAmmo.getScale().y);
            gameWindow.addLaser(new Laser(this.shipSprite.getPosition().x,this.shipSprite.getPosition().y,gameWindow));
            gameSound.Sound("shoot", 35.f);
            ammo--;
            Timer reloadTimer = new Timer();
            reloadTimer.schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    ammo++;
                    reloadTimer.cancel();
                    curAmmo.setScale(curAmmo.getScale().x+sizePerAmmo,curAmmo.getScale().y);
                } 
            }, 
            2000);
        }
    }

    /**
     * gets current lives
     * @return int for lives
     */
    public int getLives(){
        return lives;
    }

    /**
     * Damage this ship and remove a life if no shield active
     * @param window window is part of
     * @return 0 if shield took damage, 1 if damage taken
     */
    public int shipDamage(GameBoard window){
        if(shieldActive){
            if(shield.doDamage() == 0){
                shield = null;
                shieldActive = false;
            }
            return 0;
        }
        lives--;
        return 1;
    }
    /**
     * Sets the lives to inputted value, also deactivates button presses. Call at start of level
     * @param i lives to set to
     */
    public void setLives(int i){
        up = false;
        down = false;
        left = false;
        right = false;
        lives = i;
    }
    /**
     * Creates a sprite from image in path
     * @param path path of image
     * @return new sprite with image
     */
    protected Sprite loadImage(Path path){
        Texture image = new Texture();  
             
        try {                                       // Try to load image
            image.loadFromFile(path);
            
        } catch (IOException e) {
            System.out.println("Unable to load player ship image");
            e.printStackTrace();
        }
        image.setSmooth(true);
        return new Sprite(image);
    }

    /**
     * Abstract class for getting capacity
     * @return int for extra capacity of ship
     */
    public abstract int getCapacity();

    /**
     * Adds this powerup to ship
     * @param p name of powerup to add "LASER","HYPER","SHIELD"
     */
    public void addPowerUp(String p){
        if(equipped == null){
            if(p == "LASER"){
                equipped = new StrongLaser(powerUpLevels[0]);
            }
            if(p == "HYPER" ){
                equipped = new HyperDrive(powerUpLevels[1]);
            }
            if(p == "SHIELD"){
                equipped = new Shield(powerUpLevels[2]);
            }
        }
    }

    /**
     * Uses powerup if one equipped
     * @param window window this is part of
     */
    public void usePower(GameBoard window){
        if(equipped != null){
            equipped.use(window,this);
            equipped = null;
        }
    }

    /**
     * get current xspeed
     * @return double of xspeed
     */
    public double getXSpeed(){
        return xSpeed;
    }
     /**
     * get current yspeed
     * @return double of yspeed
     */
    public double getYSpeed(){
        return ySpeed;
    }
    /**
     * Gets if up pressed
     * @return true yes, false no
     */
    public boolean getUp(){
        return up;
    }
    /**
     * Gets if right pressed
     * @return true yes, false no
     */
    public boolean getRight(){
        return right;
    }
    /**
     * Gets if left pressed
     * @return true yes, false no
     */
    public boolean getLeft(){
        return left;
    }
    /**
     * Gets if down pressed
     * @return true yes, false no
     */
    public boolean getDown(){
        return down;
    }
    /**
     * Gets if shield is active
     * @return true yes, false no
     */
    public boolean getShieldActive(){
        return shieldActive;
    }
    /**
     * sets shieldactive to this value
     * @param set value to set to
     */
    public void setShieldActive(boolean set){
        if(set == false){
            shield = null;
        }
        shieldActive = set;
    }
    /**
     * Sets this ships shield to this
     * @param set shield to give this ship
     */
    public void setShield(Shield set){
       
        shield = set;
    }
    /**
     * Gets if this ship has a powerup equipped
     * @return true if yes, false if no
     */
    public boolean getEquipped(){
        if(equipped == null){
            return false;
        }
        return true;
    }
    
}

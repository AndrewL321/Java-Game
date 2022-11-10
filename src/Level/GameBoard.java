package Level;

import Ships.*;
import Laser.*;
import Animations.Projectile.*;
import Main.GameState;
import StatusScreens.*;
import Utility.GameSound;
import Utility.LevelLoader;

import org.jsfml.window.*;

import java.util.ArrayList;

import Obstacles.*;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;


/**
 * The main class for storing and running all assests during a level
 */
public class GameBoard {

    private PlayerShip player; // Player controlled ship
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(); // Array list of drawable of entities
    private ArrayList<Obstacle> collectables = new ArrayList<Obstacle>(); // Array list of drawable of entities
    private ArrayList<Laser> lasers = new ArrayList<Laser>(); // Array list of drawable of entities
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); // For animations
    
    private UiBuilder ui; //Ui used during level

    private GameLoop loop; //Game loop
    private RenderWindow window; // Window to draw on

    private BackgorundScroll background; //Background

    private int distance;

    private int win = 0;

    private int uiSize;

    private Collisions collision;
    private GameState state;
    private LevelLoader load;
    private Explosions explosion = new Explosions();
    private GameSound gameSound = new GameSound();

    private int pizzaThiefRemain;

    /**
     * Gameboard constructor, creates a backgound scroll instance and a game loop
     * @param rWindow Render window used for this level
     * @param s The game state for loading information
     */
    public GameBoard(RenderWindow rWindow, GameState s) {
        
        window = rWindow;
        uiSize =(int)( 100 * this.getScale().y);
        window.clear();
        background = new BackgorundScroll(uiSize,this);
        
        collision = Collisions.getInstance();

        loop = new GameLoop(this);
        
        state = s;
    }

    /**
     * Start the game loop. Creates all that else that is needed for loop. 
     * Starts the loop in Gamellop
     */
    public void gameLevel() {

        LevelSelect select = new LevelSelect();
        load = select.getLevel(state.getCurrentPlanet(),state,window);
        select = null;
        if(load == null){
            return;
        }

        if(load.getDifficulty() == "HARD"){
            pizzaThiefRemain = 2;
        }
        else{
            pizzaThiefRemain = 1;
        }

        distance = load.getDistance();
        
        ShipBuilder builder = ShipBuilder.getInstance();
        player = builder.getShip(state.getShip(), this, uiSize,load.calcLives(distance),state.getAllUnlock());
        builder = null;

        ui = new UiBuilder(uiSize, this, player.getLives(), distance, state);

        StartLevelScreen start = new StartLevelScreen(player.getCapacity());
        start.show(window, load);
        start = null;

        this.clear();

        if(gameSound.isPlaying())
            gameSound.stop();
        gameSound.Sound("inGame", 40.f);

        loop.run(load);
    }

    /**
     * Used to detect user input
     * PollEvents and react to all
     */
    public void input(){

            for (Event events : window.pollEvents()) { // Check for events
                switch (events.type) {
                    case KEY_PRESSED:
                        keyPressEventHandler(events.asKeyEvent().key);
                        break;
                    case KEY_RELEASED:
                        keyReleasedEventHandler(events.asKeyEvent().key);
                        break;
                    case MOUSE_BUTTON_PRESSED:
                        mousePressedeventHandler(events);
                        break;
                    default:
                        break;

                }
            }

    }

    /**
     * Key press event handler
     * @param events The key that was pressed
     */
    private void keyPressEventHandler(Keyboard.Key events) { // events is name of a keyboard key

        switch (events) {
            case ESCAPE:
                pause();
                // System.exit(1);
                break;
            case UP:
            case W:
                player.setUp();
                break;
            case RIGHT:
            case D:
                player.setRight();
                break;
            case LEFT:
            case A:
                player.setLeft();
                break;
            case DOWN:
            case S:
                player.setDown();
                break;
            case E:
                player.usePower(this);
                ui.resetPower();
                break;    
            default:
                break;

        }

    }

    /**
     * Reeased handler
     * @param events key that was released
     */
    private void keyReleasedEventHandler(Keyboard.Key events) { // events is name of a keyboard key
        switch (events) {
            case UP:
            case W:
                player.unSetUp();
                break;
            case RIGHT:
            case D:
                player.unSetRight();
                break;
            case LEFT:
            case A:
                player.unSetLeft();
                break;
            case DOWN:
            case S:
                player.unSetDown();
                break;
            case SPACE:
                player.shoot(this);
            default:
                break;
        }
    }

    /**
     * Mouse press handler
     * @param events The mouse press event
     */
    private void mousePressedeventHandler(Event events) {
        if (events.asMouseButtonEvent().position.y < uiSize) {

            switch (ui.mousePressHandler(new Vector2f(events.asMouseButtonEvent().position))) {
                case 1:
                    pause();
                    break;
            }
        }
    }

    /**
     * Updates all objects currently stored in this class
     * Used a fixed time step
     * @param dT The time step as 1sec/tickrate in milliseconds
     */
    public void updateAll(double dT) {

        for (int i = 0; i < collectables.size(); i++) {
            if (collision.coinCollision(player, collectables.get(i)) != null) {

                if(collectables.get(i) instanceof Coin){
                    ui.addScore(collectables.get(i).getValue());

                }
                else if(collectables.get(i) instanceof PowerUpCollectable){
                    if(player.getEquipped() == false){
                        PowerUpCollectable temp = (PowerUpCollectable) collectables.get(i);

                        player.addPowerUp(temp.getType());
                        ui.setPower(temp.getType());
                    }
                }
                collectables.remove(i);
            }

        }
        if (player.isGhost() == false && collision.shipCollision(player, obstacles) != null) {
            if(player.shipDamage(this) == 1){
                explosion.shipExplosion(this, player.getSprite().getPosition().x, player.getSprite().getPosition().y);
                player.reset(this);
                ui.updateLives(player.getLives());
                if (player.getLives() <= 0) {
                    setLevelFailed();
                }
            }
        }

        player.update(this,dT);

        for (int i = 0; i < lasers.size(); i++) {
            for (int y = 0; y < obstacles.size(); y++) {
                if (collision.laserCollision(lasers.get(i), obstacles.get(y)) != null) {
                    int test = obstacles.get(y).doDamage();
                    if(test <=0){
                        obstacles.remove(y);
                    }
                    if(lasers.get(i) instanceof PowerLaser){
                        break;
                    }
                    lasers.remove(i);
                    break;
                }
            }
        }

        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).update(this,dT) == 1) {
                removeObstacle(obstacles.get(i));
            }
        }
        for (int i = 0; i < collectables.size(); i++) {
            if (collectables.get(i).update(this,dT) == 1) {
                removeCollectable(collectables.get(i));
            }
        }
        for (int i = 0; i < lasers.size(); i++) {
            if (lasers.get(i).update(this,dT) == 1) {
                removeLaser(lasers.get(i));
            }
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).update(this,dT) == 1) {
                removeProjectile(projectiles.get(i));
            }
        }
        if (ui.updateDistance(dT / 10000000) <= 0) {
            setLevelWin();
        }
        background.update(dT);
    }

    /**
     * Render all objects. Used interpoation to predict position before render
     * @param delta how far between timestep. 0-1 as percent of timestep.
     * @param dT the target timestep
     */
    public void render(double delta, double dT) {

        window.clear();
        background.draw(this, delta,dT);

        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).draw(this, delta, dT);
        }
        for (int i = 0; i < collectables.size(); i++) {
            collectables.get(i).draw(this, delta, dT);

        }
        for (int i = 0; i < lasers.size(); i++) {
            lasers.get(i).draw(this, delta, dT);
        }
        if (player.getVisible()) {
            player.draw(this, delta, dT);
        }
        for(int i = 0; i < projectiles.size(); i++)
        {
            projectiles.get(i).draw(this, delta, dT);
        }

        window.draw(ui);

        window.display();

    }

    /**
     * Add obstacle to board
     * @param toAdd Obstacle to add
     */
    public void addObstacle(Obstacle toAdd) {
        obstacles.add(toAdd);
    }

    /**
     * Add collectable to board
     * @param toAdd colectable to add
     */
    public void addCollectable(Obstacle toAdd) {
        collectables.add(toAdd);
    }

    /**
     * Add laser to board
     * @param toAdd laser to add
     */
    public void addLaser(Laser toAdd) {
        lasers.add(toAdd);
    }

    /**
     * Remove obstacle from board
     * @param toRemove obstacle to remove
     */
    public void removeObstacle(Obstacle toRemove) { obstacles.remove(toRemove); }

    /**
     * add projectile 
     * @param toAdd projectile to add
     */
    public void addProjectile(Projectile toAdd) { projectiles.add(toAdd); }

    /**
     * Remove projectile
     * @param toRemove projectile to remove
     */
    public void removeProjectile(Projectile toRemove) { projectiles.remove(toRemove); }

    /**
     * Remove collectable
     * @param toRemove collactable to remove
     */
    public void removeCollectable(Obstacle toRemove) {
        collectables.remove(toRemove);
    }

     /**
     * Remove laser
     * @param toRemove laser to remove
     */
    public void removeLaser(Entity toRemove) {
        lasers.remove(toRemove);
    }

    /**
     * Returns the size of the ui at the top of board in pixels
     * @return pixel size of ui
     */
    public int getUiSize() {
        return uiSize;
    }

    /**
     * Calculates and returns size of window
     * @return a vector of the size of the window
     */
    public Vector2f getSize() {
        return new Vector2f (1920 * getScale().x,1080 * getScale().y);
    }

    /**
     * Clears all objects stored by gameboard
     * Sets windows context to false for thread
     * Clears window
     */
    public void clear() {
        obstacles.clear();
        collectables.clear();
        lasers.clear();
        window.clear();
        try {
            window.setActive(false);
        } catch (ContextActivationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Draws an object to render window
     * @param e thing to draw
     */
    public void draw(Drawable e) {

        window.draw(e);
    }

    /**
     * calls display in render window
     */
    public void display() {

        window.display();
    }

    /**
     * Shows either a lose or win screen. updates state if win
     * @param win int representing if player has won or lost
     */
    public void showScreen(int win) {
        clear();
        gameSound.stop();
        
        if (win == 1) {
            state.addCoins(ui.getCoins() + (load.getReward()));
            state.setPlanet(load.getDest().getIndex());
            WinScreen screen = new WinScreen();
            screen.show(window,load);

        } else {
            LoseScreen screen = new LoseScreen();
            screen.show(window);
        }
    }

    /**
     * Pauses the game and opens the pause menu
     * Responds based off what user selects in pause menu
     */
    public void pause() {
        loop.setPaused();
        gameSound.stop();
        gameSound.Sound("start", 40.f);

        PauseMenu menu = new PauseMenu(window);
        int returnValue = menu.menu();
        menu = null;
        loop.unPause();

        
        if (returnValue == 1) {
            gameSound.stop();
            gameSound.Sound("inGame", 40.f);
        }
        else if(returnValue == 2){
            gameSound.stop();
            this.clear();
            loop.swapPause();
            loop.setActive(false);
            win = 2;
        }
    }

    /**
     * set context
     * @param set false or true for context
     */
    public void setActive(boolean set) {
        try {
            window.setActive(set);
        } catch (ContextActivationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets variables for win and ends loop
     */
    public void setLevelWin(){
        win = 1;
        loop.setActive(false);
        showScreen(win);
    }
    /**
     * Sets variables for lose and ends loop
     */
    public void setLevelFailed() {
        loop.setActive(false);
        showScreen(win);
    }
    /**
     * Gets scale of the window compared to target 1920 by 1080
     * @return vector containing scale, e.g 2,2 for a window of 3840 by 2160
     */
    public Vector2f getScale(){
        float newX = window.getSize().x/1920f;
        float newY = window.getSize().y/1080f;

        if(newX < newY){
            newY = newX;
        }
        else{
            newX = newY;
        }
        return new Vector2f(newX,newY);
    }

    /**
     * Get how many pizza thiefs are available to spawn on this level
     * @return thieves remaining as int 
     */
    public int getThief(){
        return pizzaThiefRemain;
    }
    /**
     * Decrease amount of remaining thieves by 1
     */
    public void removeOneThief(){
        pizzaThiefRemain --;
    }
}
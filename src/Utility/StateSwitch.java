package Utility;
import Menu.*;
import StatusScreens.StoryScreen;
import Level.*;
import Main.GameState;
import Main.SaveManager;

import org.jsfml.graphics.RenderWindow;

/**
 * Hub used for switching the stages of the game. All menus and levels should return here after they have finished
 */
public class StateSwitch {
    private RenderWindow window;
    private GameState state;
    private GameSound gameSound = new GameSound();
    
    /**
     * Creates StateSwitch
     * @param w window to draw on
     * @param g GameState of current game
     */
    public StateSwitch(RenderWindow w, GameState g){
        window = w;
        state = g;
    }

    /**
     * Starts the first menu. Starts playing sound
     * Calls next method based off value returned from menu
     */
    public void start(){
        MainMenu menu = new MainMenu(window);
        gameSound.Sound("start", 100.f);
        if(menu.menu()== 1){
            menu = null;
            StoryScreen show = new StoryScreen();
            show.show(window);
            show = null;
            startGameMenu();
        }
        else{
            SaveManager man = new SaveManager();
            man.loadSave(state);
            menu = null;
            startGameMenu();
        }
    }
    /**
     * Creates and starts a level
     */
    public void startLevel(){
        gameSound.pause();
        GameBoard level = new GameBoard(window,state);
        level.gameLevel();
        level = null;
        gameSound.Sound("start", 100.f);
        return;
    }

    /**
     * Starts a game menu. Return value determines next method
     */
    public void startGameMenu(){
        while(true){
            GameMenu gMenu = new GameMenu(window,state);
            int temp = gMenu.menu();
            gMenu = null;
        if(temp ==1){
            startLevel();
        }
        else if (temp == 2){
            startShop();
        }
        else{
            start();
        }

    }

    }
    /**
     * Starts shop menu
     */
    public void startShop(){
        ShopMenu menu = new ShopMenu(window,state);
        menu.open();
        menu = null;
        return;
    }
    /**
     * Gets current game state
     * @return the games current state
     */
    public GameState getState(){
        return state;
    }
}

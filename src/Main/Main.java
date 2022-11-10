package Main;

import org.jsfml.window.*;

import org.jsfml.graphics.*;
import Utility.*;

public class Main {


    /**
     * Main class. Creates render window and starts game
     * Vsysnc does not work on VM so is commented out 
     * 
     * 
     * !!! Don't apply vsync with framelimit - remove framelimit if you re enable vsync  !!!
     * 
     * Window is fullscreen based off monitor size
     */
    static final RenderWindow window = new RenderWindow(VideoMode.getDesktopMode(), "Space Express", WindowStyle.FULLSCREEN);

    public static void main(String[] args) {

        if(!System.getProperty("os.name").contains("Windows"))
            System.load(System.getProperty("user.dir")+"/libfixXInitThreads.so");   // This is needed to run on linux but will break windows
         
        //window.setVerticalSyncEnabled(true);
        //window.setKeyRepeatEnabled(false);
        window.setFramerateLimit(120);
        
        GameState state = new GameState();
        StateSwitch sw = new StateSwitch(window,state);
        sw.start();
    }
}
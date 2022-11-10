package Menu;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import Main.GameState;
import Main.SaveManager;

/**
 * Class used to create a game menu where player can select next action
 */
public class GameMenu {

    private RenderWindow window;
    private Boolean active = true;
    private GameState state;
    private int selected = 1;
    private Sprite button1;
    private Sprite button2;
    private Sprite button3;
    private Sprite button4;
    private RectangleShape rec;
    private Sprite background;

    /**
     * Create all objects for game menu and set position etc..
     * @param w window to draw
     * @param s state for current game. Used if player loads a game from this menu
     */
    public GameMenu(RenderWindow w, GameState s) {
        state = s;
        window = w;
        button1 = loadImage(Paths.get("Images/startLevelButton.png"));
        button2 = loadImage(Paths.get("Images/shopButton.png"));
        
        button3 = loadImage(Paths.get("Images/saveGame.png"));
        button4 = loadImage(Paths.get("Images/exitGameButton.png"));
        BackgroundPlanetBuilder loader = new BackgroundPlanetBuilder();
        background = loadImage(loader.getPath(s.getCurrentPlanet()));

        button1.setScale(getScale());
        button2.setScale(getScale());
        button3.setScale(getScale());
        button4.setScale(getScale());

        rec = new RectangleShape(new Vector2f(button1.getGlobalBounds().width,button1.getGlobalBounds().height));
        button1.setOrigin(button1.getGlobalBounds().width / 2, button1.getGlobalBounds().height / 2);
        button2.setOrigin(button2.getGlobalBounds().width / 2, button2.getGlobalBounds().height / 2);
        button3.setOrigin(button3.getGlobalBounds().width / 2, button3.getGlobalBounds().height / 2);
        button4.setOrigin(button4.getGlobalBounds().width / 2, button4.getGlobalBounds().height / 2);

        button1.setPosition(window.getSize().x/2,400 * getScale().y);
        button2.setPosition(window.getSize().x/2,575 * getScale().y);
        button3.setPosition(window.getSize().x/2,750 * getScale().y);
        button4.setPosition(window.getSize().x/2,925 * getScale().y);
        background.setScale((1920/background.getGlobalBounds().width) * getScale().x,(1080/background.getGlobalBounds().height) * getScale().y);


        rec.setOrigin(rec.getSize().x / 2, rec.getSize().y / 2);
        rec.setFillColor(Color.RED);

    }

    /**
     * Start the loop for this menu. Detects input
     * @return int represents selected option
     */
    public int menu() {
        active = true;
        drawButtons(selected);

        while (active) {
            Event events = window.pollEvent();
            if (events != null)
             { // Check for events
                switch (events.type) {
                    case KEY_PRESSED:
                        keyPressEventHandler(events.asKeyEvent().key);
                        break;
                    default:
                        break;
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }        
        }
        return selected;
    }

    /**
     * Key press event handler
     * @param events key pressed
     */
    public void keyPressEventHandler(Keyboard.Key events){
        switch (events) {
            case S:
            case DOWN:
               if(selected <4)
                   selected +=1;
                   drawButtons(selected);
                break;
            case W:
            case UP:
               if(selected >1)
                   selected -=1;
                   drawButtons(selected);
                break;
            case RETURN:
            case E:
                action(selected);
            default:
                break;

        }
    }

    /**
     * Load an image to a sprite
     * @param path path for image
     * @return newly created sprite with this image
     */
    public Sprite loadImage(Path path){
        Texture temp = new Texture();
        try{
            temp.loadFromFile(path);
            return new Sprite(temp);
        }
        catch(Exception e){
            System.out.println("Failed to load menu button image");
        }

        return null;
    }

    /**
     * Draw all buttons and draw indicator on selected button
     * @param selected the button selected
     */
    public void drawButtons(int selected){
        window.clear(new Color(100, 100,100));
        window.draw(background);

        if(selected == 1){
            rec.setPosition(button1.getPosition());
        }
        else if(selected == 2){
            rec.setPosition(button2.getPosition());
        }
        else if(selected == 3){
            rec.setPosition(button3.getPosition());
        }
        else if(selected == 4){
            rec.setPosition(button4.getPosition());
        }

        window.draw(rec);
        
        window.draw(button1);
        window.draw(button2);
        window.draw(button3);
        window.draw(button4);
        window.display();
    }
    /**
     * Called when player has selected an option
     * @param selected value selected
     */
    public void action(int selected){
        if(selected == 1){
            active = false;
        }
        else if(selected == 2){
            active = false;
        }
        else if(selected == 3){
            SaveManager save = new SaveManager();
            save.makeSave(state);
        }
        else if(selected == 4){
            System.exit(1);
        }
    }
    /**
     * Gets scale of window based off target 1920x1080
     * @return vector with scale
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
}
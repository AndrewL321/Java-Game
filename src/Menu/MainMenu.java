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

public class MainMenu {

    private RenderWindow window;
    private Boolean active = true;
    private int selected = 1;
    private Sprite button1;
    private Sprite button2;
    private Sprite button3;
    private RectangleShape rec;
    private Sprite background;
    private Sprite instructions;


    /**
     * Creates and draws all objects for main menu
     * @param w window to draw on
     */
    public MainMenu(RenderWindow w) {
       
        window = w; 
        button1 = loadImage(Paths.get("Images/newGameButton.png"));
        button2 = loadImage(Paths.get("Images/loadGameButton.png"));
        button3 = loadImage(Paths.get("Images/exitGameButton.png"));
        background = loadImage(Paths.get("Images/homescreen.png"));

        button1.setScale(getScale());
        button2.setScale(getScale());
        button3.setScale(getScale());


           
        button1.setOrigin(button1.getGlobalBounds().width/2,button1.getGlobalBounds().height/2);
        button2.setOrigin(button2.getGlobalBounds().width/2,button2.getGlobalBounds().height/2);
        button3.setOrigin(button3.getGlobalBounds().width/2,button3.getGlobalBounds().height/2);


        button1.setPosition(window.getSize().x/4,500 * getScale().y);
        button2.setPosition(window.getSize().x/4,700 * getScale().y);
        button3.setPosition(window.getSize().x/4,900 * getScale().y);

        instructions = loadImage(Paths.get("Images/gameInstructions2.png"));
        instructions.setScale(getScale().x * 2, getScale().y * 2);
        instructions.setPosition((button1.getPosition().x + button1.getGlobalBounds().width/1.5f),button1.getPosition().y - button1.getGlobalBounds().height/2);

        rec = new RectangleShape(new Vector2f(button1.getGlobalBounds().width,button1.getGlobalBounds().height));

        background.setScale((1920/background.getGlobalBounds().width) * getScale().x,(1080/background.getGlobalBounds().height) * getScale().y);


        rec.setOrigin(rec.getGlobalBounds().width/2,rec.getGlobalBounds().height/2);
        rec.setFillColor(Color.RED);
    }

    /**
     * Input detecting loop
     */
    public int menu() {
     
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
     * @param events pressed key
     */
    public void keyPressEventHandler(Keyboard.Key events){
        switch (events) {
            case S:
            case DOWN:
               if(selected <3)
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
     * Loads image from given path and creates sprite with it
     * @param path path to image
     * @return created sprite
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
     * Moves indicator to selected button and draws new placement
     * @param selected current selected button
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

        window.draw(rec);
        
        window.draw(button1);
        window.draw(button2);
        window.draw(button3);
        window.draw(instructions);
        window.display();
    }
    /**
     * When player has selected an option
     * @param selected option selected
     */
    public void action(int selected){
        if(selected == 1){
            active = false;
        }
        else if(selected == 2){
            active = false;
        }
        else if(selected == 3){
            System.exit(1);
        }
    }
    /**
     * Get scale of window based off target 1920x1080
     * @return vertor holding scale
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

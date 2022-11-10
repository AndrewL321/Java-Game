package Level;


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


public class PauseMenu {
    private RenderWindow window;
    private Boolean active = true;
    private int selected = 1;
    private Sprite[] buttons = new Sprite[3];
    private RectangleShape rec;
    private Sprite background;

    /**
     * Pause menu constructor. Creates and draws all images used for this menu
     * @param w Window to draw on
     */
    public PauseMenu(RenderWindow w) {
        window = w;
        window.clear();

        loadImagesButtons();
        loadBackground();
        loadRect();

        drawButtons(selected);
    }

    /**
     * Menu loop
     * @return option chosen by player
     */
    public int menu() {
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
     * Key event handler
     * @param events Key that was pressed
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
     * Loads an in image and creates sprite
     * @param path path of image
     * @return new sprite with texture applied
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
     * moves selected rectangle to correct place and draws it and everything else
     * @param selected button that is now selected
     */

    public void drawButtons(int selected){
        window.clear();
        window.draw(background);

        if(selected == 1){
            rec.setPosition(buttons[0].getPosition());
        }
        else if(selected == 2){
            rec.setPosition(buttons[1].getPosition());
        }
        else if(selected == 3){
            rec.setPosition(buttons[2].getPosition());
        }

        window.draw(rec);
        
        for(Sprite cur : buttons){
            window.draw(cur);
        }
        window.display();
    }
    /**
     * if option selected respond
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
     * Get scale of window compared to target 1920x1080
     * @return vector of scale ratio
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
     * sets the scale of a sprite to correct ratio
     * @param x sprite to be adjusted
     */
    public void setScale(Sprite x){
        x.setScale(getScale());
    }
    /**
     * Loads images for buttons and sets position
     */
    private void loadImagesButtons(){
        buttons[0] = loadImage(Paths.get("Images/resumeButton.png"));
        buttons[1] = loadImage(Paths.get("Images/exitLevelButton.png"));
        buttons[2] = loadImage(Paths.get("Images/exitGameButton.png"));

        for(int i=0; i<buttons.length;i++){
            setScale(buttons[i]);
            buttons[i].setOrigin(buttons[i].getGlobalBounds().width/2,buttons[i].getGlobalBounds().height/2);
        }

        buttons[0].setPosition(window.getSize().x/2,500 *(getScale().y));
        buttons[1].setPosition(window.getSize().x/2,700 *(getScale().y));
        buttons[2].setPosition(window.getSize().x/2,900 *(getScale().y));
    }

    /**
     * loads the background for this page and set correct size
     */
    private void loadBackground(){
        background = loadImage(Paths.get("Images/homescreen.png"));
        background.setScale((1920/background.getGlobalBounds().width) * getScale().x,(1080/background.getGlobalBounds().height) * getScale().y);
    }

    /**
     * Creates rectangle used to show what is selected
     */
    private void loadRect(){
        rec = new RectangleShape(new Vector2f(buttons[1].getGlobalBounds().width,buttons[1].getGlobalBounds().height));
        rec.setOrigin(rec.getGlobalBounds().width/2,rec.getGlobalBounds().height/2);
        rec.setFillColor(Color.RED);

    }
}

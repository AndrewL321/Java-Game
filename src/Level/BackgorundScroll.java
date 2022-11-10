package Level;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/**
 * Background scroll class. Uses 2 backgrounds to create illusion of endless background scrolling
 */

public class BackgorundScroll {
        
    private Sprite background1;
    private Sprite background2;

    private final float SPEED = -0.0000002f;            // Speed to scroll at. As pixels per update, will be * dT for total movement

    /**
     * Constructor
     * @param uiSize The amount of space used by UI
     * @param window GameBoard this is to be used on
     */
    public BackgorundScroll(int uiSize, GameBoard window){
              
        
        int temp = (int) (Math.random() * 3 + 1);       //Random number to decide which image to load

        background1 = loadBackground(temp);
        background2 = loadBackground(temp);

        background1.setScale(1 * window.getScale().x,((1080-uiSize)/background1.getGlobalBounds().height) * window.getScale().y);
        background2.setScale(1 * window.getScale().x,((1080-uiSize)/background2.getGlobalBounds().height) * window.getScale().y);


        background1.setPosition(0,uiSize);
        background2.setPosition(background2.getGlobalBounds().width,uiSize);
    }

    /**
     * Method used to update position of backgrounds. Will move to end of other backgorund when off screen
     * @param dT time step as 1sec/tickrate
     */
    public void update(double dT){
        if(background1.getPosition().x < -background1.getGlobalBounds().width){
            background1.setPosition(background2.getPosition().x + background2.getGlobalBounds().width, background1.getPosition().y);
        }
        if(background2.getPosition().x < -background2.getGlobalBounds().width){
            background2.setPosition(background1.getPosition().x + background2.getGlobalBounds().width, background1.getPosition().y);
        }
        background1.move((float)(SPEED * dT),0);
        background2.move((float)(SPEED * dT),0);        
    }

    /**
     * Draw method for the backgrounds, only draws if on screen
     * @param window GameBoard used to draw
     * @param delta Time between time step as value 0-1
     * @param dT The time step as 1sec/tickrate
     */
    public void draw(GameBoard window, double delta, double dT){
        float newX = background1.getPosition().x;
        float newXTwo = background2.getPosition().x;


        background1.setPosition((float)((newX * (1 - delta)) + ((newX + SPEED * dT) * delta)), background1.getPosition().y);          // Set position with interpolation
        background2.setPosition((float)((newXTwo * (1 - delta)) + ((newXTwo + SPEED * dT) * delta)), background2.getPosition().y);
    
        if(background1.getPosition().x < window.getSize().x)
            window.draw(background1);
        if(background2.getPosition().x < window.getSize().x)
            window.draw(background2);
        background1.setPosition(newX,background1.getPosition().y);                    // Move back to position before interpolation
        background2.setPosition(newXTwo,background2.getPosition().y); 
    }

    /**
     * Used to load a texture
     * @param path path of texture
     * @return A loaded texture, null if failed to load
     */
    private Texture loadTexture(Path path){
        Texture text = new Texture();
        try{
            text.loadFromFile(path);
        }
        catch(Exception e){
            System.out.println("Failed to load background");
            return null;
        }
        text.setSmooth(true);
        text.setRepeated(true);
        return text;
    }
    /**
     * Used to randomally load an image
     * @param ran random number 1-3
     * @return a sprite of the background image
     */
    private Sprite loadBackground(int ran){
        Path path;
        if(ran == 1){
            path= Paths.get("Images/back1.png");
        }
        else if(ran == 2){
            path = Paths.get("Images/back2.png");
        }
        else{
            path = Paths.get("Images/back3.png");
        }

        Texture backTex = loadTexture(path);
        return new Sprite(backTex);
    }
}

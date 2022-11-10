package StatusScreens;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import Utility.LevelLoader;

/**
 * Class used to display information needed before level start
 */
public class StartLevelScreen extends StatusAbstract {

    private int capacity;

    public StartLevelScreen(int c){
        capacity = c;
    }
    
    /**
     * Creates and then shows this screen and loops input detection until space pressed
     * @param w window to draw on
     */
    public void show(RenderWindow w,LevelLoader l){

        Sprite back = loadSprite(Paths.get("Images/startBack.png"));
        back.setScale((1920/back.getGlobalBounds().width) * getScale(w).x,(1080/back.getGlobalBounds().height) * getScale(w).y);
        w.draw(back);

        Text message = new Text("You are travelling from " + l.getCurrent().getName() + " to " + l.getDest().getName() + "\nSurvive long enough to reach your destination",loadFont());
        message.setColor(Color.WHITE);
        message.setScale(getScale(w).x * 1.5f,getScale(w).y * 1.5f);
        message.setPosition((w.getSize().x/2) - message.getGlobalBounds().width/2,w.getSize().y/2);

        Text message2 = new Text("Distance = " +(int) Math.abs(l.getCurrent().getLocation() - l.getDest().getLocation()) +"\nPizzas to deliver = " + l.calcLives(l.getDistance()) +" + " + capacity + " (extra capacity)" + "\nPress Space to continue",loadFont());
        message2.setScale(getScale(w));
        message2.setPosition(message.getPosition());
        message2.move(-message2.getGlobalBounds().width / 4,message2.getGlobalBounds().height * 2.5f);

        Sprite instructions = loadSprite(Paths.get("Images/gameInstructions.png"));
        instructions.setScale(getScale(w).x * 0.8f, getScale(w).y * 0.8f);
        instructions.setPosition(message2.getPosition().x + instructions.getGlobalBounds().width*1.5f,message2.getPosition().y - message2.getGlobalBounds().height);


        w.draw(message);
        w.draw(message2);
        w.draw(instructions);


        Sprite image1 = loadSprite(l.getCurrent().getPath());
        Sprite image2 = loadSprite(l.getDest().getPath());

        origin(image1);
        origin(image2);

        image1.setPosition((w.getSize().x/4), w.getSize().y/4);
        image2.setPosition((w.getSize().x/4)*3, w.getSize().y/4);

        w.draw(image1);
        w.draw(image2);

        loop(w);
    }

     /**
     * Gets the scale for scalling objects
     * @return vector cotaining intended scale
     */
    public Vector2f getScale(RenderWindow window){
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

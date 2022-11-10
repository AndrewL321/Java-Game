package StatusScreens;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

/**
 * Screen shown when player loses a level
 */
public class LoseScreen {
    public LoseScreen(){

    }
    /**
     * Shows this screen and loops input detection until space pressed
     * @param w window to draw on
     */
    public void show(RenderWindow w){
        Font font = new Font();
        Path fontPath = Paths.get("Images/font.ttf");
        
        try{
            font.loadFromFile(fontPath);
        }
        catch(Exception e){
            System.out.println("Couldn't load font");
        }

        Text message = new Text("Level Failed",font);
        message.setColor(Color.RED);
        message.setScale(getScale(w));
        message.setPosition((w.getSize().x/2) - message.getGlobalBounds().width/2,w.getSize().y/2);

        Text message2 = new Text("Press space to continue",font);
        message2.setScale(getScale(w));
        message2.setPosition(message.getPosition());
        message2.move(-message2.getGlobalBounds().width / 4,message2.getGlobalBounds().height * 4);


        w.draw(message);
        w.draw(message2);
        w.display();

        while (true) {


            Event events = w.pollEvent();


            if (events != null)
             { // Check for events

                switch (events.type) {

                    case KEY_RELEASED:
                        switch (events.asKeyEvent().key){
                            case SPACE:
                                w.clear();
                                return;
                            default:
                            break;
                        }
                        
                        
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

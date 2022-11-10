package StatusScreens;

import java.nio.file.Paths;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;

/**
 * Screen shown when player retires
 */
public class RetireScreen extends StatusAbstract {
    public RetireScreen(){
    }

    /**
     * Shows this screen and loops input detection until space pressed
     * @param w window to draw on
     */
    @Override
    public void show(RenderWindow w){
        w.clear();
        Text message = new Text("Congratulations you have bought the retirement package!!!\n\nPress Space to continue", loadFont());
        message.setScale(getScale(w));
        message.setOrigin(message.getGlobalBounds().width/2,message.getGlobalBounds().height/2);
        message.setPosition(w.getSize().x/2,(w.getSize().y/2) + (300 *getScale(w).y));

        w.draw(message);

        Sprite image = loadSprite(Paths.get("Images/retirementPicture.png"));
        image.setOrigin(image.getGlobalBounds().width/2,image.getGlobalBounds().height/2);
        image.setPosition(message.getPosition().x,message.getPosition().y - (380 * getScale(w).y));

        w.draw(image);


        this.loop(w);
       
    }
    
}

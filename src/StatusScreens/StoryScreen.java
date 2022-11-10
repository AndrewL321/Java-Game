package StatusScreens;

import java.nio.file.Paths;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

/**
 * Screen showed when starting a new game with story
 */
public class StoryScreen extends StatusAbstract {
    public StoryScreen(){

    }
    /**
     * Shows this screen and loops input detection until space pressed
     * @param w window to draw on
     */
    public void show(RenderWindow window){
        Sprite back = loadSprite(Paths.get("Images/intro.png"));
        back.setScale((1920/back.getGlobalBounds().width) * getScale(window).x,(1080/back.getGlobalBounds().height) * getScale(window).y);
        window.clear();
        window.draw(back);
        loop(window);
    }
}

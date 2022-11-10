package StatusScreens;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;


/**
 * Abstract class used to create status screens
 * Static pages that display images until space is pressed then returns
 */
public abstract class StatusAbstract {

    public void show(RenderWindow w) {

    }

    public void loop(RenderWindow w) {

        w.display();
        while (true) {

            Event events = w.pollEvent();

            if (events != null) { // Check for events

                switch (events.type) {

                    case KEY_RELEASED:
                        switch (events.asKeyEvent().key) {
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

    public Font loadFont() {
        Font font = new Font();
        Path fontPath = Paths.get("Images/font.ttf");
        try {
            font.loadFromFile(fontPath);
        } catch (Exception e) {
            System.out.println("Couldn't load font");
        }
        return font;
    }

    public Sprite loadSprite(Path p) {
        Texture temp = new Texture();
        try {
            temp.loadFromFile(p);
        } catch (IOException e) {
            System.out.println("Failed to load image " + p);
            e.printStackTrace();
            return null;
        }
        Sprite s = new Sprite(temp);
        return s;
    }

    public void origin(Sprite x){
        x.setOrigin(x.getGlobalBounds().width/2,x.getGlobalBounds().height/2);
    }
}
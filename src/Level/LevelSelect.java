package Level;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

import Main.GameState;
import Utility.LevelLoader;

public class LevelSelect {
     private int[] dests = new int[3];
     private ArrayList<Drawable> draw = new ArrayList<Drawable>();
     private RenderWindow window;
     private int[] dist = new int[3];
     private RectangleShape rec;
     private int selected = 0;
     private Sprite button1;
     private Sprite button2;
     private Sprite button3;
     private Sprite backButton;
     private boolean active = true;
     private GameState state;


     /**
      * LevelLoader constructor. Creates 3 instances of level randomly. User can then choose
      * @param curPlanet Current planet int. Can't load this destinatiom
      * @param s Game state of current game
      * @param w Window to draw on
      * @return Level that is selected by user
      */
    public LevelLoader getLevel(int curPlanet,GameState s,RenderWindow w){
        window = w;
        window.clear();
        state = s;

        Sprite back = loadSprite(Paths.get("Images/shopBackground.png"));
        back.setScale((1920/back.getGlobalBounds().width),(1080/back.getGlobalBounds().height));
        draw.add(back);

        for(int i = 0; i< dests.length; i++){
            dests[i] = getRandom(i,curPlanet);
        }

        Sprite[] planets = new Sprite[3];

        float offset = (window.getSize().x / 4);

        for(int i =0;i<planets.length;i++){
            planets[i] = loadSprite(state.getPlanet(dests[i]).getPath());
            origin(planets[i]);
            planets[i].setScale(0.4f,0.4f);
            
            planets[i].setPosition(offset, window.getSize().y/4);
            draw.add(planets[i]);
            offset += (window.getSize().x / 4);

            dist[i] = Math.abs(state.getPlanet(dests[i]).getLocation() - state.getPlanet(state.getCurrentPlanet()).getLocation());
            Text distanceText = new Text("Distance = " + dist[i], loadFont());
            distanceText.setPosition(planets[i].getGlobalBounds().left,planets[i].getPosition().y + planets[i].getGlobalBounds().height);
            draw.add(distanceText);
            
        }
        Text text1 = new Text("Easy\nReward = " + dist[0]/30 +"\nLives = " + calcLives(dist[0]),loadFont());
        text1.setColor(Color.GREEN);
        text1.setPosition(planets[0].getGlobalBounds().left,planets[0].getPosition().y + planets[0].getGlobalBounds().height  * 1.3f);

        Text text2 = new Text("Medium\nReward = " + dist[1]/25 +"\nLives = " + calcLives(dist[1]) + "\nMore + Faster Obstacles", loadFont());
        text2.setColor(Color.YELLOW);
        text2.setPosition(planets[1].getGlobalBounds().left,planets[1].getPosition().y + planets[1].getGlobalBounds().height  * 1.3f);


        Text text3 = new Text("Hard\nReward = " + dist[2]/15+"\nLives = " + calcLives(dist[2]) + "\nMore Thieves + Faster", loadFont());
        text3.setColor(Color.RED);
        text3.setPosition(planets[2].getGlobalBounds().left,planets[2].getPosition().y + planets[2].getGlobalBounds().height  * 1.3f);



        draw.add(text1);
        draw.add(text2);
        draw.add(text3);


        button1 = loadSprite(Paths.get("Images/select.png"));
        button2 = loadSprite(Paths.get("Images/select.png"));
        button3 = loadSprite(Paths.get("Images/select.png"));

        button1.setPosition(text1.getGlobalBounds().left,text1.getGlobalBounds().top + (text3.getGlobalBounds().height * 1.5f));
        button2.setPosition(text2.getGlobalBounds().left,text1.getGlobalBounds().top + (text3.getGlobalBounds().height * 1.5f));
        button3.setPosition(text3.getGlobalBounds().left,text1.getGlobalBounds().top + (text3.getGlobalBounds().height * 1.5f));

        rec = new RectangleShape(new Vector2f(button1.getGlobalBounds().width,button1.getGlobalBounds().height));
        rec.setFillColor(Color.RED);
        drawButtons(selected);
        draw.add(rec);

        backButton = loadSprite(Paths.get("Images/back.png"));
        backButton.setPosition(button1.getPosition().x - button1.getGlobalBounds().width*1.5f, window.getSize().y - backButton.getGlobalBounds().height * 1.5f);



        draw.add(backButton);
        draw.add(button1);
        draw.add(button2);
        draw.add(button3);

        drawAll();
        menu(window);
        return selected(selected);
    }

    /**
     * Recursive function. Gens random number and calls itself if number is a repeat
     * @param index position of number in number array
     * @param curPlanet current planet number. Not to be repeated
     * @return new random number
     */
    public int getRandom(int index, int curPlanet){
        int num;
        num = (int)(Math.random() * 8);
        if(num == curPlanet){
            num = getRandom(index, curPlanet);
        }
        for(int i =0; i < index; i++){
            if(dests[i] == num){
                num = getRandom(index, curPlanet);
            }
        }

        return num;

    }
    /**
     * Loads a sprite
     * @param path path of texture
     * @return new sprite
     */
    public Sprite loadSprite(Path path) {
        Texture temp = new Texture();
        try {
            temp.loadFromFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp.setSmooth(true);
        return new Sprite(temp);
    }

    /**
     * Creates a loop that runs and detects user input
     * @param window window to draw on
     * @return int representing what option the user selected
     */
    public int menu(RenderWindow window) {
        
        while (active) {
            org.jsfml.window.event.Event events = window.pollEvent();

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
            case D:
            case RIGHT:
                if(selected < 2){
                    selected ++;
                    drawButtons(selected);
                    drawAll();
                }
                break;
            case A:
            case LEFT:
                if(selected >-1){
                    selected --;
                    drawButtons(selected);
                    drawAll();
                }
                break;
            case E:
            case RETURN:
                active = false;
                break;
            case W:
            case UP:
                if(selected < 0){
                    selected = 0;
                    drawButtons(selected);
                    drawAll();
                }
                break;
            case S:
            case DOWN:
                if(selected >= 0){
                    selected = -1;
                    drawButtons(selected);
                    drawAll();
                }
                break;    
            
            default:
                break;

        }
    }

    /**
     * Set the origin of a sprite to centre
     * @param sprite to set origin of
     */
    public void origin(Sprite x){
        x.setOrigin(x.getGlobalBounds().width/2,x.getGlobalBounds().height/2);
    }  

    /**
     * loads a font
     * @return loaded font
     */
    public Font loadFont(){
        Font font = new Font();
        Path image = Paths.get("Images/font.ttf");
        
        try{
            font.loadFromFile(image);
        }
        catch(Exception e){
            System.out.println("Couldn't load font");
        }
        return font;
    }
    /**
     * draws all objects that are part of this class
     */
    public void drawAll(){
        window.clear();
        for(Drawable cur : draw){
            window.draw(cur);
        }
        window.display();
    }
    /**
     * Moves selected indicator rectangle to selected button
     * @param selected button that is now selected
     */
    public void drawButtons(int selected){
        if(selected == 0){
            rec.setPosition(button1.getPosition());
        }
        else if(selected == 1){
            rec.setPosition(button2.getPosition());
        }
        else if(selected == 2){
            rec.setPosition(button3.getPosition());
        }
        else{
            rec.setPosition(backButton.getPosition());
        }
    }

    /**
     * Creates a level loader based off user choice
     * @param sel selceted level
     * @return New level loader to be returned
     */
    public LevelLoader selected(int sel){
        if(sel == 0){
            return new LevelLoader(dests[sel], state,"EASY");
        }
        else if(sel == 1){
            return new LevelLoader(dests[sel], state,"MEDIUM");
        }
        else if(sel == 2){
            return new LevelLoader(dests[sel], state,"HARD");
        }
        else{
            return null;
        }
    }

    /**
     * Calculates user lives based off distance
     * @param distance dist to travel
     * @return lives total
     */
    public int calcLives(float distance){
        return (int)(distance/2000 + 0.5);
    }
}

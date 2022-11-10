package Level;

import java.util.ArrayList;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import Main.GameState;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UiBuilder implements Drawable{

    private ArrayList<Drawable> elements = new ArrayList<Drawable>();

    private Sprite bar2;

    private int score = 0;

    private Path image = Paths.get("Images/menuButton.png");         // Path for image

    private Texture imageText = new Texture();

    private Text coins;
    private Text lives;
    private Text distanceText;
    private float distance;

    private Sprite[] pizzas;
    private Texture[] textures = new Texture[4];

    private Sprite equipSprite;

    private Path[] powerups = new Path[4];
    /**
     * Constructor. Builds all objects used as part of ui and sets their position
     * @param size Size for the ui to be
     * @param window window to draw
     * @param playerLives the players lives used to draw correct amount on the ui
     * @param dist distance player has to travel
     * @param state current game state
     */
    public UiBuilder(int size,GameBoard window, int playerLives,int dist,GameState state){

        RectangleShape bar = new RectangleShape(new Vector2f(window.getSize().x,size));
        bar.setPosition(0,0);

        loadPaths();
        loadTextures();

        equipSprite = new Sprite(loadTexture(powerups[0]));
        equipSprite.setScale(window.getScale().x * 0.3f,window.getScale().y * 0.3f);
        equipSprite.setPosition(0f + equipSprite.getGlobalBounds().width * 0.5f,size + equipSprite.getGlobalBounds().height * 0.5f);
        equipSprite.setColor(new Color(252, 252,252,120));

        bar.setFillColor(new Color(100, 100,100));
        elements.add(bar);
        try{
            imageText.loadFromFile(image);
        }
        catch(Exception e){
            System.out.println("Failed to load menu button image");
        }
        bar2 = new Sprite(imageText);
        bar2.setScale(window.getScale());
        bar2.setPosition(window.getSize().x/30,size/5);

        Font font = new Font();
        image = Paths.get("Images/font.ttf");
        
        try{
            font.loadFromFile(image);
        }
        catch(Exception e){
            System.out.println("Couldn't load font");
        }

        coins = new Text(" : " + state.getCoins(),font);
        coins.setScale(window.getScale());
        elements.add(bar2);
        coins.setPosition(1800 * window.getScale().x,35 * window.getScale().y);
        coins.setScale(window.getScale());
        elements.add(coins);

        image = Paths.get("Images/coin1.png");

        Texture coinText = new Texture();
        try{
            coinText.loadFromFile(image);
        }
        catch(Exception e){
            System.out.println("Couldn't load coin image for UI");
        }

        Sprite coinImage = new Sprite(coinText);

        coinImage.setScale(0.3f * window.getScale().x,0.3f * window.getScale().y);
        coinImage.setPosition(1750*window.getScale().x,30 * window.getScale().y);

        elements.add(coinImage);
        
        lives = new Text("Lives : ",font);
        lives.setScale(window.getScale());
        lives.setPosition(525* window.getScale().x,35 * window.getScale().x);

        elements.add(lives);
        addLifeSprite(playerLives,window);
        addDistance(dist,window,size);
        elements.add(equipSprite);
        distance = dist;
        score = state.getCoins();
        
    }

    /**
     * draws all elements of ui
     * @param window to draw on
     * @param state unused
     */
    @Override
    public void draw(RenderTarget window, RenderStates state) {
        for(Drawable current : elements){
            window.draw(current);
        }
    }

    /**
     * Mouse press event handler
     * @param co co ordinates of press
     * @return if it is inside the menu button
     */
    public int mousePressHandler(Vector2f co) {

        if(bar2.getGlobalBounds().contains(co)){
            return 1;
        }
        return 0;
    }

    /**
     * Adds score to total and adjusts string to show this
     * @param x amount to add
     */
    public void addScore(int x){
        score += x;
        coins.setString(" : " + score);
    }

    /**
     * Removes a life from ui
     * @param lives to remove
     */
    public void updateLives(int lives){
        elements.remove(pizzas[lives]);
        pizzas[lives] = null;

    }


    /**
     * Loads add adds correct amount of sprite used for life
     * @param amount how many to show
     * @param window window to draw on
     */
    public void addLifeSprite(int amount,GameBoard window){
        image = Paths.get("Images/pizza.png");
        Texture pizzaText = new Texture();
        try{
            pizzaText.loadFromFile(image);
        }
        catch(Exception e){
            System.out.println("Failed to load pizza image");
        }
        pizzaText.setSmooth(true);

        pizzas = new Sprite[amount];

        int offset = 0;

        for(int i = 0; i < amount;i++){
            pizzas[i] = new Sprite(pizzaText);
            pizzas[i].setScale(1.5f * window.getScale().x,1.5f * window.getScale().y);
            pizzas[i].setPosition((675 + offset) * window.getScale().x,35 * window.getScale().y);
            offset += 75 * window.getScale().x;
            elements.add(pizzas[i]);
        }
    }

    /**
     * Adds distance value to ui
     * @param d total distance
     * @param window window to draw
     * @param size size of the ui, for placing in correct place
     */
    public void addDistance(int d, GameBoard window,int size){
        Font font = new Font();
        image = Paths.get("Images/font.ttf");
        
        try{
            font.loadFromFile(image);
        }
        catch(Exception e){
            System.out.println("Couldn't load font");
        }
        distanceText = new Text("Remain :" + d,font);
        distanceText.setScale(window.getScale());
        distanceText.setColor(new Color(252,252,252,150));
        distanceText.setPosition(window.getSize().x - distanceText.getGlobalBounds().width,size + distanceText.getGlobalBounds().height);
        elements.add(distanceText);
    }
    /**
     * updates the remaining string
     * @param dist distance to take off
     * @return new distance
     */
    public float updateDistance(double dist){
        if(distance>0){
            distance -= dist;
        }
        else{
            distance = 0;
        }
        distanceText.setString("Remain : " + (int)distance);
        return distance;
    }

    /**
     * Returns current coins
     * @return total coins
     */
    public int getCoins(){
        return score;
    }

    /**
     * Loads paths for powerup images into array
     */
    public void loadPaths(){
        powerups[0] = Paths.get("Images/empty.png");
        powerups[1] = Paths.get("Images/laserShopFrame.png");
        powerups[2] = Paths.get("Images/hyperdriveShopFrame.png");
        powerups[3] = Paths.get("Images/shieldShopFrame.png");
    }

    /**
     * Loads all powerup textures for ui
     */
    private void loadTextures(){

        for(int i = 0; i<  powerups.length;i++){

            textures[i] = loadTexture(powerups[i]);
        }
        
    }

    /**
     * load a texture
     * @param path of texture
     * @return loaded texture, null if failed
     */
    public Texture loadTexture(Path path){
        Texture temp = new Texture();
        try {
            temp.loadFromFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * Set ui to show current power up user has
     * @param i name of power up to show image for
     */
    public void setPower(String i){
        if(i == "LASER")
            equipSprite.setTexture(textures[1]);
        if(i == "HYPER")
            equipSprite.setTexture(textures[2]);
        if(i == "SHIELD")
            equipSprite.setTexture(textures[3]);
    }
    /**
     * Set power up ui image back to default
     */
    public void resetPower(){
        equipSprite.setTexture(textures[0]);
    }

}

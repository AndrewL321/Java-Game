package Menu;

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
import org.jsfml.window.event.Event;

import Main.GameState;
import StatusScreens.RetireScreen;


public class ShopMenu {

    
    private final int  UPGRADE_COST = 250;
    private final int SHIP_COST = 500;
    private final int RETIRE_COST = 10000;

    private Sprite back;
    private ArrayList<Drawable> draw = new ArrayList<Drawable>();

    private RenderWindow window;
    private GameState state;
    private int xSelected = 1;
    private int ySelected = 1;
    private RectangleShape rect;
    private boolean active;
    private Sprite[] ships = new Sprite[4];
    private Sprite[] buttons = new Sprite[9];
    private Sprite[] bars = new Sprite[3];
    private Sprite[] coins = new Sprite[4];
    private Text[] cost = new Text[4];
    private Text currentCoins = new Text();
    private Sprite[] upgradeCostCoin = new Sprite[3];
    private Text[] upgradeCostText = new Text[3];
    private Texture[] descImages = new Texture[8];
    private Sprite curDesc;


    /**
     * Creates and draws all object for shop menu
     * @param w window to draw on
     * @param s current game state
     */
    public ShopMenu(RenderWindow w,GameState s) {
        state = s;
        window = w;


        rect = new RectangleShape();
        rect.setFillColor(Color.RED);
        draw.add(rect);


        loadShips();
        setShips(ships);
        loadButtons();
        loadUpgradeSprites();
        buttons[7] = loadSprite(Paths.get("Images/backButton.png"));

        
        draw.add(buttons[7]);
        back = loadSprite(Paths.get("Images/shopBackground.png"));
        back.setScale((1920/back.getGlobalBounds().width) * getScale().x,(1080/back.getGlobalBounds().height) * getScale().y);
        origin(buttons[7]);
        buttons[7].setScale((buttons[0].getGlobalBounds().width/buttons[7].getGlobalBounds().width), (buttons[0].getGlobalBounds().height/buttons[7].getGlobalBounds().height));
        buttons[7].setPosition(0 + buttons[7].getGlobalBounds().width * getScale().x, (window.getSize().y * getScale().y) - buttons[7].getGlobalBounds().height - (10*getScale().y));



        loadRetireButton();
        

       
        loadCoins();
        loadDescImages();

        rect.setSize(new Vector2f((buttons[1].getGlobalBounds().width + (8*getScale().x)),(buttons[1].getGlobalBounds().height +(8*getScale().y))));
        rect.setOrigin(rect.getSize().x/2, rect.getSize().y/2);
        setSelected();

    }

    /**
     * Shop menu loop for detecting input
     */
    public void open() {
        drawAll();
        active = true;

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
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return;

    }

    /**
     * Creates sprite with image from given path
     * @param path path of image to use
     * @return new sprite with image
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
     * Draws everything for this menu
     */
    public void drawAll(){
        window.clear();
        window.draw(back);
        for(Drawable current : draw){
            window.draw(current);
        }
        window.display();
    }

    /**
     * Loads the prices for objects
     */
    public void loadCoins(){
        Font font = loadFont();
        Sprite coin = loadSprite(Paths.get("Images/coin1.png"));
        coin.setScale(0.3f*getScale().x,0.3f*getScale().y);

        currentCoins = new Text(" : " + state.getCoins(), font);
        currentCoins.setScale(getScale());

        coin.setOrigin(coin.getGlobalBounds().width/2,coin.getGlobalBounds().height/2);
        currentCoins.setOrigin(currentCoins.getGlobalBounds().width/2,currentCoins.getGlobalBounds().height/2);

        currentCoins.setPosition(window.getSize().x - currentCoins.getGlobalBounds().width, 50);
        coin.setPosition(currentCoins.getPosition().x - coin.getGlobalBounds().width * 2 - 10, 50);

        draw.add(currentCoins);
        draw.add(coin);
    }

    /**
     * Load font used on this menu
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
     * Sets sprites origin to centre
     * @param x sprite to set origin of
     */
    public void origin(Sprite x){
        x.setOrigin(x.getGlobalBounds().width/2,x.getGlobalBounds().height/2);
    }  


    /**
     * Moves indicator to selected button and changes description image to relevant one
     */
    public void setSelected(){
        
        if(ySelected == 1){
            rect.setPosition(buttons[xSelected - 1].getPosition());
            setDesc(xSelected-1);
        }
        else if(ySelected > 1 && xSelected == 2){
            rect.setPosition(buttons[8].getPosition());
            setDesc(7);
        }
        else if(ySelected == 2){
            rect.setPosition(buttons[4].getPosition());
            setDesc(4);
        }
        else if(ySelected == 3){
            rect.setPosition(buttons[5].getPosition());
            setDesc(5);
        }
        else if(ySelected == 4){
            rect.setPosition(buttons[6].getPosition());
            setDesc(6);
        }
        else if(ySelected == 5){
            rect.setPosition(buttons[7].getPosition());
            hideDesc();
           
        }
    
    }

    /**
     * Key press event handler
     * @param events key pressed
     */
    public void keyPressEventHandler(Keyboard.Key events){


        switch (events) {
            case D:
            case RIGHT:
                if(ySelected >1){
                    xSelected = 2;
                    setSelected();
                    drawAll();
                    break;
                }
                if(xSelected < 4){
                    xSelected ++;
                    setSelected();
                    drawAll();
                    break;
                }
                break;

            case A:
            case LEFT:
                if(ySelected > 1){
                    ySelected =4;
                    xSelected = 1;
                    setSelected();
                    drawAll();
                  break;
                }
                if(xSelected > 1){
                    xSelected --;
                    setSelected();
                    drawAll();
                    break;
                }
                break;
            case S:
            case DOWN:
                if(xSelected <= 2 && ySelected == 1){
                    xSelected = 1;
                }
                if(xSelected >2 && ySelected == 1){
                    xSelected = 2;
                }
                if(ySelected < 5){
                    ySelected ++;
                    setSelected();
                    drawAll();
                    break;
                }
                break;
            case W:
            case UP:
                if(xSelected == 2){
                    xSelected = 4;
                    ySelected =1;
                    setSelected();
                    drawAll();
                }
                if(ySelected > 1){
                    ySelected --;
                    setSelected();
                    drawAll();
                    break;
                }
                break;
            case RETURN:
            case E:
                if(ySelected == 1){
                    updateShip(xSelected);
                    drawAll();
                    break;
                }
                else if(ySelected > 1 && xSelected == 2){
                    if(!state.getRetire() && costTest(RETIRE_COST)){
                        RetireScreen s = new RetireScreen();
                        state.setRetire();
                        s.show(window);
                        swapRetire();
                        drawAll();
                    }
                    break;
                }
                else if(ySelected == 5){
                    active = false;
                    break;
                }
                else{
                    updateBar(ySelected);
                    drawAll();
                    break;
                }

            case ESCAPE:
                active = false;
                break;
            
            default:
                break;

        }

    }

    /**
     * Loads sprites for ships
     */
    public void loadShips(){
        ships[0] = loadSprite(Paths.get("Images/mainShip.png"));
        ships[1] = loadSprite(Paths.get("Images/fastShip.png"));
        ships[2] = loadSprite(Paths.get("Images/laserShip.png"));
        ships[3] = loadSprite(Paths.get("Images/strongShip.png"));

    }

    /**
     * Sets ship sprite to correct places
     * @param ships ships to place
     */
    public void setShips(Sprite[] ships){
        int width = (int)((window.getSize().x - (ships[1].getGlobalBounds().width * getScale().x * 0.8f)));
        int offset = 0;
        for(Sprite ship : ships){
            ship.setScale(0.8f * getScale().x,0.8f*getScale().y);
            origin(ship);
            ship.setPosition(ship.getGlobalBounds().width + offset, 100 * getScale().y);
            offset += (width/4);
            draw.add(ship);
        }
        addButtons(ships);

    }
    /**
     * Adds buttons to menu
     * @param Ships ships to add buttons for
     */
    public void addButtons(Sprite[] Ships){
        for(int i = 0; i < ships.length; i++){
            buttons[i] = loadButton(i);
            origin(buttons[i]);
            buttons[i].setScale(getScale());
            buttons[i].setPosition(ships[i].getPosition().x, ships[i].getPosition().y + buttons[i].getGlobalBounds().height * 2);
            draw.add(buttons[i]);
            if(state.getUnlocked(i) == false){
                coins[i] = loadSprite(Paths.get("Images/coin1.png"));
                coins[i].setScale(0.2f*getScale().x,0.2f*getScale().y);
                origin(coins[i]);
                coins[i].setPosition(buttons[i].getPosition().x - buttons[i].getGlobalBounds().width/2,buttons[i].getPosition().y + coins[i].getGlobalBounds().height);
                draw.add(coins[i]);

                
                Font font = loadFont();
                cost[i] = new Text("" + SHIP_COST,font);
                cost[i].setScale(getScale());
                cost[i].setOrigin(cost[i].getGlobalBounds().width/2,cost[i].getGlobalBounds().height/2);
                cost[i].setPosition(coins[i].getPosition().x + coins[i].getGlobalBounds().width + coins[i].getGlobalBounds().width + 10, coins[i].getPosition().y);

                draw.add(cost[i]);

            }

        }
    }

    /**
     * Loads the correct button sprite, for unlocked or not
     * @param i index of button
     * @return Sprite with correct image
     */
    public Sprite loadButton(int i){
        if(state.getUnlocked(i) == false){
            return (loadSprite(Paths.get("Images/purchaseButton.png")));
        }
        if(state.getShip() == i + 1){
            return loadSprite(Paths.get("Images/equipButtonGrey.png"));
        }
        return loadSprite(Paths.get("Images/equipButton.png"));
    }

    /**
     * Load texture from path
     * @param path path for texture
     * @return loaded texture
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
     * Equips ship if it is unlocked or player has enough coins to unlock
     * @param i ship that is to be attempted to equip
     */
    public void updateShip(int i){
        
        int temp = state.getShip();

        if(i == temp){
            return;
        }
        if(state.getUnlocked(i -1) == false){
            if(costTest(SHIP_COST) == false){
                return;
            }
            else{
                state.setUnlocked(i-1);
                draw.remove(coins[i-1]);
                draw.remove(cost[i-1]);
                coins[i-1] = null;
                cost[i-1] = null;
            }
        }
        
        buttons[i-1].setTexture(loadTexture(Paths.get("Images/equipButtonGrey.png")));
        buttons[temp - 1].setTexture(loadTexture(Paths.get("Images/equipButton.png")));
        state.setShip(i);
    }
    /**
     * Loads buttons
     */
    public void loadButtons(){
        loadUpgradeButton();
    }

    /**
     * Loads all the buttons for upgrades
     * Adds cost text and image if not fully unlocked
     */
    public void loadUpgradeButton(){
        int offset =(int) buttons[0].getGlobalBounds().height*5;
        for(int i =0; i<3; i++){
            if(state.getUnlock(i) < 4){
                buttons[4+i] = loadSprite(Paths.get("Images/upgradeButton.png"));
                upgradeCostCoin[i] = loadSprite(Paths.get("Images/coin1.png"));
                upgradeCostCoin[i].setScale(0.2f*getScale().x,0.2f*getScale().y);
                origin(upgradeCostCoin[i]);
                upgradeCostText[i] = new Text(" " + UPGRADE_COST,(loadFont()));
                upgradeCostText[i].setScale(getScale());
                upgradeCostText[i].setOrigin(upgradeCostText[i].getGlobalBounds().width/2,upgradeCostText[i].getGlobalBounds().height/2);

            }
            else{
                buttons[4+i] = loadSprite(Paths.get("Images/upgradeButtongrey.png"));
            }
            buttons[4+i].setScale(getScale());
            origin(buttons[4+i]);

            
            buttons[4+i].setPosition(buttons[0].getPosition().x + buttons[4+i].getGlobalBounds().width,buttons[0].getPosition().y + (buttons[4].getGlobalBounds().height) + offset);
            offset += (buttons[4].getGlobalBounds().height) * 4;
            draw.add(buttons[4+i]);
            if(upgradeCostCoin[i] != null && upgradeCostText[i] != null){
                upgradeCostText[i].setPosition(buttons[4+i].getPosition().x - buttons[4+i].getGlobalBounds().width/2 - upgradeCostText[i].getGlobalBounds().width,buttons[4+i].getPosition().y - buttons[4+i].getGlobalBounds().height/2);
                upgradeCostCoin[i].setPosition(upgradeCostText[i].getPosition().x - upgradeCostCoin[i].getGlobalBounds().width*2, buttons[4+i].getPosition().y- buttons[4+i].getGlobalBounds().height/2);
                draw.add(upgradeCostCoin[i]);
                draw.add(upgradeCostText[i]);
            }
        }

    }

    /**
     * Loads images for upgrades
     */
    public void loadUpgradeSprites(){
        Sprite laser = loadSprite(Paths.get("Images/laserShopFrame.png"));
        Sprite hyper = loadSprite(Paths.get("Images/hyperdriveShopFrame.png"));
        Sprite shield = loadSprite(Paths.get("Images/shieldShopFrame.png"));
        laser.setScale(0.4f*getScale().x, 0.4f*getScale().y);
        hyper.setScale(0.4f*getScale().x, 0.4f*getScale().y);
        shield.setScale(0.4f*getScale().x, 0.4f*getScale().y);
        
        origin(laser);
        

        origin(hyper);
        origin(shield);

        laser.setPosition(buttons[4].getPosition().x - ((buttons[4].getGlobalBounds().width/5)*getScale().x), buttons[4].getPosition().y - (laser.getGlobalBounds().height) - (15*getScale().y));
        draw.add(laser);
        hyper.setPosition(buttons[4].getPosition().x - ((buttons[5].getGlobalBounds().width/5)*getScale().x), buttons[5].getPosition().y - (hyper.getGlobalBounds().height) - (15*getScale().y));
        draw.add(hyper);
        shield.setPosition(buttons[4].getPosition().x - ((buttons[6].getGlobalBounds().width/5)*getScale().x), buttons[6].getPosition().y - (shield.getGlobalBounds().height) - (15*getScale().y));
        draw.add(shield);

        loadBars();
    }

    /**
     * Loads images to show how much each upgrade has been unlocked
     */
    public void loadBars(){
        for(int i = 0; i < 3; i++){
            bars[i] = loadSprite(getBar(i));
            bars[i].setScale(2*getScale().x,2*getScale().y);
            bars[i].setPosition(buttons[4+i].getPosition().x + (buttons[4+i].getGlobalBounds().width),buttons[4+i].getPosition().y - buttons[4+i].getGlobalBounds().height*2);
            draw.add(bars[i]);
        }
    }
    /**
     * Gets an image for bar based off state
     * @param i state of bar desired 0-4 for unlock levels
     * @return Path to image for this state
     */
    public Path getBar(int i){
        int temp = state.getUnlock(i);
        if(temp == 0){
            return Paths.get("Images/powerUpBar0.png");
        }
        else if(temp == 1){
            return Paths.get("Images/powerUpBar1.png");
        }
        else if(temp == 2){
            return Paths.get("Images/powerUpBar2.png");
        }
        else if(temp == 3){
            return Paths.get("Images/powerUpBar3.png");
        }
        else if(temp == 4){
            return Paths.get("Images/powerUpBar4.png");
        }
        return null;

    }
    /**
     * Changes image for bar if state changes
     * @param i  new state for bar
     */
    public void updateBar(int i){
        if(state.getUnlock(i-2) < 4){
            if(costTest(UPGRADE_COST) == false){
                return;
            }
            state.addUnlock(i -2);
            bars[i - 2].setTexture(loadTexture(getBar(i-2)));
        }
        
        if(state.getUnlock(i - 2)== 4){
            buttons[i+2].setTexture(loadTexture(Paths.get("Images/upgradeButtongrey.png")));
            draw.remove(upgradeCostCoin[i-2]);
            draw.remove(upgradeCostText[i-2]);
            upgradeCostText[i-2] = null;
            upgradeCostCoin[i-2] = null;
        }
    }

    /**
     * Checks if player can afford item and unlocks if yes
     * @param i Cost of item
     * @return true if now unlocked false if not
     */
    public boolean costTest(int i){
        if(i > state.getCoins()){
            return false;
        }
        state.removeCoins(i);
        updateCoins();
        return true;
    }
    /**
     * Updates the coin total text to new coin total
     */
    public void updateCoins(){
        currentCoins.setString(" : " + state.getCoins());
    }

    /**
     * Load buttons and images for retirement option
     */
    public void loadRetireButton(){
        if(state.getRetire() == false){
            buttons[8] = loadSprite(Paths.get("Images/retire.png"));
        }
        else{
            buttons[8] = loadSprite(Paths.get("Images/retireGrey.png"));
        }
        buttons[8].setScale(getScale());
        origin(buttons[8]);
        buttons[8].setPosition(buttons[3].getPosition().x, buttons[6].getPosition().y);
        draw.add(buttons[8]);
        Sprite coin = loadSprite(Paths.get("Images/coin1.png"));
        coin.setScale(0.2f*getScale().x,0.2f*getScale().y);
        origin(coin);
        coin.setPosition(buttons[8].getPosition().x - buttons[8].getGlobalBounds().width/2,buttons[8].getPosition().y + coin.getGlobalBounds().height);
        draw.add(coin);

        Text cost;  
        Font font = loadFont();
        cost = new Text("" + RETIRE_COST,font);
        cost.setScale(getScale());
        cost.setOrigin(cost.getGlobalBounds().width/2,cost.getGlobalBounds().height/2);
        cost.setPosition(coin.getPosition().x + coin.getGlobalBounds().width + 10  + coin.getGlobalBounds().width, coin.getPosition().y);

        draw.add(cost);

        Sprite image = loadSprite(Paths.get("Images/retirementPicture.png"));
        origin(image);
        image.setScale(0.7f, 0.7f);
        image.setPosition(buttons[8].getPosition().x , buttons[8].getPosition().y - image.getGlobalBounds().height/2 - buttons[8].getGlobalBounds().height);
        draw.add(image);
    }
    /**
     * gets scale of window based off target 1920x1080
     * @return vector containing scale
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
     * Swap the retirement button state. Unlocked or not
     */
    public void swapRetire(){
        if(state.getRetire() == false){
            buttons[8].setTexture(loadTexture(Paths.get("Images/retire.png")));
        }
        else{
            buttons[8].setTexture(loadTexture(Paths.get("Images/retireGrey.png")));
        }
    }

    /**
     * Loads paths for all images used for description of items
     * Creates sprite with texture of current selectes
     */
    public void loadDescImages(){
        Path[] paths = new Path[8];
        paths[0] = Paths.get("Images/defShipUp.png");
        paths[1] = Paths.get("Images/fastShipUp.png");
        paths[2] = Paths.get("Images/laserShipUp.png");
        paths[3] = Paths.get("Images/strongShipUp.png");
        paths[4] = Paths.get("Images/laserUpgrade.png");
        paths[5] = Paths.get("Images/hyperdriveUpgrade.png");
        paths[6] = Paths.get("Images/shieldUpgrade.png");
        paths[7] = Paths.get("Images/retirementUp.png");

        for(int i =0; i< descImages.length; i++){
            descImages[i] = loadTexture(paths[i]);
        }

        curDesc = new Sprite(descImages[1]);
        origin(curDesc);
        curDesc.setScale(2*getScale().x,2*getScale().y);
        curDesc.setPosition(buttons[8].getPosition().x, buttons[8].getPosition().y - 450 *getScale().y);
        draw.add(curDesc);
    }

    /**
     * Set description to current selected item
     * @param i index of button
     */
    public void setDesc(int i){
        curDesc.setPosition(buttons[8].getPosition().x, buttons[8].getPosition().y - 450 *getScale().y);
        curDesc.setTexture(descImages[i]);
    }
    /**
     * Hides this image off screen
     * quicker than destroying and recreating everytime
     */
    public void hideDesc(){
        curDesc.setPosition(window.getSize().x*2,window.getSize().y*2);
    }

}


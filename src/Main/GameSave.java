package Main;

import java.io.Serializable;

/**
 * Serializable class that contains all information need to save the game state
 */

public class GameSave implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int coins;
    private int ship;
    private int currentPlanet;
    private int[] unlock;
    private boolean[] shipUnlock;
    private boolean retire;

    /**
     * Sets values to be store
     * @param c current coins
     * @param s current ship
     * @param current current planet number
     * @param u unlocks
     * @param sh ships unlocked
     * @param r if retirement is unlocked
     */
    public void setAll(int c,int s, int current, int[] u, boolean[] sh, boolean r){
        coins = c;
        ship = s;
        currentPlanet = current;
        unlock = u;
        shipUnlock = sh;
        retire = r;
    }  
    /**
     * Gets coins
     * @return coins total
     */
    public int getCoins(){
        return coins;
    }
   /**
    * gets current ship
    * @return ship selected
    */ 
    public int getShip(){
        return ship;
    }

    /**
     * Gets current planet 
     * @return planet nmber
     */
    public int getCurrentPlanet(){
        return currentPlanet;
    }
    /**
     * gets unlocks
     * @return unlock as numbers in array
     */
    public int[] getUnlock(){
        return unlock;
    }
    /**
     * What ships are unlocked
     * @return ship unlock array
     */
    public boolean[] getShipUnlock(){
        return shipUnlock;
    }
    /**
     * retirement state
     * @return get retirement state
     */
    public boolean getRetire(){
        return retire;
    }

}

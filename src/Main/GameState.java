package Main;

/**
 * Class used to hold the state of the game
 * e.g. Coins and unlocks
 */

public class GameState {
        private int coins = 0;
        private int ship = 1;
        private int currentPlanet = 2;
        private int[] unlock = new int[3];
        private boolean[] shipUnlock = new boolean [4];
        private Planets[] planets = new Planets[8];
        private boolean retire = false;

        /**
         * Loads all planets and sets original state
         * No unlocks, start at earth and default ship
         */
    public GameState(){
        unlock[0] = 0;
        unlock[1] = 0;
        unlock[2] = 0;
        shipUnlock[0] =true;
        for(int i = 1; i<4;i++){
            shipUnlock[i] = false;
        }
        planets[0]= new Planets("Mercury", 0,0,"Images/mercury.png");
        planets[1]= new Planets("Venus",2000,1,"Images/venus.png");
        planets[2]= new Planets("Earth", 4000,2,"Images/earth.png");
        planets[3]= new Planets("Mars", 6500,3,"Images/mars.png");
        planets[4]= new Planets("Jupiter", 9000,4,"Images/jupiter.png");
        planets[5]= new Planets("Saturn", 10500,5,"Images/saturn.png");
        planets[6]= new Planets("Uranus", 13000,6,"Images/uranus.png");
        planets[7]= new Planets("Neptune", 14500,7,"Images/neptune.png");

    }

    /**
     * Returns the boolean for if this ship is unlocked
     * @param i ship to check
     * @return true if unlocked, false if not
     */
    public boolean getUnlocked(int i){
        return shipUnlock[i];
    }
    /**
     * Set this ship to unlocked
     * @param i ship to unlock
     */
    public void setUnlocked(int i){
        shipUnlock[i] = true;
    }

    /**
     * Gets level of this unlock
     * @param i unlock to check
     * @return value of this unlock
     */
    public int getUnlock(int i){
        return unlock[i];
    }

    /**
     * If unlock level below max, add unlock level
     * @param i unlock to add to
     */
    public void addUnlock(int i){
        if(unlock[i] < 4){
            unlock[i] += 1;
        }
        
    }
    /**
     * set current planet
     * @param i planet to set to
     */
    public void setPlanet(int i){
        currentPlanet = i;
    }
    /**
     * get coins
     * @return coins total
     */
    public int getCoins(){
        return coins;
    }
    /**
     * Get current selected ship
     * @return selected ship as int 1-4
     */
    public int getShip(){
        return ship;
    }
    /**
     * Current planet number
     * @return planet 0-7
     */
    public int getCurrentPlanet(){
        return currentPlanet;
    }
    /**
     * Get the instance of planet
     * @param i planet to get
     * @return returns a planet
     */
    public Planets getPlanet(int i){
        return planets[i];
    }
    /**
     * Set the coins total stored
     * @param i value to set coins to
     */
    public void setCoins(int i){
        coins = i;
    }
    /**
     * Add coins to current total
     * @param i value to add
     */
    public void addCoins(int i){
        coins = i;
    }
    /**
     * Remove coins from total
     * @param i value to remove
     */
    public void removeCoins(int i){
        coins-=i;
    }

    /**
     * Set selected ship
     * @param i value to set ship to 1-4
     */
    public void setShip(int i){
        ship = i;
    }
    /**
     * Gets name of planet based off index
     * @param i index of planet 0-7
     * @return name of this planet
     */
    public String getPlanetName(int i){
        return planets[i].getName();
    }
    /**
     * Get distance of planet from origin
     * @param i planet to get distance of
     * @return distance from origin as int
     */
    public int getPlanetDist(int i){
        return planets[i].getLocation();
    }
    /**
     * Get is player has unlocked retirement
     * @return true if yes, false if no
     */
    public boolean getRetire(){
        return retire;
    }
    /**
     * Set retirement to unlocked
     */
    public void setRetire(){
        retire = true;;
    }
    /**
     * returns full array of unlocks
     * @return array with ints representing the level each unlocked to
     */
    public int[] getAllUnlock(){
        return unlock;
    }
    /**
     * get which ships are unlocked
     * @return array with true false for each ship
     */
    public boolean[] getAllShip(){
        return shipUnlock;
    }

    /**
     * Sets all value in state. Used for when loading a serialized save
     * @param c coins
     * @param s ship
     * @param cur planet
     * @param un unlocks
     * @param ships ships unlocked
     * @param r if retired
     */
    public void setAll(int c, int s, int cur, int[] un, boolean[] ships, boolean r){
        coins = c;
        ship =s;
        currentPlanet = cur;
        unlock = un;
        shipUnlock = ships;
        retire = r;
    }
}


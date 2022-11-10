package Ships;

import Level.GameBoard;

/**
 * Singleton builder for PlayerShips
 */
public class ShipBuilder {

    private static ShipBuilder builderInstance  = null;

    /**
     * Call getInstance to get an instance
     */
    private ShipBuilder(){
    }

    /**
     * Creates an instance if doesn't exist. Returns the instance
     * @return instance of shipbuilder
     */
    public static ShipBuilder getInstance(){
        if(builderInstance == null){
            builderInstance = new ShipBuilder();
        }
        return builderInstance;
    }

    /**
     * Returns a ship of inputted type
     * Default is defaultship
     */
    public PlayerShip getShip(int shipNum,GameBoard window, int ui,int lives,int[] unlock){
        if(shipNum == 1){
            return new DefaultShip(window,ui,lives,unlock);
        }
        else if(shipNum == 2){
            return new FastShip(window,ui,lives,unlock);
        }
        else if(shipNum == 3){
            return new LaserShip(window,ui,lives,unlock);
        }
        else if(shipNum == 4){
            return new StrongShip(window,ui,lives,unlock);
        }
        return new DefaultShip(window,ui,lives,unlock);
    }
    
}

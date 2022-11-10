package Main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class used to trnasfer values from gamestate to the serializable class for game saving
 */


public class SaveManager {
    String fileName = "Save.ser";
    public SaveManager(){
    }

    /**
     * Converts gamestate to saveable class
     * @param state GameState to save
     */
    public void makeSave(GameState state){
        GameSave save = new GameSave();
        save.setAll(state.getCoins(), state.getShip(), state.getCurrentPlanet(), state.getAllUnlock(), state.getAllShip(), state.getRetire());


        try{
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream saveObject = new ObjectOutputStream(file);
            saveObject.writeObject(save);
            file.close();
            saveObject.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to save game");
        }
    }

    /**
     * loads a save and assigns to given GameState
     * @param s GameState currently in use
     */
    public void loadSave(GameState s){

        GameSave save;
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream obj = new ObjectInputStream(file);

            save = (GameSave) obj.readObject();
            file.close();
            obj.close();
        }
        catch(Exception e){
            System.out.println("Failed to load save");
            return;
        }

        
        s.setAll(save.getCoins(), save.getShip(), save.getCurrentPlanet(), save.getUnlock(), save.getShipUnlock(), save.getRetire());


    }
}

package Main;

import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Planets class used to store info on planets in the game
 */

public class Planets {
    private String name;
    private int location;
    private int index;
    private Path p;

    /**
     * Constructor
     * @param n name to assign planet
     * @param l location based off distance from origin (0)
     * @param i index of planet
     * @param path Path for this planets image
     */
    public Planets(String n, int l,int i, String path){
        name = n;
        location = l;
        index = i;
        p = Paths.get(path);
    }
    /**
     * Returns planets name
     * @return planets name
     */
    public String getName(){
        return name;
    }
    /**
     * Gets planets location as distance from origin
     * @return int of location
     */
    public int getLocation(){
        return location;
    }
    /**
     * Index assigned when planet created
     * @return int index
     */
    public int getIndex(){
        return index;
    }

    /**
     * Get the path of this planets image
     * @return Path for this image
     */
    public Path getPath(){
        return p;
    }
}

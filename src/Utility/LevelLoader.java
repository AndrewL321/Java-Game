package Utility;

import Main.GameState;
import Main.Planets;

/**
 * Class used to create a level
 */
public class LevelLoader {

    private int distance;
    private Planets current;
    private Planets dest;
    private String difficulty;
    private float difMutiplier = 1;
    private int reward;
    
    /**
     * Creates level with these values
     * @param c current planet
     * @param s current gamestate
     * @param dif the difficulty as String. Valid values "HARD","MEDIUM","EASY"
     */
    public LevelLoader(int c, GameState s,String dif){

        distance = Math.abs(s.getPlanetDist(c) - s.getPlanetDist(s.getCurrentPlanet()));
        current = s.getPlanet(s.getCurrentPlanet());
        dest = s.getPlanet(c);
        difficulty = dif;
        reward = distance/30;

        if(difficulty == "MEDIUM"){
            difMutiplier = 1.3f;
            reward = distance/25;
        }
        else if(difficulty == "HARD"){
            difMutiplier = 1.7f;
            reward = distance/15;
        }

    }

    /**
     * Gets distance of level
     * @return int for distance
     */
    public int getDistance(){
        return distance;
    }
    /**
     * Gets the difficulty multiplier for this level
     * @return float of diffivulty multiplier
     */
    public float getDifMultiplier(){
        return difMutiplier;
    }
    /**
     * Gets reward value for this level
     * @return int of level reward
     */
    public int getReward(){
        return reward;
    }
    /**
     * Gets difficulty of level
     * @return String for level difficulty
     */
    public String getDifficulty(){
        return difficulty;
    }
    /**
     * Gets current/start planet
     * @return planet objects of current location
     */
    public Planets getCurrent(){
        return current;
    }
    /**
     * Gets destination planet
     * @return planet object of destination
     */
    public Planets getDest(){
        return dest;
    }
    /**
     * Calculates the lives given for this level based off distance
     * @param distance distance of level
     * @return int for lives
     */
    public int calcLives(float distance){
        return (int)(distance/2000 + 0.5);
    }
    
}

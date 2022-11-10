package Obstacles;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;


/**
 * Class used to create obstacles. These are any type of objects
 * Including powerups and coins
 */
public final class ObstacleBuilder {
    
    private Texture images[] = new Texture[11];
    private float difficulty;


    /**
     * Loads all textures needed for every obstacle
     * @param dif the difficulty multipier to apply to obstacles speed
     */
    public ObstacleBuilder(float dif){
        difficulty = dif;

        Path path[] = new Path[images.length];         // Path to image
        path[0] = Paths.get("Images/ast1.png24");
        path[1] = Paths.get("Images/ast2.png24");
        path[2] = Paths.get("Images/ast3.png24");
        path[3] = Paths.get("Images/ast4.png24");
        path[4] = Paths.get("Images/coin1.png");
        path[5] = Paths.get("Images/coin2.png");
        path[6] = Paths.get("Images/coin3.png");
        path[7] = Paths.get("Images/thief.png");
        path[8] = Paths.get("Images/laser.png");
        path[9] = Paths.get("Images/hyper.png");
        path[10] = Paths.get("Images/sheild.png");


        for(int i = 0; i< path.length; i++){
            try {
                images[i] = new Texture();                                                   // Try to load image from path
                images[i].loadFromFile(path[i]);
                images[i].setSmooth(true);
            } catch (IOException e) {
                System.out.println("Unable to load image : " + path[i]);
                e.printStackTrace();
            }
        }
    }


    /**
     * Creates an obstacle based off input
     * @param type Type of obstacle to create. "ASTEROID", "COIN", "POWERUP", "THIEF"
     * @param typeNum
     * @param scale
     * @return
     */

    public Obstacle getObstacle(String type, int typeNum,Vector2f scale){
        if(type == "ASTEROID"){
            if(typeNum == 1){
                return new Asteroid(images[0],1*difficulty,new Vector2f (scale.x * 0.6f,scale.y *0.6f),scale);
            }
            else if(typeNum == 2){
                return new Asteroid(images[1],2*difficulty,new Vector2f (scale.x * 1f,scale.y *1f),scale);
            }
            else if(typeNum == 3){
                return new Asteroid(images[2],1*difficulty,new Vector2f (scale.x * 0.6f,scale.y *0.6f),scale);
            }
            else if(typeNum == 4){
                return new Asteroid(images[3],2*difficulty,new Vector2f (scale.x * 1f,scale.y *1f),scale);
            }
            else{
                System.out.println("Invalid Asteroid");
            }
        }
        else if(type == "COIN"){
            if(typeNum == 1){
                return new Coin(images[4],3*difficulty,1,new Vector2f (scale.x * 0.5f,scale.y *0.5f),scale);
            }
            else if(typeNum == 2){
                return new Coin(images[5],3*difficulty,3,new Vector2f (scale.x * 0.5f,scale.y *0.5f),scale);
            }
            else if(typeNum == 3){
                return new Coin(images[6],3*difficulty,10,new Vector2f (scale.x * 0.5f,scale.y *0.5f),scale);
            }
            else{
                System.out.println("Invalid Coin");
            }
        }
        else if(type == "POWERUP"){
            if(typeNum == 1){
                return new PowerUpCollectable(images[8],2*difficulty,new Vector2f (scale.x * 1.5f,scale.y *1.5f),"LASER",scale);
            }
            else if(typeNum == 2){
                return new PowerUpCollectable(images[9],2*difficulty,new Vector2f (scale.x * 1.5f,scale.y *1.5f),"HYPER",scale);
            }
            else if(typeNum == 3){
                return new PowerUpCollectable(images[10],2*difficulty,new Vector2f (scale.x * 1.5f,scale.y *1.5f),"SHIELD",scale);
            }
            else{
                System.out.println("Invalid Powerup");
            }
        }
        else if(type == "THIEF" && typeNum == 1){
            return new PizzaThief(images[7], 1f*difficulty, scale,scale);
        }
        System.out.println("Builder failed - debug = " + type + " - " + typeNum);
        return null;
    }
}

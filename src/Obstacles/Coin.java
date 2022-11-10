package Obstacles;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Class used for coins, extends obstacle
 */
public class Coin extends Obstacle {

    private int value;

    /**
     * Creates coin with inputted value
     * @param image image for this coin
     * @param speedMult speed of coin multiplier
     * @param xScale xscale
     * @param yScale yscale
     * @param v value of the coin
     * @param sScale Vector of scale
     */
    public Coin(Texture image, float speedMult, int v,Vector2f scale, Vector2f speedScale) {
        super(image, speedMult,scale,speedScale);
        value = v;
        
    }
    /**
     * Returns value of coin
     */
    @Override
    public int getValue(){
        return value;
    }


    
}

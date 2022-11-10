package Obstacles;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Class used for asteroids, extends obstacle
 */
public class Asteroid extends Obstacle {

    public Asteroid(Texture image, float speedMult,Vector2f scale, Vector2f speedScale) {
        super(image, (float)(speedMult * (Math.random()* 2 + 2)),scale,speedScale);
    }

    
}

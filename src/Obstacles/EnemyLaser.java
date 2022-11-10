package Obstacles;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Class used for enemy laser
 */
public class EnemyLaser extends Obstacle {
    public EnemyLaser(Texture image, float speedMult,Vector2f scale, Vector2f speedScale) {
        super(image, speedMult,scale,speedScale);
    }
    
}


package demo_game;

import doctrina.Entities.Entity;
import doctrina.debug.Color;
import org.joml.Vector3f;

public class EntityTest extends Entity {
    public EntityTest() {
        super(Models.makeEnemy(), new Vector3f(0.7f, 1.8f, 0.7f));
        hitbox.setColor(Color.ORANGE);
    }
}

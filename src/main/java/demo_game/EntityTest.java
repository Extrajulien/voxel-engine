package demo_game;

import doctrina.Entities.Entity;
import doctrina.debug.Color;
import doctrina.physic.HitBox;
import doctrina.rendering.Model;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class EntityTest extends Entity {
    public EntityTest(Model model) {
        super(model, new Vector3f(1,1,1));
        hitbox.setColor(Color.YELLOW);
    }
}

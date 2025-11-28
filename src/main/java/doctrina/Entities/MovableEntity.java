package doctrina.Entities;

import doctrina.rendering.Model;
import org.joml.Vector3f;

public abstract class MovableEntity extends Entity {
    protected float walkSpeed;

    public MovableEntity(Model model, Vector3f hitboxDimension) {
        super(model, hitboxDimension);
    }

    public void move(Vector3f direction) {
        this.position.add(direction);
        modelMatrix.setTranslation(position);

        hitbox.update();
    }

    public abstract void update(double deltaTime);


}

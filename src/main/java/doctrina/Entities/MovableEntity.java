package doctrina.Entities;

import doctrina.rendering.Model;
import org.joml.Vector3f;

public abstract class MovableEntity extends Entity {
    protected float walkSpeed;
    protected float maxSpeed; // does not include gravity

    public MovableEntity(Model model, Vector3f hitboxDimension) {
        super(model, hitboxDimension);
    }

    public void move(Vector3f direction) {
        this.position.add(direction);
    }

    public void update(double deltaTime) {
        modelMatrix.translate(position.x, position.y, position.z);
        hitbox.moveToEntity(this);
    }


}

package doctrina.Entities;

import doctrina.Utils.BoundingBox;
import doctrina.physic.*;
import doctrina.rendering.CameraView;
import doctrina.rendering.Model;
import org.joml.Vector3d;
import org.joml.Vector3f;

public abstract class MovableEntity extends Entity {
    protected float walkSpeed;
    protected final Vector3f currentSpeed;
    private Collider collider;
    private float gravityStrength;
    protected float jumpHeight;

    public MovableEntity(Model model, BoundingBox hitboxDimension) {
        super(model, hitboxDimension);
        collider = new Collider(hitboxDimension);
        currentSpeed = new Vector3f();
        walkSpeed = 1;
        jumpHeight = 1;
    }

    public void move() {
        moveEntity();
    }

    private void moveEntity() {
        this.position.add(currentSpeed);
        modelMatrix.setTranslation(position);
    }

    public void drawSpeedBox(CameraView data) {

    }

    public void update(double deltaTime) {

    }

    public void collide(CollisionCandidates  candidates) {
        Vector3d allowedSpeed = collider.collide(currentSpeed, candidates);
        currentSpeed.set(allowedSpeed.x, allowedSpeed.y, allowedSpeed.z);
    }


    public void jump() {

    }

    public void setGravity(float unitSquareBySeconds) {
        gravityStrength = unitSquareBySeconds;
    }

}

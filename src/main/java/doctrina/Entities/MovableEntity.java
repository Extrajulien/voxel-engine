package doctrina.Entities;

import demo_game.Player.EntityState;
import demo_game.Player.EntityStates;
import doctrina.Utils.BoundingBox;
import doctrina.Utils.FloatUtils;
import doctrina.physic.*;
import doctrina.rendering.Model;
import org.joml.Vector3f;

public abstract class MovableEntity extends Entity {
    protected float walkSpeed;
    protected final Vector3f currentSpeed;
    private final Collider collider;
    private float gravityStrength = 0.35f;
    private final EntityStates states;

    public MovableEntity(Model model, BoundingBox hitboxDimension) {
        super(model, hitboxDimension);
        collider = new Collider();
        states = new EntityStates();
        currentSpeed = new Vector3f();
        walkSpeed = 1;
    }

    public void move(CollisionCandidates  candidates, double deltaTime) {
        moveEntity(candidates, deltaTime);
    }

    private void moveEntity(CollisionCandidates  candidates, double deltaTime) {
        float dx = collider.getAllowedX(currentSpeed.x, hitbox.getWorldBounds(), candidates);
        this.position.add(dx, 0, 0);
        hitbox.update(position);

        applyGravity(deltaTime);
        float dy = collider.getAllowedY(currentSpeed.y, hitbox.getWorldBounds(), candidates);
        this.position.add(0, dy, 0);
        hitbox.update(position);

        if (currentSpeed.y <= 0 && FloatUtils.nearlyEqual(dy, 0, 0.00001f)) {
            states.enable(EntityState.IS_GROUNDED);
            currentSpeed.y = 0;
        } else {
            states.disable(EntityState.IS_GROUNDED);
        }

        float dz = collider.getAllowedZ(currentSpeed.z, hitbox.getWorldBounds(), candidates);
        this.position.add(0, 0, dz);
        hitbox.update(position);
    }

    private void applyGravity(double deltaTime) {
        currentSpeed.add(0,-gravityStrength * (float) deltaTime,0);
    }



    public void setState(EntityState state, boolean value) {
        if (value) {
            states.enable(state);
            return;
        }
        states.disable(state);
    }

    public boolean getState(EntityState state) {
        return states.isOn(state);
    }

}

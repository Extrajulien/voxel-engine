package doctrina.Entities;

import doctrina.physic.HitBox;
import doctrina.rendering.CameraView;
import doctrina.rendering.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Entity {
    protected HitBox hitbox;
    private final Model model;

    protected Vector3f position;
    protected Matrix4f modelMatrix;

    public Entity(Model model, Vector3f hitboxDimension) {
        position = new Vector3f(0);
        modelMatrix = new Matrix4f();
        this.model = model;
        hitbox = new HitBox(this, hitboxDimension);
    }

    public void draw(CameraView data) {
        model.draw(modelMatrix, data.viewMatrix(), data.projectionMatrix());
    }

    public final void drawHitBox(CameraView data) {
        hitbox.drawBounds(data.viewMatrix(), data.projectionMatrix());
    }

    public final Vector3f getPosition() {
        return position;
    }

    public final void moveTo(Vector3f position) {
        moveTo(position.x, position.y, position.z);
    }
    public final void moveTo(float x, float y, float z) {
        this.position = new Vector3f(x,y,z);
        modelMatrix = new Matrix4f().translate(position.x, position.y, position.z);
        hitbox.moveToEntity(this);
    }

    public void enableGravity() {

    }

    public void setGravity(float unitSquareBySeconds) {

    }
}

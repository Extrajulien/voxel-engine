package doctrina.Entities;

import doctrina.Utils.BoundingBox;
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

    public Entity(Model model, BoundingBox hitboxDimension) {
        position = new Vector3f(0);
        modelMatrix = new Matrix4f();
        this.model = model;
        hitbox = new HitBox(hitboxDimension);
    }

    public void draw(CameraView data) {
        model.draw(modelMatrix, data.viewMatrix(), data.projectionMatrix());
    }

    public void drawHitBox(CameraView data) {
        hitbox.drawBounds(data.viewMatrix(), data.projectionMatrix());
    }

    public final Vector3f getPosition() {
        return position;
    }

    public final void moveTo(Vector3f position) {
        moveTo(position.x, position.y, position.z);
    }
    public final void moveTo(float x, float y, float z) {
        this.position.set(x,y,z);
        modelMatrix.setTranslation(position.x, position.y, position.z);
        hitbox.update(position);
    }

    public void update(double deltaTime) {

    }
}

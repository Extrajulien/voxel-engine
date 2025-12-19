package doctrina.Entities;

import doctrina.Utils.BoundingBox;
import doctrina.physic.HitBox;
import doctrina.rendering.CameraView;
import doctrina.rendering.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Entity {
    protected final HitBox hitbox;
    private final Model model;
    protected final Vector3f position;
    private final Matrix4f modelMatrix;
    private final Vector3f scalingFactor;
    private final Vector3f rotation;


    public Entity(Model model, BoundingBox hitboxDimension) {
        position = new Vector3f(0);
        modelMatrix = new Matrix4f();
        this.model = model;
        hitbox = new HitBox(hitboxDimension);
        scalingFactor = new Vector3f(1,1,1);
        rotation = new Vector3f();
    }

    public void scaleModel(float x, float y, float z) {
        scalingFactor.set(x,y,z);
    }

    public void rotateModel(float x, float y, float z) {
        rotation.set(x,y,z);
    }

    public BoundingBox getBounds() {
        return new BoundingBox(hitbox.getWorldBounds());
    }

    public void draw(CameraView data) {
        modelMatrix.identity();
        modelMatrix.translate(position);
        modelMatrix.scale(scalingFactor);
        modelMatrix.rotateXYZ(rotation.x, rotation.y, rotation.z);

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
        hitbox.update(position);
    }

    public void update(double deltaTime) {

    }
}

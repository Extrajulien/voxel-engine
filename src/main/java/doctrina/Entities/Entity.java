package doctrina.Entities;

import doctrina.physic.HitBox;
import doctrina.rendering.Camera;
import doctrina.rendering.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Entity {
    protected HitBox hitbox;
    private final Model model;

    protected Vector3f position;
    private Matrix4f modelMatrix;

    public Entity(Model model, Vector3f hitboxDimension) {
        position = new Vector3f(0);
        modelMatrix = new Matrix4f();
        this.model = model;
        hitbox = new HitBox(this, hitboxDimension);
    }

    public void draw(Camera camera) {
        model.draw(modelMatrix, camera.getViewMatrix(), camera.getProjectionMatrix());
    }

    public final void drawHitBox(Camera camera) {
        hitbox.drawBounds(camera.getViewMatrix(), camera.getProjectionMatrix());
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

    public final void move(Vector3f position) {
        this.position.add(position);
        modelMatrix.translate(position.x, position.y, position.z);
        hitbox.moveToEntity(this);
    }

}

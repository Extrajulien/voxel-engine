package doctrina.Entities;

import doctrina.physic.HitBox;
import doctrina.rendering.Model;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

//RG Thermique

public abstract class Entity {
    private HitBox hitbox;
    private final Model model;

    protected Vector3f position;
    protected Vector3d dimension;
    private Matrix4f modelMatrix;

    public Entity(Model model) {
        dimension = new Vector3d(0);
        position = new Vector3f(0);
        modelMatrix = new Matrix4f().translate(1.5f, 0,-1);
        this.model = model;
        hitbox = new HitBox(this, dimension);
    }

    public void draw(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        model.draw(modelMatrix, viewMatrix, projectionMatrix);
    }

    public void drawHitBox(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        hitbox.drawBounds(viewMatrix, projectionMatrix);
    }

    public Vector3f getPosition() {
        return position;
    }



    public void moveTo(Vector3f position) {
        this.position = position;
        hitbox.updatePosition(this);
    }
    public void moveTo(float x, float y, float z) {
        this.position = new Vector3f(x,y,z);
        hitbox.updatePosition(this);
    }

    public void setDimension(Vector3d dimension) {
        this.dimension = dimension;
    }








}

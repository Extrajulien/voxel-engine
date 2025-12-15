package doctrina.physic;

import doctrina.Entities.Entity;
import doctrina.Utils.BoundingBox;
import doctrina.debug.Color;
import doctrina.Uniform.HitboxUniform;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11C.glLineWidth;

public class HitBox {
    private Vector3f position;
    private BoundingBox bounds;
    private final static Mesh cube = new Mesh.Builder().boundingBox().build();
    private Material<HitboxUniform> material;
    private final Model<HitboxUniform> boundsModel;
    private final Matrix4f hitboxModelMatrix;
    private Vector3f color;

    public HitBox(BoundingBox dimension) {
        this(dimension, new Vector3f(255.0f,255.0f,255.0f));
    }

    public HitBox(BoundingBox dimension, Vector3f color) {
        createMaterial();
        hitboxModelMatrix = new Matrix4f();
        boundsModel = new Model<>(cube, material);
        this.color = color;
        attachColorToShader();
        this.position = new Vector3f();
        this.bounds = new BoundingBox(dimension);
        update(position);
    }

    public void setBounds(BoundingBox box) {
        this.bounds = box;
    }

    public void moveToEntity(Entity entity) {
        this.position = entity.getPosition();
    }

    public void update(Vector3f position) {
        this.position.set(position);
        bounds.moveTo(position);
        updateHitboxModelMatrix();
    }

    public BoundingBox getWorldBounds() {
        return new BoundingBox(bounds).translate(position.x, position.y, position.z);
    }

    public void setBoundsSides() {

    }

    public void setColor(Vector3f color) {
        material.use();
        this.color = color;
        attachColorToShader();
    }

    public void setColor(Color color) {
        material.use();
        this.color = color.getValue();
        attachColorToShader();
    }

    public void drawBounds(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        glLineWidth(2.0f);
        material.use();
        boundsModel.drawBoundingBox(hitboxModelMatrix, viewMatrix, projectionMatrix);
    }

    private void attachColorToShader() {
        material.setUniform(HitboxUniform.HITBOX_COLOR, color);
    }

    private void createMaterial() {
        Shader<HitboxUniform> shader = new Shader<>(HitboxUniform.class, "vertex.glsl", "hitboxFragment.glsl");
        material = new Material<>(shader);
    }

    private void updateHitboxModelMatrix() {
        this.hitboxModelMatrix.scaling(bounds.width(), bounds.height(), bounds.depth());
        this.hitboxModelMatrix.setTranslation(new Vector3f(position));
    }
}

package doctrina.physic;

import doctrina.Entities.Entity;
import doctrina.debug.Color;
import doctrina.Uniform.HitboxUniform;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11C.glLineWidth;

public class HitBox {
    protected Vector3f position;
    protected Vector3f dimension;
    private final static Mesh cube = new Mesh.Builder().boundingBox().build();
    private Material<HitboxUniform> material;
    private final Model<HitboxUniform> bounds;
    private Matrix4f modelMatrix;
    private Vector3f color;

    public HitBox(Entity entity, Vector3f dimension) {
        this(entity, dimension, new Vector3f(255.0f,255.0f,255.0f));
    }

    public HitBox(Entity entity, Vector3f dimension, Vector3f color) {
        createMaterial();
        bounds = new Model<>(cube, material);
        this.color = getColorFromRange(color);
        attachColorToShader();
        this.position = entity.getPosition();
        this.dimension = dimension;
        initializeModelMatrix();
    }

    public void setDimension() {
        this.dimension = new Vector3f(dimension);
    }

    public void moveToEntity(Entity entity) {
        this.position = entity.getPosition();
    }

    public void update() {
        initializeModelMatrix();
    }



    public void setColor(Vector3f color) {
        material.use();
        this.color = getColorFromRange(color);
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
        bounds.drawBoundingBox(modelMatrix, viewMatrix, projectionMatrix);
    }

    private Vector3f getColorFromRange(Vector3f color) {
        return color.div(255);
    }

    private void attachColorToShader() {
        material.setUniform(HitboxUniform.HITBOX_COLOR, color);
    }

    private void createMaterial() {
        Shader<HitboxUniform> shader = new Shader<>(HitboxUniform.class, "vertex.glsl", "hitboxFragment.glsl");
        material = new Material<>(shader);
    }

    private void initializeModelMatrix() {
        this.modelMatrix = new Matrix4f().setTranslation(position);
        this.modelMatrix.scale(dimension);
    }
}

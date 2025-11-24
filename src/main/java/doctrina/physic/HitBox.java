package doctrina.physic;

import doctrina.Entities.Entity;
import doctrina.debug.Color;
import doctrina.debug.DebugUniform;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11C.glLineWidth;

public class HitBox {
    protected Vector3f position;
    protected Vector3f dimension;
    private final static Mesh cube = new Mesh.Builder().boundingBox().build();
    private final static Shader shader = new Shader("vertex.glsl", "colorFragment.glsl");
    private final static Material material = new Material(shader);
    private final static Model bounds = new Model(cube, material);
    private Matrix4f modelMatrix;
    private Vector3f color;

    public HitBox(Entity entity, Vector3f dimension) {
        this(entity, dimension, new Vector3f(255.0f,255.0f,255.0f));
    }

    public HitBox(Entity entity, Vector3f dimension, Vector3f color) {
        this.color = getColorFromRange(color);
        attachColorToShader();
        this.position = entity.getPosition();
        this.modelMatrix = new Matrix4f().translate(position);
        this.modelMatrix.scale(dimension);
    }

    public void setDimension() {
        this.dimension = new Vector3f(dimension);
    }

    public void moveToEntity(Entity entity) {
        this.position = entity.getPosition();
        this.modelMatrix.translate(position);
    }

    public void setColor(Vector3f color) {
        this.color = getColorFromRange(color);
        attachColorToShader();
    }

    public void setColor(Color color) {
        this.color = color.getValue();
        attachColorToShader();
    }

    public void drawBounds(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        glLineWidth(1.0f);
        material.use();
        bounds.drawBoundingBox(modelMatrix, viewMatrix, projectionMatrix);
    }

    private Vector3f getColorFromRange(Vector3f color) {
        return color.div(255);
    }

    private void attachColorToShader() {
        if (!DebugUniform.isIsInitialized()) {
            DebugUniform.HITBOX_COLOR.loadPositionLUT(shader);
        }
        if (!DebugUniform.HITBOX_COLOR.isCorrectShader(shader)) {
            throw new RuntimeException("DebugUniform is not using the correct shader");
        }
        material.setUniform(DebugUniform.HITBOX_COLOR, color);
    }

}

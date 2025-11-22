package doctrina.physic;

import doctrina.Entities.Entity;
import doctrina.debug.DebugUniform;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11C.glLineWidth;

public class HitBox {
    protected Vector3f position;
    protected Vector3d dimension;
    private final static Mesh cube = new Mesh.Builder().boundingBox().build();
    private final static Material material = new Material(new Shader("vertex.glsl", "colorFragment.glsl"));
    private final static Model bounds = new Model(cube, material);
    private Matrix4f modelMatrix;
    private Vector3f color;

    public HitBox(Entity entity, Vector3d dimension) {
        this(entity, dimension, new Vector3f(0,0,255.0f));
    }

    public HitBox(Entity entity, Vector3d dimension, Vector3f color) {
        this.color = color.div(255);
        this.position = entity.getPosition();
        this.modelMatrix = new Matrix4f().translate(position);
    }

    public void updatePosition(Entity entity) {
        this.position = entity.getPosition();
        this.modelMatrix = new Matrix4f().translate(position);
    }

    public void drawBounds(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        glLineWidth(2.0f);
        material.use();
        material.setUniform(DebugUniform.HITBOX_COLOR, color);
        bounds.drawBoundingBox(modelMatrix, viewMatrix, projectionMatrix);
    }



}

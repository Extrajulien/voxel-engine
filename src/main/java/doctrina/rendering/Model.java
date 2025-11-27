package doctrina.rendering;

import doctrina.Uniform.Uniform;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class Model<U extends Enum<U> & Uniform> {
    private final Mesh mesh;
    private final Material<U> material;
    private final int VAO;

    public Model(Mesh mesh, Material<U> material) {
        this.mesh = mesh;
        this.material = material;
        VAO = glGenVertexArrays();
        assignComponentsToVao();
        VertexAttribute.enableAll();
        unBindVAO();
    }

    public void draw(Matrix4f modelMatrix, Matrix4f viewMatrix,Matrix4f projectionMatrix) {
        glEnable(GL_DEPTH_TEST);
        material.use();
        material.setModelMatrix(modelMatrix);
        material.setViewMatrix(viewMatrix);
        material.setProjectionMatrix(projectionMatrix);
        material.bindTextures();
        bindVAO();
        glDrawElements(GL_TRIANGLES, mesh.getIndicesSize(), GL_UNSIGNED_INT, 0);
        unBindVAO();
    }

    public void drawBoundingBox(Matrix4f modelMatrix, Matrix4f viewMatrix,Matrix4f projectionMatrix) {
        glDisable(GL_DEPTH_TEST);
        material.use();
        material.setModelMatrix(modelMatrix);
        material.setViewMatrix(viewMatrix);
        material.setProjectionMatrix(projectionMatrix);
        bindVAO();

        glDrawElements(GL_LINES, mesh.getIndicesSize(), GL_UNSIGNED_INT, 0);
        unBindVAO();
    }

    private void bindVAO() {
        glBindVertexArray(VAO);
    }

    // just there to prevent the outer world from accidentally messing with it
    private void unBindVAO() {
        glBindVertexArray(0);
    }

    private void assignComponentsToVao() {
        bindVAO();
        mesh.bind();
        material.use();
    }

}

package doctrina.rendering;

import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class Model {
    private final Mesh mesh;
    private final Material material;
    private final int VAO;

    public Model(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
        VAO = glGenVertexArrays();
        assignComponentsToVao();
        VertexAttribute.enableAll();
        unBindVAO();
    }

    public void draw(Matrix4f modelMatrix, Matrix4f viewMatrix,Matrix4f projectionMatrix) {
        material.setModelMatrix(modelMatrix);
        material.setViewMatrix(viewMatrix);
        material.setProjectionMatrix(projectionMatrix);
        material.bindTextures();
        bindVAO();

        glDrawElements(GL_TRIANGLES, mesh.getIndicesSize(), GL_UNSIGNED_INT, 0);
        unBindVAO();
    }

    private void bindVAO() {
        glBindVertexArray(VAO);
    }

    // just there to prevent the outer world from messing with it
    private void unBindVAO() {
        glBindVertexArray(0);
    }

    private void assignComponentsToVao() {
        bindVAO();
        mesh.bind();
        material.use();
    }

}

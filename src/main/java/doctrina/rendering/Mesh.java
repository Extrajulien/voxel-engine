package doctrina.rendering;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;

public class Mesh {
    private final float[] vertices;
    private final int[] indices;
    private int VBO;
    private int EBO;

    /**
     * Create a Mesh data structure that automatically Create the associated EBO and VBO
     * @param indices int array wound in counterClockwise order
     * @param vertices float array containing the vertex attributes defined in the {@link VertexAttribute} enum, In the same order!
     */
    public Mesh(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        createVBO();
        createEBO();
    }

    public Mesh(Builder builder) {
        this.vertices = builder.vertices;
        this.indices = builder.indices;
        createVBO();
        createEBO();
    }

    public void bind() {
        bindVBO();
        bindEBO();
    }

    private void bindEBO() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
    }

    private void bindVBO() {
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
    }



    private void createVBO() {
        VBO = glGenBuffers();
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        unbindVBO();
    }

    private void createEBO() {
        EBO = glGenBuffers();
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
        unbindEBO();
    }

    private void unbindVBO() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void unbindEBO() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public static class Builder {

        float[] vertices;
        int[] indices;


        public Builder cube() {

            this.vertices = new float[] {
                    // --- Front face (Z = +0.5) ---
                    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, // bottom-left
                    0.5f, -0.5f,  0.5f,  1.0f, 0.0f, // bottom-right
                    0.5f,  0.5f,  0.5f,  1.0f, 1.0f, // top-right
                    -0.5f,  0.5f,  0.5f,  0.0f, 1.0f, // top-left

                    // --- Back face (Z = -0.5) ---
                    0.5f, -0.5f, -0.5f,  0.0f, 0.0f, // bottom-left
                    -0.5f, -0.5f, -0.5f,  1.0f, 0.0f, // bottom-right
                    -0.5f,  0.5f, -0.5f,  1.0f, 1.0f, // top-right
                    0.5f,  0.5f, -0.5f,  0.0f, 1.0f, // top-left

                    // --- Left face (X = -0.5) ---
                    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                    -0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                    -0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,

                    // --- Right face (X = +0.5) ---
                    0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                    0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                    0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                    0.5f,  0.5f,  0.5f,  0.0f, 1.0f,

                    // --- Top face (Y = +0.5) ---
                    -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                    0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                    0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,

                    // --- Bottom face (Y = -0.5) ---
                    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                    0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                    0.5f, -0.5f,  0.5f,  1.0f, 1.0f,
                    -0.5f, -0.5f,  0.5f,  0.0f, 1.0f
            };

            this.indices = new int[] {
                    0, 1, 2,  2, 3, 0,       // front
                    4, 5, 6,  6, 7, 4,       // back
                    8, 9,10, 10,11, 8,       // left
                    12,13,14, 14,15,12,       // right
                    16,17,18, 18,19,16,       // top
                    20,21,22, 22,23,20        // bottom
            };

            return this;
        }

        public Mesh build() {
            return new Mesh(this);
        }
    }
}

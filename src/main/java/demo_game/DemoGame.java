package demo_game;

import doctrina.Game;
import doctrina.rendering.RenderingEngine;
import doctrina.rendering.Shader;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class DemoGame extends Game {
    int triangleVAO;
    int triangleVBO;

    Shader shader;

    float[] triangle = {
            0.0f,  0.5f, 0f,   // top vertex
            -0.5f, -0.5f, 0f,   // bottom-left
            0.5f, -0.5f, 0f    // bottom-right
    };
    @Override
    public void initialize() {

        shader = new Shader("src/main/java/demo_game/vertex.glsl", "src/main/java/demo_game/fragment.glsl");
        shader.use();

        triangleVAO = glGenVertexArrays();
        glBindVertexArray(triangleVAO);
        triangleVBO = glGenBuffers();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(triangle.length);
        buffer.put(triangle).flip();
        glBindBuffer(GL_ARRAY_BUFFER, triangleVBO);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);

    }

    @Override
    public void update() {
        if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            stop();
        }
    }

    @Override
    public void draw() {
        glBindVertexArray(triangleVAO);
            glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}

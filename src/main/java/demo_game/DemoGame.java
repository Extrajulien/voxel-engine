package demo_game;

import doctrina.Game;
import doctrina.rendering.RenderingEngine;
import doctrina.rendering.Shader;
import doctrina.rendering.Texture;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class DemoGame extends Game {
    int squareVAO;
    int squareVBO;
    int squareEBO;

    boolean wasAKeyPressed = false;
    boolean wasSKeyPressed = false;

    int greenValue = 50;

    Shader shader;

    float[] square = {
            // positions        // texture coords
             0.5f,  0.5f, 0.0f, 1.0f, 1.0f,   // top right
             0.5f, -0.5f, 0.0f, 1.0f, 0.0f,   // bottom right
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,   // bottom left
            -0.5f,  0.5f, 0.0f, 0.0f, 1.0f    // top left
    };
    int[] indices = {
            0,3,1,
            3,2,1
    };

    Texture texture;
    @Override
    public void initialize() {

        shader = new Shader("src/main/java/demo_game/vertex.glsl", "src/main/java/demo_game/fragment.glsl");
        shader.use();
        texture = new Texture("resources/dirt.jpg");

        squareVAO = glGenVertexArrays();
        glBindVertexArray(squareVAO);
        squareVBO = glGenBuffers();
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(square.length);
        vertexBuffer.put(square).flip();
        glBindBuffer(GL_ARRAY_BUFFER, squareVBO);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        squareEBO = glGenBuffers();
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, squareEBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);


        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * (Float.BYTES), 0L);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * (Float.BYTES), 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update() {

            if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                stop();
            }

            if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_A) == GLFW_PRESS && !wasAKeyPressed) {
                greenValue += 10;
                wasAKeyPressed = true;
            } else if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_A) == GLFW_RELEASE) {
                wasAKeyPressed = false;
            }

            if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_S) == GLFW_PRESS && !wasSKeyPressed) {
                greenValue -= 10;
                wasSKeyPressed = true;
            } else if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_S) == GLFW_RELEASE) {
                wasSKeyPressed = false;
            }





        shader.setUniform("greenValue", (float)(greenValue)/100);
    }

    @Override
    public void draw() {
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        glBindVertexArray(squareVAO);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }
}

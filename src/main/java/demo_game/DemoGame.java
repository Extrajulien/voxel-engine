package demo_game;

import doctrina.Game;
import doctrina.rendering.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class DemoGame extends Game {
    int cubeVAO;

    Material dirt;

    Mesh cube;



    @Override
    public void initialize() {

        Shader shader = new Shader("src/main/java/demo_game/vertex.glsl", "src/main/java/demo_game/fragment.glsl");
        shader.use();
        Texture texture = new Texture("game_resources/dirt.jpg");

        dirt = new Material(shader, texture);

        cubeVAO = glGenVertexArrays();
        glBindVertexArray(cubeVAO);
        cube = new Mesh.Builder().cube().build();
        cube.bind();

        VertexAttribute.POSITION.enable();

        VertexAttribute.TEXCOORD.enable();
        glBindVertexArray(0);

    }

    @Override
    public void update() {
        if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            stop();
        }
    }

    @Override
    public void draw() {
        glBindVertexArray(cubeVAO);
        dirt.use();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }
}

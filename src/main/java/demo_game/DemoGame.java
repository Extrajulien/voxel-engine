package demo_game;

import doctrina.Game;
import doctrina.rendering.*;

import static org.lwjgl.glfw.GLFW.*;

public class DemoGame extends Game {



    private Model cubeModel;



    @Override
    public void initialize() {

        Shader shader = new Shader("src/main/java/demo_game/vertex.glsl", "src/main/java/demo_game/fragment.glsl");
        shader.use();
        Texture texture = new Texture("game_resources/dirt.jpg");
        Material dirt = new Material(shader, texture);
        Mesh cube = new Mesh.Builder().cube().build();

        cubeModel = new Model(cube, dirt);
    }

    @Override
    public void update() {
        if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            stop();
        }
    }

    @Override
    public void draw() {
        cubeModel.draw();
    }
}

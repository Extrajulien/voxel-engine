package demo_game;

import doctrina.Game;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static java.lang.Math.sin;
import static org.lwjgl.glfw.GLFW.*;

public class DemoGame extends Game {
    private Model cubeModel;
    private Model toasterCube;
    private final Matrix4f projectionMatrix = new Matrix4f().perspective(1, (float)16/9, 0.1f, 1000);

    @Override
    public void initialize() {

        Shader shader = new Shader("vertex.glsl", "fragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("dirt.jpg");
        Texture toasterTex = new Texture("toaster.png");
        Material dirt = new Material(shader, toasterTex);
        Material toaster = new Material(shader, dirtTex);
        Mesh cube = new Mesh.Builder().cube().build();

        cubeModel = new Model(cube, dirt);
        toasterCube = new Model(cube, toaster);
    }

    @Override
    public void update() {
        if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            stop();
        }
    }

    @Override
    public void draw() {
        float angle = (float)sin (glfwGetTime());
        cubeModel.draw(new Matrix4f().rotateX(angle).rotateY(angle).rotateZ(angle), new Matrix4f().translate(new Vector3f(0,0,-2f)), projectionMatrix);
        toasterCube.draw(new Matrix4f(), new Matrix4f().translate(new Vector3f(0,0,-2f)), projectionMatrix);
    }
}

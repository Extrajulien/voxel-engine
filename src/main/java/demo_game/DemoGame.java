package demo_game;

import doctrina.Game;
import doctrina.Input.Key;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DemoGame extends Game {
    private Model cubeModel;
    private float camX = 0;
    private float camZ = 0;
    private final Matrix4f projectionMatrix = new Matrix4f().perspective(1, (float)16/9, 0.1f, 1000);
    private Matrix4f viewMatrix;

    @Override
    public void initialize() {

        Shader shader = new Shader("vertex.glsl", "fragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("dirt.jpg");
        Texture toasterTex = new Texture("toaster.png");
        Material dirt = new Material(shader, dirtTex);
        Material toaster = new Material(shader, toasterTex);
        Mesh cube = new Mesh.Builder().cube().build();

        cubeModel = new Model(cube, dirt);
    }

    @Override
    public void update() {
        if (Key.ESCAPE.isPressed(RenderingEngine.getWindow())) {
            stop();
        }

        if (Key.W.isPressed(RenderingEngine.getWindow())) {
            camZ -= (1 * deltaTime());
        }
        if (Key.S.isPressed(RenderingEngine.getWindow())) {
            camZ += (1 * deltaTime());
        }

        if (Key.A.isPressed(RenderingEngine.getWindow())) {
            camX -= (1 * deltaTime());
        }
        if (Key.D.isPressed(RenderingEngine.getWindow())) {
            camX += (1 * deltaTime());
        }

        viewMatrix = new Matrix4f().lookAt(
                new Vector3f(camX, 0.0f, camZ),
                new Vector3f(0.0f, 0.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f)
        );
    }

    @Override
    public void draw() {
        cubeModel.draw(new Matrix4f(), viewMatrix, projectionMatrix);
    }
}

package demo_game;

import doctrina.Game;
import doctrina.Input.*;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DemoGame extends Game {
    Mouse mouse;
    Keyboard keyboard;
    private Model cubeModel;
    private float camX = 0;
    private float camY = 0;
    private float camZ = 0;
    private float cameraSpeed = 2;
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
        mouse = new Mouse();
        keyboard = new Keyboard();

        cubeModel = new Model(cube, dirt);
    }

    @Override
    public void update() {
        if (keyboard.isDown(Key.ESCAPE)) {
            stop();
        }

        if (keyboard.isDown(Key.F11)) {
            toggleFullscreen(true);
        }

        if (keyboard.isDown(Key.W)) {
            camZ -= (float) (cameraSpeed * deltaTime());
        }
        if (keyboard.isDown(Key.S)) {
            camZ += (float) (cameraSpeed * deltaTime());
        }

        if (keyboard.isDown(Key.A)) {
            camX -= (float) (cameraSpeed * deltaTime());
        }
        if (keyboard.isDown(Key.D)) {
            camX += (float) (cameraSpeed * deltaTime());
        }

        if (mouse.isPressed(MouseButton.LEFT)) {
            camY += (float) (cameraSpeed * deltaTime());
            mouse.freeCursor();
        }
        if (mouse.isPressed(MouseButton.RIGHT)) {
            camY -= (float) (cameraSpeed * deltaTime());
            mouse.captureCursor();
        }

        viewMatrix = new Matrix4f().lookAt(
                new Vector3f(camX, camY, camZ),
                new Vector3f(0.0f, 0.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f)
        );
    }

    @Override
    public void draw() {
        cubeModel.draw(new Matrix4f(), viewMatrix, projectionMatrix);
    }
}

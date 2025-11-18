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
    private boolean isFullscreen = false;
    private Camera camera;
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
        mouse = new Mouse(0.1f);
        keyboard = new Keyboard();
        camera = new Camera(mouse, keyboard);
        camera.setSpeed(cameraSpeed);
        camera.setSensitivity(0.1);

        cubeModel = new Model(cube, dirt);
    }

    @Override
    public void update() {

        if (keyboard.isDown(Key.ESCAPE)) {
            stop();
        }

        if (keyboard.isPressed(Key.F11)) {
            isFullscreen = !isFullscreen;
            toggleFullscreen(isFullscreen);
        }


        camera.updateCamera();
        mouse.clearDelta();
        viewMatrix = camera.getViewMatrix();
    }

    @Override
    public void draw() {
        cubeModel.draw(new Matrix4f(), viewMatrix, projectionMatrix);
    }
}

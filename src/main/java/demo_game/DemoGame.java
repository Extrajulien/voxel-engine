package demo_game;

import doctrina.Entities.Entity;
import doctrina.Game;
import doctrina.Input.*;
import doctrina.rendering.*;
import org.joml.Vector3f;

public class DemoGame extends Game {
    Mouse mouse;
    Keyboard keyboard;
    private boolean isFullscreen = false;
    private Camera camera;
    private Camera camera2;
    private Entity cubeEntity;

    @Override
    public void initialize() {
        Shader shader = new Shader("vertex.glsl", "imageFragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("dirt.jpg");
        Texture toasterTex = new Texture("toaster.png");
        Material dirt = new Material(shader, dirtTex);
        Material toaster = new Material(shader, toasterTex);
        Mesh cube = new Mesh.Builder().cube().build();
        mouse = new Mouse(0.1f);
        keyboard = new Keyboard();
        camera = new Camera(mouse);
        camera.setSensitivity(1);
        camera.moveTo(new Vector3f(0,0,5));

        camera2 = new Camera(mouse);
        camera2.moveTo(new Vector3f(2,0,0));
        camera2.setSensitivity(1);

        Model cubeModel = new Model(cube, dirt);
        cubeEntity = new EntityTest(cubeModel);
        cubeEntity.moveTo(0,0,0);
        mouse.captureCursor();
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

        if (keyboard.isDown(Key.W)) {
            camera.move(new Vector3f (0,0, (float) -deltaTime()));
        }

        if (keyboard.isDown(Key.S)) {
            camera.move(new Vector3f (0,0, (float) deltaTime()));
        }

        if (keyboard.isDown(Key.ARROW_UP)) {
            camera2.move(new Vector3f (0,0, (float) -deltaTime()));
        }

        if (keyboard.isDown(Key.ARROW_DOWN)) {
            camera2.move(new Vector3f (0,0, (float) deltaTime()));
        }

        if (mouse.isPressed(MouseButton.RIGHT)) {
            mouse.freeCursor();
        }

        if (mouse.isPressed(MouseButton.LEFT)) {
            mouse.captureCursor();
        }

        camera.updateCamera();
        camera2.updateCamera();
        mouse.clearDelta();
    }

    @Override
    public void draw() {

        cubeEntity.draw(camera);
        cubeEntity.drawHitBox(camera);

        cubeEntity.draw(camera2);
    }
}

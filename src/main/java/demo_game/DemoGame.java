package demo_game;

import demo_game.Inputs.GameKMController;
import demo_game.Uniforms.CubeUniform;
import doctrina.Entities.Entity;
import doctrina.Game;
import doctrina.Input.*;
import doctrina.rendering.*;
import org.joml.Vector3f;

public class DemoGame extends Game {
    Mouse mouse;
    Keyboard keyboard;
    private boolean isFullscreen = false;
    private Player player;
    private Entity cubeEntity;
    private World world;
    private GameKMController controller;

    @Override
    public void initialize() {
        Shader<CubeUniform> shader = new Shader<>(CubeUniform.class, "vertex.glsl", "imageFragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("dirt.jpg");
        Material<CubeUniform> dirt = new Material<>(shader, dirtTex);
        Mesh cube = new Mesh.Builder().cube().build();
        Model<CubeUniform> cubeModel = new Model<>(cube, dirt);
        cubeEntity = new EntityTest(cubeModel);

        world = new World(0);



        mouse = new Mouse(0.1f);
        keyboard = new Keyboard();
        controller = new GameKMController(keyboard, mouse);


        player = new Player(controller);

        cubeEntity.moveTo(0,0,-5);
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

        if (mouse.isPressed(MouseButton.RIGHT)) {
            mouse.freeCursor();
        }

        if (mouse.isPressed(MouseButton.LEFT)) {
            mouse.captureCursor();
        }

        player.update(deltaTime());
        controller.update();
    }

    @Override
    public void draw() {

        cubeEntity.draw(player.getCameraView());
        cubeEntity.drawHitBox(player.getCameraView());

        player.draw(player.getCameraView());
        player.drawHitBox(player.getCameraView());

    }
}

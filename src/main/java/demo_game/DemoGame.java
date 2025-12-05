package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.GameKMController;
import demo_game.Player.Player;
import demo_game.Uniforms.PlayerUniform;
import demo_game.WorldGen.World;
import doctrina.Entities.Entity;
import doctrina.Game;
import doctrina.Input.*;
import doctrina.rendering.*;

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
        Shader<PlayerUniform> shader = new Shader<>(PlayerUniform.class, "vertex.glsl", "imageFragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("toaster.png");
        Material<PlayerUniform> dirt = new Material<>(shader, dirtTex);
        Mesh cube = new Mesh.Builder().cube().build();
        Model<PlayerUniform> cubeModel = new Model<>(cube, dirt);
        cubeEntity = new EntityTest(cubeModel);

        mouse = new Mouse(0.1f);
        keyboard = new Keyboard();
        controller = new GameKMController(keyboard, mouse);

        player = new Player(controller);

        world = new World(0, player);

        cubeEntity.moveTo(0,0,0);
        mouse.captureCursor();
    }

    @Override
    public void update() {
        world.loadChunks(player);

        if (controller.isPressed(Action.QUIT)) {
            stop();
        }

        if (controller.isPressed(Action.TOGGLE_FULLSCREEN)) {
            isFullscreen = !isFullscreen;
            toggleFullscreen(isFullscreen);
        }

        if (controller.isPressed(Action.TOGGLE_CHUNK_BOUNDING_BOX)) {
            boolean isShown = !world.isBoundingBoxShown();
            world.setBoundingBoxShown(isShown);
        }

        player.update(deltaTime());
        controller.update();
    }

    @Override
    public void draw() {
        world.draw(player);
        cubeEntity.draw(player.getCameraView());
        cubeEntity.drawHitBox(player.getCameraView());

        player.draw(player.getCameraView());
        player.drawHitBox(player.getCameraView());

    }
}

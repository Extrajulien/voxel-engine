package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.GameKMController;
import demo_game.Player.Player;
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
        mouse = new Mouse(0.1f);
        keyboard = new Keyboard();
        controller = new GameKMController(keyboard, mouse);
        player = new Player(controller);
        world = new World(0, player);
        cubeEntity = new EntityTest();
        cubeEntity.moveTo(0,0,0);
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

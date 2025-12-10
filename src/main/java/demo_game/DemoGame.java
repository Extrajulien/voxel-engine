package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.GameKMController;
import demo_game.Player.Player;
import demo_game.World.Chunk.ChunkRenderingMode;
import demo_game.World.World;
import doctrina.Entities.Entity;
import doctrina.Game;
import doctrina.Input.*;

public class DemoGame extends Game {
    Mouse mouse;
    Keyboard keyboard;
    private boolean isFullscreen = false;
    private Player player;
    private Entity cubeEntity;
    private World world;
    private GameKMController controller;
    private ChunkRenderingMode currentMode;

    @Override
    public void initialize() {
        mouse = new Mouse(0.1f);
        keyboard = new Keyboard();
        controller = new GameKMController(keyboard, mouse);
        player = new Player(controller);
        world = new World(1, player);
        cubeEntity = new EntityTest();
        cubeEntity.moveTo(0,0,0);
        currentMode = ChunkRenderingMode.NORMAL;
    }

    @Override
    public void update() {

        world.update(player, deltaTime());
        world.updateCollision(player);
        player.update(deltaTime());
        if (controller.isPressed(Action.QUIT)) {
            stop();
        }

        if (controller.isPressed(Action.TOGGLE_FULLSCREEN)) {
            isFullscreen = !isFullscreen;
            toggleFullscreen(isFullscreen);
        }

        if (controller.isPressed(Action.TOGGLE_CHUNK_RENDERING_MODE)) {
            currentMode = currentMode.next();
        }


        controller.update();
    }

    @Override
    public void draw() {
        world.draw(player, currentMode);
        world.drawCollisionBlocks(player);
        cubeEntity.draw(player.getCameraView());
        cubeEntity.drawHitBox(player.getCameraView());

        player.draw(player.getCameraView());
        player.drawSpeedBox(player.getCameraView());

    }
}

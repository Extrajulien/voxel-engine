package demo_game.Player;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import demo_game.debug.LogEntry;
import demo_game.debug.Logger;
import doctrina.Input.Controller;
import org.joml.Vector3f;

public final class PlayerMovementHandler {
    private final Vector3f movementDirection;
    private final Controller<Action, Axis> controller;
    private final PlayerCamera camera;
    private final Player player;

    public PlayerMovementHandler(Player player) {
        this.player = player;
        this.controller = player.getController();
        this.camera = player.getCamera();
        movementDirection = new Vector3f(0, 0, 0);
    }

    public void update(double deltaTime) {
        updateSprintFromInput();
        updateCameraModeFromInputs();
        updateMovementDirection();

        updateLog();
    }

    public Vector3f getMovementDirection() {
        return movementDirection;
    }

    public boolean isMoving() {
        return controller.isDown(Action.MOVE_NORTH) ||  controller.isDown(Action.MOVE_SOUTH)
                || controller.isDown(Action.MOVE_EAST) || controller.isDown(Action.MOVE_WEST);
    }

    private void updateCameraModeFromInputs() {
        if (controller.isPressed(Action.TOGGLE_CAMERA_MODE)) {
            camera.switchMode();
        }
    }

    private void updateMovementDirection() {
        boolean movingNorth =  controller.isDown(Action.MOVE_NORTH);
        boolean movingSouth =  controller.isDown(Action.MOVE_SOUTH);
        boolean movingEast =  controller.isDown(Action.MOVE_EAST);
        boolean movingWest =  controller.isDown(Action.MOVE_WEST);
        Vector3f xzLookingDirection = camera.getXZLookingDirectionUnitVector();
        Vector3f eastDirection = new  Vector3f(xzLookingDirection).cross(camera.getWorldUp()).normalize();


        Vector3f direction = new Vector3f();
        if (movingNorth) {
            direction.add(xzLookingDirection);
        }
        if (movingSouth) {
            direction.add(-xzLookingDirection.x, 0, -xzLookingDirection.z);
        }
        if (movingEast) {
            direction.add(eastDirection);
        }
        if (movingWest) {
            direction.add(eastDirection.negate());
        }

        // prevent division by zero
        if (!isMoving() || direction.equals(0,0,0)) {
            movementDirection.zero();
            return;
        }

        movementDirection.set(direction.x, 0, direction.z).normalize();
    }

    private void updateSprintFromInput() {
        if (controller.isPressed(Action.SPRINT)) {
            player.setSprinting(true);
            return;
        }

        if (!controller.isDown(Action.MOVE_NORTH)) {
            player.setSprinting(false);
        }
    }


    private void updateLog() {
        if (controller.isPressed(Action.CHUNK_POS_LOG)) {
            Logger.getInstance().refreshLog(LogEntry.CHUNK_POS);
        }

    }
}

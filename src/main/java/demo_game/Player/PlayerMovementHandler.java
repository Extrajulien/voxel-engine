package demo_game.Player;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
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
    }

    public Vector3f getMovementDirection() {
        return movementDirection;
    }

    public boolean isMoving() {
        return controller.isDown(Action.MOVE_NORTH) ||  controller.isDown(Action.MOVE_SOUTH)
                || controller.isDown(Action.MOVE_EAST) || controller.isDown(Action.MOVE_WEST)
                || controller.isDown(Action.CROUCH) || controller.isDown(Action.JUMP);
    }

    private void updateCameraModeFromInputs() {
        if (controller.isPressed(Action.TOGGLE_CAMERA_MODE)) {
            camera.switchMode();
        }
    }

    private void updateMovementDirection() {
        Vector3f direction = new Vector3f();
        if (controller.isDown(Action.MOVE_NORTH)) {
            direction.add(camera.getLookDirectionUnitVector());
        }
        if (controller.isDown(Action.MOVE_SOUTH)) {
            direction.add(-camera.getLookDirectionUnitVector().x, 0, -camera.getLookDirectionUnitVector().z);
        }
        if (controller.isDown(Action.MOVE_EAST)) {
            direction.add(camera.getLookDirectionUnitVector().cross(camera.getWorldUp()));
        }
        if (controller.isDown(Action.MOVE_WEST)) {
            direction.add(camera.getLookDirectionUnitVector().cross(camera.getWorldUp()).negate());
        }

        if (controller.isDown(Action.CROUCH)) {
            direction.add(0, -1, 0);
        }

        if (controller.isDown(Action.JUMP)) {
            direction.add(0,1,0);
        }

        // prevent division by zero
        if (!isMoving()) {
            movementDirection.zero();
            return;
        }

        movementDirection.set(direction.x, direction.y, direction.z).normalize();
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
}

package demo_game.Player;

import demo_game.Inputs.Action;
import demo_game.Inputs.Analog;
import demo_game.debug.LogEntry;
import demo_game.debug.Logger;
import doctrina.Input.Controller;
import org.joml.Vector3f;

public final class PlayerMovementHandler {
    private final Vector3f movementDirection;
    private final Controller<Action, Analog> controller;
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
        updateJumpStateFromInput();

        updateLog();
    }

    public Vector3f getMovementDirection() {
        return movementDirection;
    }

    public boolean isMoving() {
        return controller.isDown(Action.MOVE_NORTH) ||  controller.isDown(Action.MOVE_SOUTH)
                || controller.isDown(Action.MOVE_EAST) || controller.isDown(Action.MOVE_WEST) || !player.getState(EntityState.IS_GROUNDED);
    }

    private void updateCameraModeFromInputs() {
        if (controller.isPressed(Action.TOGGLE_CAMERA_MODE)) {
            camera.switchMode();
        }
    }

    private void updateMovementDirection() {
        boolean movingNorth = controller.isDown(Action.MOVE_NORTH);
        boolean movingSouth = controller.isDown(Action.MOVE_SOUTH);
        boolean movingEast  = controller.isDown(Action.MOVE_EAST);
        boolean movingWest  = controller.isDown(Action.MOVE_WEST);
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
            player.setState(EntityState.IS_SPRINTING, true);
            return;
        }

        if (!controller.isDown(Action.MOVE_NORTH)) {
            player.setState(EntityState.IS_SPRINTING, false);
        }
    }

    private void updateJumpStateFromInput() {
        if (player.getState(EntityState.IS_GROUNDED) && controller.isDown(Action.JUMP)) {
            player.setState(EntityState.IS_GROUNDED, false);
            movementDirection.add(0,player.getJumpForce(),0);
        }
    }


    private void updateLog() {
        if (controller.isPressed(Action.CHUNK_POS_LOG)) {
            Logger.getInstance().refreshLog(LogEntry.CHUNK_POS);
        }

        if (controller.isPressed(Action.RAYCASTING_LOG)) {
            Logger.getInstance().refreshLog(LogEntry.CAMERA_COMPONENT);
            Logger.getInstance().refreshLog(LogEntry.CAMERA_POSITION);
            Logger.getInstance().refreshLog(LogEntry.RAYCASTING_TARGET);
        }

    }
}

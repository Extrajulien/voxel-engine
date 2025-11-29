package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import demo_game.Uniforms.CubeUniform;
import doctrina.Entities.ControllableEntity;
import doctrina.Input.Controller;
import doctrina.debug.Color;
import doctrina.rendering.*;
import org.joml.Vector3f;

public class Player extends ControllableEntity<Action, Axis> {
    private final int CHUNK_LOADING_RADIUS = 2;
    private final PlayerCamera camera;
    private final Inventory inventory;
    private Vector3f currentSpeed;
    private float sprintSpeed = 6;
    private boolean isSprinting = false;


    private final static Model<CubeUniform> model = Models.makePlayer();


    public Player(Controller<Action, Axis> controller) {
        super(model, new Vector3f(0.7f, 1.8f, 0.7f), controller);
        this.camera = new PlayerCamera(controller, this);
        walkSpeed = 3;
        currentSpeed = new Vector3f();
        hitbox.setColor(Color.WHITE);
        inventory = new Inventory();
    }

    public void move(Vector3f direction) {
        super.move(direction);
    }





    public void update(double deltaTime) {
        handleInputs(deltaTime);


        if (!inventory.isOpen()) {
            this.move(currentSpeed);
            camera.update();
        }

    }

    public CameraView getCameraView() {
        return camera.GetCameraView();
    }

    private void handleInputs(double deltaTime) {
        setSprintFromInput();
        setSpeedFromInputs(deltaTime);
        setCameraModeFromInputs();
        if (controller.isPressed(Action.INVENTORY_TOGGLE)) {
            if (inventory.isOpen()) {
                closeInventory();
            } else {
                openInventory();
            }
        }
    }


    private void setSpeedFromInputs(double deltaTime) {
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
        if (!isMoving()) {
            currentSpeed.zero();
            return;
        }

        float speed = isSprinting ? sprintSpeed : walkSpeed;
        currentSpeed = new Vector3f(direction.x, 0, direction.z).normalize().mul(speed * (float) deltaTime);
    }

    private void setCameraModeFromInputs() {
        if (controller.isPressed(Action.TOGGLE_CAMERA_MODE)) {
            switchCameraMode();
        }
    }

    private void setSprintFromInput() {
        if (controller.isPressed(Action.SPRINT)) {
            isSprinting = true;
        }

        if (!controller.isDown(Action.MOVE_NORTH)) {
            isSprinting = false;
        }
    }

    private void switchCameraMode() {
        if (camera.getMode() == CameraMode.ORBITAL) {
            camera.setMode(CameraMode.FPS);
            return;
        }
        camera.setMode(CameraMode.ORBITAL);
    }

    private boolean isMoving() {
        return controller.isDown(Action.MOVE_NORTH) ||  controller.isDown(Action.MOVE_SOUTH)
                || controller.isDown(Action.MOVE_EAST) || controller.isDown(Action.MOVE_WEST);
    }

    private void openInventory() {

        inventory.open(controller);
    }

    private void closeInventory() {

        inventory.close(controller);
    }

}

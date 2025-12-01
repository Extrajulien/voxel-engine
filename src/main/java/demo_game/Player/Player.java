package demo_game.Player;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import demo_game.Inventory;
import demo_game.Models;
import demo_game.Uniforms.CubeUniform;
import doctrina.Entities.ControllableEntity;
import doctrina.Input.Controller;
import doctrina.debug.Color;
import doctrina.rendering.*;
import org.joml.Vector3f;

public final class Player extends ControllableEntity<Action, Axis> {
    private final int CHUNK_LOADING_RADIUS = 2;
    private final PlayerCamera camera;
    private final PlayerMovementHandler movementHandler;
    private final Inventory inventory;
    private Vector3f currentSpeed;
    private float sprintSpeed = 6;
    private boolean isSprinting = false;
    private final static Model<CubeUniform> model = Models.makePlayer();

    public Player(Controller<Action, Axis> controller) {
        super(model, new Vector3f(0.7f, 1.8f, 0.7f), controller);

        this.camera = new PlayerCamera(this);
        this.movementHandler = new PlayerMovementHandler(this);
        walkSpeed = 3;
        currentSpeed = new Vector3f();
        hitbox.setColor(Color.WHITE);
        inventory = new Inventory();
    }

    public void move(Vector3f direction) {
        super.move(direction);
    }

    public void update(double deltaTime) {
        /*
        modelMatrix.setRotationXYZ(0, (float) ( Math.acos(new Vector3f(camera.getLookDirectionUnitVector().x, 0, camera.getLookDirectionUnitVector().z).normalize().dot(1,0,0)) ), 0);
        */
        movementHandler.update(deltaTime);
        handleInputs(deltaTime);


        if (!inventory.isOpen()) {
            updateSpeedFromMovement(deltaTime);
            this.move(currentSpeed);
            camera.update();
        }

    }

    public CameraView getCameraView() {
        return camera.GetCameraView();
    }

    private void handleInputs(double deltaTime) {
        if (controller.isPressed(Action.INVENTORY_TOGGLE)) {
            if (inventory.isOpen()) {
                closeInventory();
            } else {
                openInventory();
            }
        }
    }

    private void updateSpeedFromMovement(double deltaTime) {
        Vector3f direction = movementHandler.getMovementDirection();
        float speed = isSprinting ? sprintSpeed : walkSpeed;
        currentSpeed.set(direction.x, 0, direction.z).mul(speed * (float) deltaTime);
    }

    private void openInventory() {
        inventory.open(controller);
    }

    private void closeInventory() {

        inventory.close(controller);
    }

    Controller<Action, Axis> getController() {
        return controller;
    }

    PlayerCamera getCamera() {
        return camera;
    }

    void setSprinting(boolean sprinting) {
        isSprinting = sprinting;
    }
}

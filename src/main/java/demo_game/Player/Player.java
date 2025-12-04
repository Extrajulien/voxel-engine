package demo_game.Player;

import demo_game.WorldGen.Chunk;
import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import demo_game.Inventory;
import demo_game.Models;
import demo_game.Uniforms.CubeUniform;
import doctrina.Entities.ControllableEntity;
import doctrina.Input.Controller;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range3d;
import doctrina.debug.Color;
import doctrina.rendering.*;
import org.joml.Vector3f;
import org.joml.Vector3i;

public final class Player extends ControllableEntity<Action, Axis> {
    private final int CHUNK_LOADING_RADIUS = 6;
    private final PlayerCamera camera;
    private final PlayerMovementHandler movementHandler;
    private final Inventory inventory;
    private final Vector3f currentSpeed;
    private float sprintSpeed = 32;
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

        movementHandler.update(deltaTime);
        handleInputs(deltaTime);


        if (!inventory.isOpen()) {
            updateSpeedFromMovement(deltaTime);
            this.move(currentSpeed);
            camera.update();
        }

    }

    public Range3d getChunkLoadingRange() {
        Vector3i chunkPos = getChunkPos();
        return new Range3d(
                new Range1d(-CHUNK_LOADING_RADIUS + chunkPos.x, CHUNK_LOADING_RADIUS + chunkPos.x),
                new Range1d(-CHUNK_LOADING_RADIUS + chunkPos.y, CHUNK_LOADING_RADIUS + chunkPos.y),
                new Range1d(-CHUNK_LOADING_RADIUS + chunkPos.z, CHUNK_LOADING_RADIUS + chunkPos.z)
        );
    }

    private Vector3i getChunkPos() {
        return Chunk.positionToChunk(position);
    }

    public int getChunkLoadingRadius() {
        return CHUNK_LOADING_RADIUS;
    }

    @Override
    public void draw(CameraView data) {
        if (camera.getMode() != CameraMode.FPS) {
            rotatePlayerModelWithCamera();
            super.draw(data);
        }
    }

    @Override
    public void drawHitBox(CameraView data) {
        if (camera.getMode() != CameraMode.FPS) {
            super.drawHitBox(data);
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
        currentSpeed.set(direction).mul(speed * (float) deltaTime);
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

    private void rotatePlayerModelWithCamera() {
        Vector3f lookingDirectionXZ = new Vector3f(camera.getLookDirectionUnitVector().x, 0, camera.getLookDirectionUnitVector().z).normalize();
        float yRotation =  (float) Math.atan2(lookingDirectionXZ.x, lookingDirectionXZ.z);
        modelMatrix.setRotationXYZ(0, yRotation , 0);

    }


}

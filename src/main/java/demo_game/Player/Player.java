package demo_game.Player;

import demo_game.World.Chunk.Chunk;
import demo_game.Inputs.Action;
import demo_game.Inputs.Analog;
import demo_game.Inventory;
import demo_game.Models;
import doctrina.Entities.ControllableEntity;
import doctrina.Input.Controller;
import doctrina.Utils.BoundingBox;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range3d;
import doctrina.debug.Color;
import doctrina.rendering.*;
import org.joml.Vector3f;
import org.joml.Vector3i;

public final class Player extends ControllableEntity<Action, Analog> {
    private final int CHUNK_LOADING_RADIUS = 2;
    private final PlayerCamera camera;
    private final PlayerMovementHandler movementHandler;
    private final Inventory inventory;
    private float sprintSpeed = 80;
    private boolean isSprinting = false;

    public Player(Controller<Action, Analog> controller) {
        super(Models.makePlayer(), new BoundingBox(new Vector3f(-0.35f,-0.9f,-0.35f), new Vector3f(0.35f,0.9f,0.35f)), controller);

        this.camera = new PlayerCamera(this);
        this.movementHandler = new PlayerMovementHandler(this);
        walkSpeed = 3;
        hitbox.setColor(Color.WHITE);
        inventory = new Inventory();
        this.controller.captureCursor();
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        movementHandler.update(deltaTime);
        handleInputs(deltaTime);

        if (!inventory.isOpen()) {
            updateSpeedFromMovement(deltaTime);
            this.move();
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
        return Chunk.worldToChunkSpace(position);
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

    Controller<Action, Analog> getController() {
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

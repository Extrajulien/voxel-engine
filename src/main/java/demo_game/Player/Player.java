package demo_game.Player;

import demo_game.BlockType;
import demo_game.RaycastHit;
import demo_game.World.Chunk.Chunk;
import demo_game.Inputs.Action;
import demo_game.Inputs.Analog;
import demo_game.Inventory;
import demo_game.Models;
import demo_game.World.World;
import doctrina.Entities.ControllableEntity;
import doctrina.Input.Controller;
import doctrina.Utils.BoundingBox;
import doctrina.Utils.RangeI1d;
import doctrina.Utils.RangeI3d;
import doctrina.Utils.Ray;
import doctrina.debug.Color;
import doctrina.physic.CollisionCandidates;
import doctrina.physic.HitBox;
import doctrina.rendering.*;
import org.joml.Vector3f;
import org.joml.Vector3i;

public final class Player extends ControllableEntity<Action, Analog> {
    private final int CHUNK_LOADING_RADIUS = 3;
    private final PlayerCamera camera;
    private final PlayerMovementHandler movementHandler;
    private final Inventory inventory;
    private final HitBox selectedBlock;
    private final float selectedBlockBoxSizeIncrement = 0.03f;

    private float sprintSpeed = 10;
    private final float reach = 7;
    private final float jumpForce = 0.08f;
    private final float airMovementSpeedMultiplier = 1.4f;

    public Player(Controller<Action, Analog> controller) {
        super(Models.makePlayer(), new BoundingBox(new Vector3f(-0.35f,-0.9f,-0.35f), new Vector3f(0.35f,0.9f,0.35f)), controller);
        position.add(0,15,0);
        this.camera = new PlayerCamera(this);
        this.movementHandler = new PlayerMovementHandler(this);
        walkSpeed = 3;
        hitbox.setColor(Color.WHITE);
        inventory = new Inventory();
        this.controller.captureCursor();
        selectedBlock = new HitBox(new BoundingBox(new Vector3f(0,0,0).sub(new Vector3f(selectedBlockBoxSizeIncrement)),
                new Vector3f(1,1,1).add(new Vector3f(selectedBlockBoxSizeIncrement))), Color.BLACK.getValue());
    }

    public void update(double deltaTime, CollisionCandidates candidates, World world) {
        super.update(deltaTime);
        movementHandler.update(deltaTime);
        handleInputs(deltaTime);




        if (!inventory.isOpen()) {
            updateSpeedFromMovement(deltaTime);
            this.move(candidates, deltaTime);
            camera.update();


            if (controller.isPressed(Action.BREAK_BLOCK)) {
                Ray ray = new Ray(camera.getPosition(), camera.getLookDirectionUnitVector());
                RaycastHit hit = world.raycast(ray, reach);
                if (hit.hit()) {
                    world.breakBlock(hit.position());
                }
            }

            if (controller.isPressed(Action.PLACE_BLOCK)) {
                Ray ray = new Ray(camera.getPosition(), camera.getLookDirectionUnitVector());
                RaycastHit hit = world.raycast(ray, reach);
                if (hit.hit()) {
                    world.placeBlock(hit.position().add(hit.normal()), BlockType.DIRT);
                }
            }
        }

    }

    public RangeI3d getChunkLoadingRange() {
        Vector3i chunkPos = getChunkPos();
        return new RangeI3d(
                new RangeI1d(-CHUNK_LOADING_RADIUS + chunkPos.x, CHUNK_LOADING_RADIUS + chunkPos.x),
                new RangeI1d(-CHUNK_LOADING_RADIUS + chunkPos.y, CHUNK_LOADING_RADIUS + chunkPos.y),
                new RangeI1d(-CHUNK_LOADING_RADIUS + chunkPos.z, CHUNK_LOADING_RADIUS + chunkPos.z)
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


    public void drawLookedAtBlockEdge(CameraView data, World world) {
        Ray ray = new Ray(camera.getPosition(), camera.getLookDirectionUnitVector());
        RaycastHit hit = world.raycast(ray, reach);
        if (hit.hit()) {
            selectedBlock.update(new Vector3f(hit.position()).add(0.5f,0.5f,0.5f));
            selectedBlock.drawBounds(data.viewMatrix(), data.projectionMatrix());
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
        float speed = getState(EntityState.IS_SPRINTING) ? sprintSpeed : walkSpeed;
        currentSpeed.x = direction.x * (speed * (float) deltaTime);
        currentSpeed.z = direction.z * (speed * (float) deltaTime);

        if (!getState(EntityState.IS_GROUNDED)) {
            currentSpeed.x *= airMovementSpeedMultiplier;
            currentSpeed.z *= airMovementSpeedMultiplier;
        }

        currentSpeed.add(0,direction.y,0);
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


    float getJumpForce() {
        return jumpForce;
    }



    private void rotatePlayerModelWithCamera() {
        Vector3f lookingDirectionXZ = new Vector3f(camera.getLookDirectionUnitVector().x, 0, camera.getLookDirectionUnitVector().z).normalize();
        float yRotation =  (float) Math.atan2(lookingDirectionXZ.x, lookingDirectionXZ.z);
        modelMatrix.setRotationXYZ(0, yRotation , 0);

    }
}

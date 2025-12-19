package doctrina.rendering;

import demo_game.debug.LogEntry;
import demo_game.debug.Logger;
import doctrina.Entities.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private final float FAR_PLANE = 10000f;
    private float orbitalRadius = 2;
    private float fpsCamYTranslation = 0f;
    private float fpsCamRadius = 1f;
    private float maxPitch = 85f;
    private float minPitch = -85f;
    private float fovY;

    private double yaw = -90;
    private double pitch = 0;
    private double sensitivity = 0.1;

    private final Vector3f origin;
    private final Vector3f position;
    private final Vector3f worldUp;
    private final Vector3f center;
    private final Vector3f lookingDirection;

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;

    private CameraMode mode;

    public Camera(Entity entity, CameraMode mode) {
        this.mode = mode;
        origin = entity.getPosition();
        worldUp = new Vector3f(0,1,0);
        fovY = (float) Math.toRadians(90);
        projectionMatrix  = new Matrix4f();
        position = new Vector3f();
        computeProjectionMatrix();
        viewMatrix = new Matrix4f();
        center = new Vector3f();
        lookingDirection = new Vector3f();
        updateValues();
    }

    public CameraView GetCameraView() {
        return new CameraView(viewMatrix, projectionMatrix);
    }

    public void setMode(CameraMode mode) {
        this.mode = mode;
    }

    public CameraMode getMode() {
        return mode;
    }

    public Vector3f getLookDirectionUnitVector() {
        return new Vector3f(lookingDirection);
    }

    public Vector3f getXZLookingDirectionUnitVector() {
        return new Vector3f(lookingDirection.x, 0, lookingDirection.z).normalize();
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public Vector3f getWorldUp() {
        return worldUp;
    }

    public void update() {
        updateValues();
        Logger.getInstance().Log(LogEntry.CAMERA_COMPONENT, lookingDirection);
        Logger.getInstance().Log(LogEntry.CAMERA_POSITION, position);
    }

    public void setFpsCamYTranslation(float fpsCamYTranslation) {
        this.fpsCamYTranslation = fpsCamYTranslation;
    }

    public void setFpsCamRadius(float fpsCamRadius) {
        this.fpsCamRadius = fpsCamRadius;
    }

    public void setMaxPitch(float maxPitch) {
        this.maxPitch = maxPitch;
    }

    public void setMinPitch(float minPitch) {
        this.minPitch = minPitch;
    }

    public void setOrbitalRadius(float orbitalRadius) {
        this.orbitalRadius = orbitalRadius;
    }

    public void addYaw(float delta) {
        yaw += delta * sensitivity;
    }

    public void addPitch(float delta) {
        double rawPitch = delta * sensitivity;
        pitch -= rawPitch;
        if (pitch <= minPitch) {
            pitch = minPitch;
        }
        if (pitch >= maxPitch) {
            pitch = maxPitch;
        }
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    public void setVerticalFOV(float angleDegrees) {
        fovY = (float) Math.toRadians(angleDegrees);
        computeProjectionMatrix();
    }

    private void computeProjectionMatrix() {
        this.projectionMatrix.setPerspective(fovY, RenderingEngine.getAspectRatio(), 0.1f, FAR_PLANE);
    }

    private void computeViewMatrix() {
        viewMatrix.setLookAt(
                position,
                center,
                worldUp);
    }

    private void updateCenter() {
        center.set(
                position.x + (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
                position.y + (float) (Math.sin(Math.toRadians(pitch))),
                position.z + (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))
        );
    }

    private void updateCameraDirection() {
        lookingDirection.set(center).sub(position).normalize();
    }

    private void translateCamera() {
        switch (mode) {
            case FPS -> fpsTranslation();
            case ORBITAL -> orbitalTranslation();
        }
    }

    private void updateValues() {
        translateCamera();
        updateCenter();
        updateCameraDirection();
        computeViewMatrix();
    }

    private void orbitalTranslation() {
        position.set(
                (float) (origin.x + -orbitalRadius * Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw))),
                (float) (origin.y + -orbitalRadius * Math.sin(Math.toRadians(pitch))),
                (float) (origin.z + -orbitalRadius * Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)))
        );
    }


    private void fpsTranslation() {
        position.set(
                (float) (origin.x + fpsCamRadius * Math.cos(Math.toRadians(yaw))),
                origin.y + fpsCamYTranslation,
                (float) (origin.z + fpsCamRadius * Math.sin(Math.toRadians(yaw)))
        );
    }
}

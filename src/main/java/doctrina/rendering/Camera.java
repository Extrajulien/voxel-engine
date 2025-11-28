package doctrina.rendering;

import doctrina.Entities.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    protected float fpsCamYTranslation = 0f;
    protected float fpsCamRadius = 1f;
    protected float maxPitch = 85f;
    protected float minPitch = -85f;

    private double yaw = -90;
    private double pitch = 0;
    private double sensitivity = 0.1;
    private Vector3f origin;
    private Vector3f position;
    private final Vector3f worldUp;
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private CameraMode mode;
    private Vector3f center = new Vector3f();
    private Vector3f lookingDirection = new Vector3f();
    private float fovY;
    private float orbitalRadius = 2;

    public Camera(Entity entity, CameraMode mode) {
        this.mode = mode;
        origin = entity.getPosition();
        worldUp = new Vector3f(0,1,0);
        fovY = (float) Math.toRadians(90);
        projectionMatrix  = new Matrix4f();
        computeProjectionMatrix();
        viewMatrix = new Matrix4f();
        updateValues();
    }

    protected void moveTo(Vector3f pos) {
        origin = pos;
    }

    public void move(Vector3f pos) {
        origin = origin.add(pos);
    }

    public CameraView GetCameraView() {
        return new CameraView(viewMatrix, projectionMatrix);
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public void setMode(CameraMode mode) {
        this.mode = mode;
    }

    public CameraMode getMode() {
        return mode;
    }

    public Vector3f getLookDirectionUnitVector() {
        return lookingDirection;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public Vector3f getWorldUp() {
        return worldUp;
    }

    public void update() {
        updateValues();
    }

    protected void addYaw(float delta) {
        yaw += delta * sensitivity;
    }

    protected void addPitch(float delta) {
        double rawPitch = delta * sensitivity;

        pitch -= rawPitch;
        if (pitch <= minPitch) {
            pitch = minPitch;
        }
        if (pitch >= maxPitch) {
            pitch = maxPitch;
        }
    }

    protected void setVerticalFOV(float angleDegrees) {
        fovY = (float) Math.toRadians(angleDegrees);
        computeProjectionMatrix();
    }

    private void computeProjectionMatrix() {
        this.projectionMatrix.setPerspective(fovY, RenderingEngine.getAspectRatio(), 0.1f, 1000);
    }

    private void computeViewMatrix() {
        viewMatrix.setLookAt(
                position,
                center,
                worldUp);
    }




    private void updateCenter() {
        center = new Vector3f(
                position.x + (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
                position.y + (float) (Math.sin(Math.toRadians(pitch))),
                position.z + (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))
        );
    }

    private void updateCameraDirection() {
        lookingDirection = new Vector3f(center).sub(position).normalize();
    }

    private void translateCamera() {
        switch (mode) {
            case FPS -> {
                fpsTranslation();
                return;
            }
            case ORBITAL -> {
                orbitalTranslation();
                return;
            }
        }
    }

    private void updateValues() {
        translateCamera();
        updateCenter();
        updateCameraDirection();
        computeViewMatrix();
    }



    private void orbitalTranslation() {
        position = new Vector3f(
                (float) (origin.x + -orbitalRadius * Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw))),
                (float) (origin.y + -orbitalRadius * Math.sin(Math.toRadians(pitch))),
                (float) (origin.z + -orbitalRadius * Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)))
        );
    }


    private void fpsTranslation() {
        position = new Vector3f(
                (float) (origin.x + fpsCamRadius * Math.cos(Math.toRadians(yaw))),
                origin.y + fpsCamYTranslation,
                (float) (origin.z + fpsCamRadius * Math.sin(Math.toRadians(yaw)))
        );
    }
}

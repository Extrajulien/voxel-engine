package doctrina.rendering;

import doctrina.Input.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private double yaw = -90;
    private double pitch = 0;
    private double sensitivity = 0.1;
    private Vector3f position;
    private final Vector3f worldUp;
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private float fovY;
    private final Mouse mouse;

    public Camera(Mouse mouse) {
        this.mouse = mouse;
        position = new Vector3f(0,0,0);
        worldUp = new Vector3f(0,1,0);
        fovY = (float) Math.toRadians(90);
        projectionMatrix  = new Matrix4f();
        computeProjectionMatrix();
        viewMatrix = new Matrix4f();
        computeViewMatrix();
    }

    public void moveTo(Vector3f pos) {
        position = pos;
    }

    public void move(Vector3f pos) {
        position = position.add(pos);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void updateCamera() {
        yaw += mouse.getCursorDelta().x * sensitivity;
        double rawPitch = mouse.getCursorDelta().y * sensitivity;

        pitch -= rawPitch;
        if (pitch < -90) {
            pitch = -89.9999;
        }
        if (pitch > 90) {
            pitch = 89.9999;
        }
        computeViewMatrix();
    }

    public void setVerticalFOV(float angleDegrees) {
        fovY = (float) Math.toRadians(angleDegrees);
        computeProjectionMatrix();
    }

    private void computeProjectionMatrix() {
        this.projectionMatrix.setPerspective(fovY, RenderingEngine.getAspectRatio(), 0.1f, 1000);
    }

    private void computeViewMatrix() {
        viewMatrix.setLookAt(
                position,
                new Vector3f(
                        position.x + (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
                        position.y + (float) (Math.sin(Math.toRadians(pitch))),
                        position.z + (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))),
                worldUp);
    }
}

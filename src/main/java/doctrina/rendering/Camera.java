package doctrina.rendering;

import doctrina.Input.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private double yaw = -90;
    private double pitch = 0;
    private double speed = 1;
    private double sensitivity = 0.1;
    private Vector3f position;
    private Vector3f front;
    private Vector3f right;
    private Vector3f up;
    private Vector3f worldUp;
    private final Matrix4f projectionMatrix = new Matrix4f().perspective(1, (float)16/9, 0.1f, 1000);
    private Mouse mouse;
    private Keyboard keyboard;

    public Camera(Mouse mouse, Keyboard keyboard) {
        this.mouse = mouse;
        this.keyboard = keyboard;
        position = new Vector3f(0,0,0);
        front = new Vector3f(0,0,0);
        right = new Vector3f(0,0,0);
        up = new Vector3f(0,1,0);
        worldUp = new Vector3f(0,1,0);
    }

    public void move(Vector3f pos) {
        position = pos;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f view = new Matrix4f().setLookAt(
                position,
                new Vector3f(
                        position.x + (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
                        position.y + (float) (Math.sin(Math.toRadians(pitch))),
                        position.z + (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))),
                up);
        return view;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }



    public void updateCamera() {
        yaw += mouse.getCursorDelta().x;
        double rawPitch = mouse.getCursorDelta().y;

        pitch -= rawPitch;
        if (pitch < -90) {
            pitch = -90;
        }
        if (pitch > 90) {
            pitch = 90;
        }
    }
}

package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private Vector2d position;
    private Vector2d delta;
    private float sensitivity;
    private double deadZone = 0.4;

    public Mouse(float sensitivity) {
        glfwSetCursorPosCallback(RenderingEngine.getWindow().getId(), cursorCallback);
        position = new Vector2d(0,0);
        delta = new Vector2d();
        this.sensitivity = sensitivity;
    }

    private final GLFWCursorPosCallbackI cursorCallback = (window, xpos, ypos) -> {
        double rawDX = xpos - this.position.x;
        double rawDY = ypos - this.position.y;

        // deadzone on raw input
        if (Math.abs(rawDX) < deadZone) rawDX = 0;
        if (Math.abs(rawDY) < deadZone) rawDY = 0;

        // apply sensitivity AFTER
        this.delta.x = rawDX * sensitivity;
        this.delta.y = rawDY * sensitivity;

        // update last position
        this.position.set(xpos, ypos);
    };



    public Vector2d getCursorPosition() {
        return position;
    }

    public Vector2d getCursorDelta() {
        return delta;
    }

    public void clearDelta() {
        delta.zero();
    }
    public boolean isPressed(MouseButton button) {
        return glfwGetMouseButton(RenderingEngine.getWindow().getId(), button.glfwKeyCode) == GLFW_PRESS;
    }


    public void captureCursor() {
        long window = RenderingEngine.getWindow().getId();
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        double[] px = new double[1];
        double[] py = new double[1];
        glfwGetCursorPos(window, px, py);

        position.x = px[0];
        position.y = py[0];

        delta.zero();
    }

    public void freeCursor() {
        glfwSetInputMode(RenderingEngine.getWindow().getId(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
}

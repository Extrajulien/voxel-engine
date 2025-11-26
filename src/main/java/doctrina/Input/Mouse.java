package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private Vector2d position;
    private Vector2d delta;
    private Vector2d scrollOffset;
    private float sensitivity;
    private double deadZone = 0.4;

    public Mouse(float sensitivity) {
        glfwSetCursorPosCallback(RenderingEngine.getWindow().getId(), cursorCallback);
        glfwSetScrollCallback(RenderingEngine.getWindow().getId(), scrollCallback);
        position = makeEmptyVector2d();
        delta = makeEmptyVector2d();
        scrollOffset = makeEmptyVector2d();

        this.sensitivity = sensitivity;
    }

    private final GLFWCursorPosCallbackI cursorCallback = (window, xPos, yPos) -> {
        double rawDX = xPos - this.position.x;
        double rawDY = yPos - this.position.y;

        // deadzone on raw input
        if (Math.abs(rawDX) < deadZone) rawDX = 0;
        if (Math.abs(rawDY) < deadZone) rawDY = 0;

        // apply sensitivity AFTER
        this.delta.x = rawDX * sensitivity;
        this.delta.y = rawDY * sensitivity;

        // update last position
        this.position.set(xPos, yPos);
    };

    private final GLFWScrollCallbackI scrollCallback = (window, xOffset, yOffset) -> {
        scrollOffset = new Vector2d(xOffset, yOffset);
    };

    public double getAxis(MouseAxis axis) {
        return switch (axis) {
            case SCROLL_X -> scrollOffset.x;
            case SCROLL_Y -> scrollOffset.y;
            case DELTA_X -> delta.x;
            case DELTA_Y -> delta.y;
            case POSITION_X -> position.x;
            case POSITION_Y -> position.y;
        };
    }

    public void update() {
        delta.zero();
        scrollOffset.zero();
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

    private Vector2d makeEmptyVector2d() {
        return new Vector2d(0,0);
    }
}

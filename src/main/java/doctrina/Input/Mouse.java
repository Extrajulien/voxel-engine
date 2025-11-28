package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private Vector2d position;
    private Vector2d delta;
    private Vector2d scrollOffset;
    private float sensitivity;
    private double deadZone = 0.4;

    private final ArrayList<MouseButton> pressedButtons = new ArrayList<>();
    private final ArrayList<MouseButton> downButtons = new ArrayList<>();
    private final ArrayList<MouseButton> releasedButtons = new ArrayList<>();

    public Mouse(float sensitivity) {
        glfwSetCursorPosCallback(RenderingEngine.getWindow().getId(), cursorCallback);
        glfwSetScrollCallback(RenderingEngine.getWindow().getId(), scrollCallback);
        glfwSetMouseButtonCallback(RenderingEngine.getWindow().getId(), buttonCallback);
        position = makeEmptyVector2d();
        delta = makeEmptyVector2d();
        scrollOffset = makeEmptyVector2d();

        this.sensitivity = sensitivity;
    }

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
        pressedButtons.clear();
        releasedButtons.clear();
    }

    public boolean isPressed(MouseButton button) {
        return pressedButtons.contains(button);
    }

    public boolean isReleased(MouseButton button) {
        return releasedButtons.contains(button);
    }

    public boolean isDown(MouseButton button) {
        return downButtons.contains(button);
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

    private final GLFWMouseButtonCallbackI buttonCallback = (window, button, action, mods) -> {
        MouseButton mouseButton = MouseButton.fromInt(button);
        if (mouseButton == null) return;
        if (action == GLFW_PRESS) {
            pressedButtons.add(mouseButton);
            downButtons.add(mouseButton);
            return;
        }
        if (action == GLFW_RELEASE) {
            downButtons.remove(mouseButton);
            releasedButtons.add(mouseButton);
        }
    };

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
}

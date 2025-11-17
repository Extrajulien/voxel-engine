package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private Vector2d position;
    private Vector2d delta;

    public Mouse() {
        glfwSetCursorPosCallback(RenderingEngine.getWindow().getId(), cursorCallback);
        position = new Vector2d(0,0);
        delta = new Vector2d();
    }

    private final GLFWCursorPosCallbackI cursorCallback = (window, xpos, ypos) -> {
        this.delta.x = xpos - this.position.x;
        this.delta.y = ypos - this.position.y;
        this.position.x = xpos;
        this.position.y = ypos;
    };



    public Vector2d getCursorPosition() {
        return position;
    }

    public Vector2d getCursorDelta() {
        return delta;
    }

    public boolean isPressed(MouseButton button) {
        return glfwGetMouseButton(RenderingEngine.getWindow().getId(), button.glfwKeyCode) == GLFW_PRESS;
    }


    public void captureCursor() {
        glfwSetInputMode(RenderingEngine.getWindow().getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void freeCursor() {
        glfwSetInputMode(RenderingEngine.getWindow().getId(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
}

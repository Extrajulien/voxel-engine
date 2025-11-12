package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private Vector2d position;

    public Mouse() {
        glfwSetCursorPosCallback(RenderingEngine.getWindow(), cursorCallback);
        position = new Vector2d();
    }

    private final GLFWCursorPosCallbackI cursorCallback = (window, xpos, ypos) -> {
        this.position.x = xpos;
        this.position.y = ypos;
    };



    public Vector2d getCursorPosition() {
        return position;
    }

    public boolean isPressed(MouseButton button) {
        return glfwGetMouseButton(RenderingEngine.getWindow(), button.glfwKeyCode) == GLFW_PRESS;
    }


    public void captureCursor() {
        glfwSetInputMode(RenderingEngine.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void freeCursor() {
        glfwSetInputMode(RenderingEngine.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
}

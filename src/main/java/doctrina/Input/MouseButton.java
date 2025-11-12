package doctrina.Input;

import static org.lwjgl.glfw.GLFW.*;

public enum MouseButton {
    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT);

    public final int glfwKeyCode;

    MouseButton(int keyCode) {
        glfwKeyCode = keyCode;
    }
}

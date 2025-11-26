package doctrina.Input;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public enum MouseButton {
    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT);

    public final int glfwKeyCode;

    private static final HashMap<Integer, MouseButton> lookup = generateLookup();

    MouseButton(int keyCode) {
        glfwKeyCode = keyCode;
    }

    public static MouseButton fromInt(int glfwKeyCode) {
        return lookup.get(glfwKeyCode);
    }

    private static HashMap generateLookup() {
        HashMap<Integer, MouseButton> lookup = new HashMap<>();
        for (MouseButton button : values()) {
            lookup.put(button.glfwKeyCode, button);
        }
        return lookup;
    }
}

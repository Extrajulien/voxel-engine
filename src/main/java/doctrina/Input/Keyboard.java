package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.HashMap;
import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
    //linked list for fast modification
    private final LinkedList<Key> pressedKeys = new LinkedList<>();

    public Keyboard() {
        glfwSetKeyCallback(RenderingEngine.getWindow().getId(), keyCallback);
    }


    public boolean isDown(Key key) {
        return pressedKeys.contains(key);
    }

    public boolean isPressed(Key key) {
        if (key.isPressed()) {
            key.release();
            return true;
        }
        return false;
    }

    public boolean isReleased(Key key) {

        return true;
    }

    private final GLFWKeyCallbackI keyCallback = (window, key, scancode, action, mods) -> {
        Key keyboardKey= Key.fromInt(key);
        if (action == GLFW_PRESS) {
            keyboardKey.press();
            pressedKeys.add(keyboardKey);
            return;
        }
        if (action == GLFW_RELEASE) {
            keyboardKey.release();
            pressedKeys.remove(keyboardKey);
        }
    };
}

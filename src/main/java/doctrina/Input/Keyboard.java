package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
    private final ArrayList<Key> pressedKeys = new ArrayList<>();
    private final ArrayList<Key> downKeys = new ArrayList<>();
    private final ArrayList<Key> releasedKeys = new ArrayList<>();

    public Keyboard() {
        glfwSetKeyCallback(RenderingEngine.getWindow().getId(), keyCallback);
    }


    public boolean isDown(Key key) {
        return downKeys.contains(key);
    }

    public boolean isPressed(Key key) {
        return pressedKeys.contains(key);
    }

    public boolean isReleased(Key key) {
        return releasedKeys.contains(key);
    }

    public void update() {
        pressedKeys.clear();
        releasedKeys.clear();
    }



    private final GLFWKeyCallbackI keyCallback = (window, key, scancode, action, mods) -> {
        Key keyboardKey= Key.fromInt(key);
        if (keyboardKey == null) return;
        if (action == GLFW_PRESS) {
            pressedKeys.add(keyboardKey);
            downKeys.add(keyboardKey);
            return;
        }
        if (action == GLFW_RELEASE) {
            downKeys.remove(keyboardKey);
            releasedKeys.add(keyboardKey);
        }
    };
}

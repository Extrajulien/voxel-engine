package doctrina.Input;

import doctrina.rendering.RenderingEngine;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard implements Input{
    private final HashMap<Key, Boolean> keyMap = new HashMap<>();

    public Keyboard() {
        glfwSetKeyCallback(RenderingEngine.getWindow().getId(), keyCallback);
    }


    public boolean isDown(Key key) {
        boolean isKeyDown = glfwGetKey(RenderingEngine.getWindow().getId(), key.glfwKeyCode) == GLFW_PRESS;
        keyMap.put(key, isKeyDown);
        return isKeyDown;
    }

    public boolean isPressed(Key key) {
        if (keyMap.get(key)) {

        }
    }

    public boolean isReleased(Key key) {

    }

    private final GLFWKeyCallbackI keyCallback = (window, key, scancode, action, mods) -> {
        Key
        keyMap.put();
    };

    @Override
    public void update() {
        for (Key key : Key.values()) {

        }
    }
}

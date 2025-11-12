package doctrina.Input;

import doctrina.rendering.RenderingEngine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Keyboard {
    public boolean isPressed(Key key) {
        return glfwGetKey(RenderingEngine.getWindow(), key.glfwKeyCode) == GLFW_PRESS;
    }
}

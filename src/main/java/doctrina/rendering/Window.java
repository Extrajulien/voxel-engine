package doctrina.rendering;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long window;
    private final Vector2i size;
    private final Vector2i pos;


    public Window(Vector2i size, String name) {
        this.size = new Vector2i();
        this.pos = new Vector2i();
        this.window = glfwCreateWindow(size.x,  size.y, name, NULL, NULL);
        glfwMakeContextCurrent(window);
    }

    public void toggleFullscreen(boolean fullscreen) {
        if (fullscreen) {
            saveSize();
            savePosition();
            long monitor = glfwGetPrimaryMonitor();
            GLFWVidMode mode = glfwGetVideoMode(monitor);
            if (mode == null) {
                throw new NullPointerException("GLFWVidMode mode is null!");
            }
            glfwSetWindowMonitor(window, monitor, 0,0, mode.width(), mode.height(), mode.refreshRate());
            return;
        }
        glfwSetWindowMonitor(window, 0, pos.x, pos.y, size.x, size.y, 0);
    }

    public long getId() {
        return window;
    }

    private void saveSize() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, width, height);
        size.x = width.get(0);
        size.y = height.get(0);
    }

    private void savePosition() {
        IntBuffer posX = BufferUtils.createIntBuffer(1);
        IntBuffer posY = BufferUtils.createIntBuffer(1);
        glfwGetWindowPos(window, posX, posY);
        pos.x = posX.get(0);
        pos.y = posY.get(0);
    }


}

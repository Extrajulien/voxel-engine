package doctrina.rendering;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class RenderingEngine {
    private static long window;
    private static RenderingEngine renderingEngine;

    private RenderingEngine() {
        initializeRenderingEngine();
    }

    private void initializeRenderingEngine() {
        initializeGLFW();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        // On macOS, you must request forward compatibility
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }

        window = glfwCreateWindow(1920,  1080, "LWJGL Window", NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    public static long getWindow() {
        return window;
    }

    public boolean isWindowOpen() {
        return !glfwWindowShouldClose(window);
    }

    public void closeWindow() {
        glfwSetWindowShouldClose(window, true);
    }


    public void clearFrame() {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public void drawOnScreen() {
        glfwSwapBuffers(window);
    }

    public void stop() {
        glfwTerminate();
    }

    public static RenderingEngine getInstance() {
        if (renderingEngine == null) {
            renderingEngine = new RenderingEngine();
        }
        return renderingEngine;
    }




    private void initializeGLFW() throws IllegalStateException {
        if (!glfwInit()) throw new IllegalStateException("Failed to init GLFW");
    }
}

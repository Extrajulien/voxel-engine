package doctrina.rendering;

import org.joml.Vector2i;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class RenderingEngine {
    private static Window window;
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

        window = new Window(new Vector2i(1920,  1080), "Robert Cantaloupe");
        GL.createCapabilities();
        glfwSwapInterval(0);
        glEnable(GL_DEPTH_TEST);

        glfwSetFramebufferSizeCallback(window.getId(), (win, width, height) -> {
            glViewport(0, 0, width, height);
        });
    }

    public static Window getWindow() {
        return window;
    }

    public boolean isWindowOpen() {
        return !glfwWindowShouldClose(window.getId());
    }

    public void closeWindow() {
        glfwSetWindowShouldClose(window.getId(), true);
    }


    public void clearFrame() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.2f, 0.4f, 1.0f, 1.0f);
    }

    public void drawOnScreen() {
        glfwSwapBuffers(window.getId());
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

    public static float getAspectRatio() {
        return (float)window.getSize().x / (float)window.getSize().y;
    }



    private void initializeGLFW() throws IllegalStateException {
        if (!glfwInit()) throw new IllegalStateException("Failed to init GLFW");
    }
}

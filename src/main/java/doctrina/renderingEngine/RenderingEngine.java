package doctrina.renderingEngine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class RenderingEngine {
    private static long window;
    private final static RenderingEngine renderingEngine = new RenderingEngine();

    private RenderingEngine() {
        assertGlfwIsInit();
        window = glfwCreateWindow(800, 600, "LWJGL + Gradle", NULL, NULL);
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

    public void start () {
        assertGlfwIsInit();
    }

    public void clearFrame() {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

        glfwSwapBuffers(window);
    }

    public void stop() {
        glfwTerminate();
    }

    public static RenderingEngine getInstance() {
        return renderingEngine;
    }




    private void assertGlfwIsInit() throws IllegalStateException {
        if (!glfwInit()) throw new IllegalStateException("Failed to init GLFW");
    }
}

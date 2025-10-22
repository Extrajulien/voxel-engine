package demo_game;

import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class App {
    public static void main(String[] args) {
        if (!glfwInit()) throw new IllegalStateException("Failed to init GLFW");
        long window = glfwCreateWindow(800, 600, "LWJGL + Gradle", NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(0.1f, 0.8f, 0.5f, 1.0f);




            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glfwTerminate();
    }
}

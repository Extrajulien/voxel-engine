package doctrina;

import doctrina.renderingEngine.RenderingEngine;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public abstract class Game {
    private RenderingEngine renderingEngine;


    public abstract void initialize();

    public abstract void update();

    public abstract void draw();


    public final void start() {
        renderingEngine = RenderingEngine.getInstance();
        initialize();
        run();
        conclude();
    }

    public void stop() {
        renderingEngine.closeWindow();
    }

    public void conclude() {

    }

    private void run() {
        //GameTime time = new GameTime();
        while (renderingEngine.isWindowOpen()) {
            update();
            renderingEngine.clearFrame();
            draw();
            renderingEngine.drawOnScreen();
            //time.synchronize();
            glfwPollEvents();
        }
        renderingEngine.stop();
    }
}

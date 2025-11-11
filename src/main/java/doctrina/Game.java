package doctrina;

import doctrina.rendering.RenderingEngine;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public abstract class Game {
    private RenderingEngine renderingEngine;
    private GameTime time;


    public abstract void initialize();

    public abstract void update();

    public abstract void draw();

    public final double deltaTime() {
        return time.deltaTime();
    }

    public final void start() {
        renderingEngine = RenderingEngine.getInstance();
        time = new GameTime();
        time.setFpsTarget(120);
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
            time.synchronize();
            glfwPollEvents();
        }
        renderingEngine.stop();
    }
}

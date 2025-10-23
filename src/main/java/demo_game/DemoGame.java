package demo_game;

import doctrina.Game;
import doctrina.renderingEngine.RenderingEngine;

import static org.lwjgl.glfw.GLFW.*;

public class DemoGame extends Game {
    @Override
    public void initialize() {

    }

    @Override
    public void update() {
        if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            stop();
        }
    }

    @Override
    public void draw() {

    }
}

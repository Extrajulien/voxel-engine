package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import doctrina.Input.Controller;

public class Inventory {
    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void open(Controller<Action, Axis> controller) {
        isOpen = true;
        controller.freeCursor();
    }

    public void close(Controller<Action, Axis> controller) {
        isOpen = false;
        controller.captureCursor();
    }
}

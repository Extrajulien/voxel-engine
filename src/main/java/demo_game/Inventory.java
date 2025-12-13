package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.Analog;
import doctrina.Input.Controller;

public class Inventory {
    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void open(Controller<Action, Analog> controller) {
        isOpen = true;
        controller.freeCursor();
    }

    public void close(Controller<Action, Analog> controller) {
        isOpen = false;
        controller.captureCursor();
    }
}

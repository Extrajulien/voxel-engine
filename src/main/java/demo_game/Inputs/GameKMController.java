package demo_game.Inputs;

import doctrina.Input.*;

public class GameKMController extends KeyboardMouseController<Action, Axis> {

    public GameKMController(Keyboard keyboard, Mouse mouse) {
        super(keyboard, mouse);
    }

    @Override
    protected void setBindings() {
        addToMouseAxis(Axis.LOOK_X, MouseAxis.DELTA_X);
        addToMouseAxis(Axis.LOOK_Y, MouseAxis.DELTA_Y);
        addToKeyboardAction(Action.MOVE_NORTH, Key.W);
        addToKeyboardAction(Action.MOVE_SOUTH, Key.S);
        addToKeyboardAction(Action.MOVE_EAST, Key.D);
        addToKeyboardAction(Action.MOVE_WEST, Key.A);
        addToKeyboardAction(Action.TOGGLE_CAMERA_MODE, Key.F5);
        addToKeyboardAction(Action.SPRINT, Key.LEFT_CONTROL);
        addToKeyboardAction(Action.INVENTORY_TOGGLE, Key.E);
        addToKeyboardAction(Action.JUMP, Key.SPACE);
        addToKeyboardAction(Action.QUIT, Key.ESCAPE);
        addToKeyboardAction(Action.TOGGLE_FULLSCREEN, Key.F11);
    }

    @Override
    public void update() {
        keyboard.update();
        mouse.update();
    }
}

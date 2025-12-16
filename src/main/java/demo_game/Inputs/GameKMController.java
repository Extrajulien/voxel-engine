package demo_game.Inputs;

import doctrina.Input.*;

public class GameKMController extends KeyboardMouseController<Action, Analog> {

    public GameKMController(Keyboard keyboard, Mouse mouse) {
        super(keyboard, mouse);
    }

    @Override
    protected void setBindings() {
        addToMouseAxis(Analog.LOOK_X, MouseAxis.DELTA_X);
        addToMouseAxis(Analog.LOOK_Y, MouseAxis.DELTA_Y);
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
        addToKeyboardAction(Action.CROUCH, Key.LEFT_SHIFT);
        addToKeyboardAction(Action.TOGGLE_CHUNK_RENDERING_MODE, Key.F3);
        addToKeyboardAction(Action.CHUNK_POS_LOG, Key.F1);
        addToMouseAction(Action.PLACE_BLOCK, MouseButton.RIGHT);
        addToMouseAction(Action.BREAK_BLOCK, MouseButton.LEFT);
    }

    @Override
    public void update() {
        keyboard.update();
        mouse.update();
    }
}

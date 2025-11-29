package doctrina.Input;

import java.util.HashMap;

public abstract class KeyboardMouseController<A extends Enum<A>, X extends Enum<X>> extends Controller<A, X> {

    protected final Keyboard keyboard;
    protected final Mouse mouse;

    private final HashMap<A, Key> keyboardActionMap;
    private final HashMap<A, MouseButton> mouseActionMap;
    private final HashMap<X, MouseAxis> mouseAxisMap;

    public KeyboardMouseController(Keyboard keyboard, Mouse mouse) {

        keyboardActionMap = new HashMap<>();
        mouseActionMap = new HashMap<>();
        mouseAxisMap = new HashMap<>();

        this.keyboard = keyboard;
        this.mouse = mouse;

        setBindings();
    }

    @Override
    public final double getAxis(X axis) {
        if (mouseAxisMap.get(axis) == null) {
            System.err.println("MouseAxisMap: '" + axis.name() + "' axis is not in the map");
            System.exit(1);
        }
        return mouse.getAxis(mouseAxisMap.get(axis));
    }

    @Override
    public final boolean isPressed(A action) {
        if (mouseActionMap.get(action) != null) {
            return mouse.isPressed(mouseActionMap.get(action));
        }
        if (keyboardActionMap.get(action) != null) {
            return keyboard.isPressed(keyboardActionMap.get(action));
        }
        System.err.println("KeyboardMouseController isPressed(): '" + action.name() + "' action is not in the maps");
        System.exit(1);
        return false;
    }

    @Override
    public final boolean isDown(A action) {
        if (mouseActionMap.get(action) != null) {
            return mouse.isDown(mouseActionMap.get(action));
        }
        if (keyboardActionMap.get(action) != null) {
            return keyboard.isDown(keyboardActionMap.get(action));
        }
        System.err.println("KeyboardMouseController isPressed(): '" + action.name() + "' action is not in the maps");
        System.exit(1);
        return false;
    }

    @Override
    public final boolean isReleased(A action) {
        if (mouseActionMap.get(action) != null) {
            return mouse.isReleased(mouseActionMap.get(action));
        }
        if (keyboardActionMap.get(action) != null) {
            return keyboard.isReleased(keyboardActionMap.get(action));
        }
        System.err.println("KeyboardMouseController isPressed(): '" + action.name() + "' action is not in the maps");
        System.exit(1);
        return false;
    }

    public final void captureCursor() {
        mouse.captureCursor();
    }

    public final void freeCursor() {
        mouse.freeCursor();
    }

    protected abstract void setBindings();

    protected void addToKeyboardAction(A action, Key key) {
        keyboardActionMap.put(action, key);
    }

    protected void addToMouseAction(A action, MouseButton button) {
        mouseActionMap.put(action, button);
    }

    protected void addToMouseAxis(X axis, MouseAxis mouseAxis) {
        mouseAxisMap.put(axis, mouseAxis);
    }

}
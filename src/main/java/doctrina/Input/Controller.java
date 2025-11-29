package doctrina.Input;

public abstract class Controller<A extends Enum<A>, X extends Enum<X>> {

    public abstract double getAxis(X axis);
    public abstract boolean isPressed(A action);
    public abstract boolean isReleased(A action);
    public abstract boolean isDown(A action);
    public abstract void captureCursor();
    public abstract void freeCursor();
    public abstract void update();
}
package doctrina.physic;

public enum Axis {
    X,
    Y,
    Z;

    public int getValue() {
        return ordinal();
    }
}

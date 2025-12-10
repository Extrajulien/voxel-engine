package doctrina.Entities;

public class EntityMovementFlag {
    private int flags;

    public EntityMovementFlag() {
        flags = 0;
    }

    public EntityMovementFlag(EntityMovementFlag other) {
        this.flags = other.flags;
    }

    public void disableDirection(MovementDir dir) {
        flags &= ~(1 << dir.ordinal());
    }

    public void enableDirection(MovementDir dir) {
        flags |= 1 << dir.ordinal();
    }

    public void enableAll() {
        flags = ~0;
    }

    public boolean canMoveInDirection(MovementDir dir) {
        return (flags & (1 << dir.ordinal())) != 0;
    }

    public enum MovementDir {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        UP,
        IS_GROUNDED
    }
}

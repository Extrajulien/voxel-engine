package doctrina.physic;

import org.joml.Vector3i;

public enum MovementDir {
    NORTH(0,0,-1),
    SOUTH(0,0,1),
    EAST(1,0,0),
    WEST(-1,0,0),
    UP(0,1,0),
    DOWN(0,-1,0);

    public final int x;
    public final int y;
    public final int z;
    public final int sign;

    MovementDir(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.sign = x + y + z;
    }

    public Vector3i getUnitDirection() {
        return new Vector3i(x, y, z);
    }

    public boolean isAxisPositive() {
        return getAxisValue() > 0;
    }

    public Axis getAxis() {
        return switch (this) {
            case EAST, WEST -> Axis.X;
            case UP, DOWN -> Axis.Y;
            case NORTH, SOUTH -> Axis.Z;
        };
    }


    public int getAxisValue() {
        return switch (this) {
            case EAST, WEST -> x;
            case UP, DOWN -> y;
            case NORTH, SOUTH -> z;
        };
    }
}

package doctrina.physic;

import org.joml.Vector3i;

public enum MovementDir {
    NORTH(0,0,-1),
    SOUTH(0,0,1),
    EAST(1,0,0),
    WEST(-1,0,0),
    UP(0,1,0),
    DOWN(0,-1,0);

    private final Vector3i unitDirection;

    MovementDir(int x, int y, int z) {
        unitDirection = new Vector3i(x,y,z);
    }

    public Vector3i getUnitDirection() {
        return new Vector3i(unitDirection);
    }

    public boolean isAxisPositive() {
        return unitDirection.get(getAxis().getValue()) > 0;
    }


    public Axis getAxis() {
        return switch (this) {
            case EAST, WEST -> Axis.X;
            case UP, DOWN -> Axis.Y;
            case NORTH, SOUTH -> Axis.Z;
        };
    }
}

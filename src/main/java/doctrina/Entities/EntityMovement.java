package doctrina.Entities;

import doctrina.Utils.BitMask;

public class EntityMovement extends BitMask<EntityMovement.MovementDir> {

    public enum MovementDir {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        UP,
        DOWN
    }
}

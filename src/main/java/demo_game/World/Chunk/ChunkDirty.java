package demo_game.World.Chunk;

import demo_game.World.Direction;

public final class ChunkDirty {
    public static final int NONE   = 0;
    public static final int NORTH  = 1;
    public static final int SOUTH  = 1 << 1;
    public static final int EAST   = 1 << 2;
    public static final int WEST   = 1 << 3;
    public static final int TOP    = 1 << 4;
    public static final int BOTTOM = 1 << 5;
    public static final int SELF = NORTH|SOUTH|EAST|WEST|TOP|BOTTOM;

    public static int getDirtySide(Direction dir) {
        return switch (dir) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            case TOP -> TOP;
            case BOTTOM -> BOTTOM;
            default -> NONE;
        };
    }

    public static boolean hasFlag(int mask, Direction dir) {
        return (mask & getDirtySide(dir)) == getDirtySide(dir);
    }
}

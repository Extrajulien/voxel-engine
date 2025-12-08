package demo_game.World.Chunk;

import demo_game.World.Direction;

public record NeighboringChunks(Chunk top, Chunk bottom, Chunk north, Chunk south, Chunk east, Chunk west) {

    public boolean isInitialized(Direction dir) {
        return get(dir) != null;
    }


    public Chunk get(Direction dir) {
        return switch (dir) {
            case TOP -> top;
            case BOTTOM -> bottom;
            case NORTH -> north;
            case SOUTH -> south;
            case EAST -> east;
            case WEST -> west;
        };
    }
}

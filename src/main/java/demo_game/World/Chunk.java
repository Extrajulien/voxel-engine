package demo_game.World;

import demo_game.BlockType;
import org.joml.RoundingMode;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Chunk {
    private final static int SIZE = 32;
    private final BlockType[][][] blocks = new BlockType[SIZE][SIZE][SIZE];
    private final Vector3i worldPos; // world pos is the x,y,z corner

    public Chunk(ChunkPos worldPosition) {
        worldPos = new Vector3i(worldPosition.x(), worldPosition.y(), worldPosition.z());
        worldPos.mul(SIZE, SIZE, SIZE);
    }

    public int getMinY() {
        return worldPos.y;
    }

    public int getMaxY() {
        return worldPos.y + SIZE - 1;
    }

    public static Vector3i positionToChunkCoordinate(Vector3f worldPosition) {
        return new Vector3i(worldPosition, RoundingMode.TRUNCATE).div(SIZE);
    }
}

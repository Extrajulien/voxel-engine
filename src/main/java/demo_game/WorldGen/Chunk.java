package demo_game.WorldGen;

import demo_game.BlockType;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range3d;
import org.joml.RoundingMode;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Chunk {
    private final static int SIZE = 16;
    private final BlockType[][][] blocks = new BlockType[SIZE][SIZE][SIZE];
    private final Vector3i worldPos; // world pos is the x,y,z corner

    public Chunk(ChunkPos worldPosition) {
        worldPos = new Vector3i(worldPosition.x(), worldPosition.y(), worldPosition.z());
    }

    public int getMinY() {
        return worldPos.y * SIZE;
    }

    public int getMaxY() {
        return worldPos.y * SIZE + SIZE - 1;
    }

    public static Vector3i positionToChunkCoordinate(Vector3f worldPosition) {
        return new Vector3i(worldPosition, RoundingMode.TRUNCATE).div(SIZE);
    }

    /**
     * returns the position of the chunk in the chunk coordinate system
     */
    public Vector3i getChunkPos() {
        return worldPos;
    }

    public Range3d getBlocksRange() {
        return new Range3d(new Range1d(0, SIZE - 1), new Range1d(0, SIZE - 1),
                new Range1d(0, SIZE - 1));
    }
    public void fill(BlockType fillerBlock) {
        for (Vector3i pos : getBlocksRange()) {
            blocks[pos.x][pos.y][pos.z] = fillerBlock;
        }
    }

    public BlockType[][][] getBlocks() {
        return blocks;
    }

    public BlockType getBlock(Vector3i vec) {
        return getBlock(vec.x, vec.y, vec.z);
    }

    public BlockType getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }


}

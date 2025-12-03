package demo_game.WorldGen;

import demo_game.BlockType;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range2d;
import doctrina.Utils.Range3d;
import org.joml.RoundingMode;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Chunk {
    private final static int SIZE = 16;
    private final BlockType[][][] blocks;
    private final Vector3i worldPos; // world pos is the x,y,z corner
    private long solidBlocks;

    public Chunk(ChunkPos worldPosition) {
        worldPos = new Vector3i(worldPosition.x(), worldPosition.y(), worldPosition.z());
        blocks = new BlockType[SIZE][SIZE][SIZE];
        solidBlocks = 0;
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
            if (fillerBlock.isSolid()) {
                ++solidBlocks;
            }
        }
    }

    public boolean isEmpty() {
        return solidBlocks == 0;
    }

    public BlockType getBlockType(Vector3i vec) {
        return getBlockType(vec.x, vec.y, vec.z);
    }

    public BlockType getBlockType(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public void setBlockType(int x, int y, int z, BlockType blockType) {
        if (!blocks[x][y][z].isSolid() && blockType.isSolid()) {
            ++solidBlocks;
        }

        if (blocks[x][y][z].isSolid() && !blockType.isSolid()) {
            --solidBlocks;
        }

        blocks[x][y][z] = blockType;
    }

    public void setBlockType(Vector3i pos, BlockType blockType) {
        setBlockType(pos.x, pos.y, pos.z, blockType);
    }

    public Range2d getXZBlocksRange() {
        return new Range2d(
                new Range1d((long) worldPos.x * SIZE, (long) worldPos.x * SIZE + SIZE - 1),
                new Range1d((long) worldPos.y * SIZE, (long) worldPos.y * SIZE + SIZE - 1)
        );
    }


}

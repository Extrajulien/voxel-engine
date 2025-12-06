package demo_game.WorldGen;

import demo_game.BlockType;
import demo_game.Player.Player;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range2d;
import doctrina.Utils.Range3d;
import org.joml.RoundingMode;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Chunk {
    private final static int SIZE_POWER_OF_2 = 4;
    public final static int SIZE = (int) Math.pow(2, SIZE_POWER_OF_2);

    private final BlockType[][][] blocks;
    private final ChunkBoundingBox boundingBox;


    private final Vector3i worldPos; // world pos is the x,y,z corner
    private final Range3d worldSpaceRange;
    private long solidBlocks;

    public Chunk(ChunkPos worldPosition) {
        worldPos = new Vector3i(worldPosition.x(), worldPosition.y(), worldPosition.z());
        blocks = new BlockType[SIZE][SIZE][SIZE];
        solidBlocks = 0;
        worldSpaceRange = createWorldSpaceRange();
        boundingBox = new ChunkBoundingBox(worldPos);
        fill(BlockType.AIR);
    }

    public void drawBounds(Player player) {
        boundingBox.draw(player);
    }

    public int getWorldSpaceMinY() {
        return worldPos.y * SIZE;
    }

    public int getWorldSpaceMaxY() {
        return worldPos.y * SIZE + SIZE - 1;
    }

    public Range1d getWorldSpaceYRange() {
        return worldSpaceRange.rangeY();
    }

    public static Vector3i positionToChunk(Vector3f worldPosition) {
        Vector3i pos = new Vector3i(worldPosition, RoundingMode.FLOOR);

        pos.x = Math.floorDiv(pos.x, SIZE);
        pos.y = Math.floorDiv(pos.y, SIZE);
        pos.z = Math.floorDiv(pos.z, SIZE);

        return pos;
    }

    /**
     * returns the position of the chunk in Chunk space
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
            if (!fillerBlock.isTransparent()) {
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
        if (blocks[x][y][z].isTransparent() && !blockType.isTransparent()) {
            ++solidBlocks;
        }

        if (!blocks[x][y][z].isTransparent() && blockType.isTransparent()) {
            --solidBlocks;
        }

        blocks[x][y][z] = blockType;
    }

    public void setBlockType(Vector3i pos, BlockType blockType) {
        setBlockType(pos.x, pos.y, pos.z, blockType);
    }

    public Range2d getWorldXZBlocksRange() {
        return new Range2d(
                new Range1d((long) worldPos.x * SIZE, (long) worldPos.x * SIZE + SIZE -1),
                new Range1d((long) worldPos.z * SIZE, (long) worldPos.z * SIZE + SIZE -1)
        );
    }


    public static int posWorldWrapToChunk(int number) {
        return number & SIZE-1;
    }


    private Range3d createWorldSpaceRange() {
        return new Range3d(
            new Range1d((long) worldPos.x * SIZE, (long) worldPos.x * SIZE + SIZE - 1),
            new Range1d((long) worldPos.y * SIZE, (long) worldPos.y * SIZE + SIZE - 1),
            new Range1d((long) worldPos.z * SIZE, (long) worldPos.z * SIZE + SIZE - 1)
        );
    }


}

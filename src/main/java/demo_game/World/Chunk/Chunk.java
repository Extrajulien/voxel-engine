package demo_game.World.Chunk;

import demo_game.BlockType;
import demo_game.Player.Player;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range2d;
import doctrina.Utils.Range3d;
import org.joml.RoundingMode;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Chunk {
    private final static int SIZE_POWER_OF_2 = 2;
    public final static int SIZE = (int) Math.pow(2, SIZE_POWER_OF_2);

    private final BlockType[][][] blocks;
    private final ChunkBoundingBox boundingBox;
    private final ChunkMesh mesh;
    private final ChunkPos chunkSpacePos; // world pos is the x,y,z corner
    private final Range3d worldSpaceRange;
    private int dirtyMask;
    private long solidBlocks;

    public Chunk(ChunkPos chunkSpacePos, ChunkRegister register) {
        register.addChunk(chunkSpacePos, this);
        this.chunkSpacePos = chunkSpacePos;
        blocks = new BlockType[SIZE][SIZE][SIZE];
        solidBlocks = 0;
        worldSpaceRange = createWorldSpaceRange();
        boundingBox = new ChunkBoundingBox(this.chunkSpacePos);
        mesh = new ChunkMesh(this);
    }

    public void drawBounds(Player player) {
        boundingBox.draw(player);
    }

    public void drawWireframe(Player player) {
        if (!isEmpty()) {
            mesh.drawWireframe(player);
        }
    }

    public void drawHighlighted(Player player) {
        boundingBox.setColor(ChunkDebuggingConstants.CHUNK_HIGHLIGHT_COLOR.getValue());
        boundingBox.draw(player);
    }

    public void draw(Player player) {
        if (!isEmpty()) {
            mesh.draw(player);
        }
    }

    public void markDirty(int flag) {
        this.dirtyMask |= flag;
    }

    public void updateMesh(ChunkRegister register) {
        if (dirtyMask != ChunkDirty.NONE) {
            mesh.update(dirtyMask, register);
            dirtyMask = ChunkDirty.NONE;
        }
    }

    public int getWorldSpaceMinY() {
        return chunkSpacePos.y() * SIZE;
    }

    public int getWorldSpaceMaxY() {
        return chunkSpacePos.y() * SIZE + SIZE - 1;
    }

    public Range1d getWorldSpaceYRange() {
        return worldSpaceRange.rangeY();
    }

    public static Vector3i worldToChunkSpace(Vector3f worldPosition) {
        Vector3i pos = new Vector3i(worldPosition, RoundingMode.FLOOR);

        pos.x = Math.floorDiv(pos.x, SIZE);
        pos.y = Math.floorDiv(pos.y, SIZE);
        pos.z = Math.floorDiv(pos.z, SIZE);

        return pos;
    }

    public static Vector3i chunkToWorldSpace(ChunkPos chunkPosition) {
        return new Vector3i(chunkPosition.x() * SIZE, chunkPosition.y() * SIZE, chunkPosition.z() * SIZE);
    }

    /**
     * returns the position of the chunk in Chunk space
     */
    public ChunkPos getChunkPos() {
        return chunkSpacePos;
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
        BlockType bT = blocks[x][y][z];
        return bT == null ? BlockType.AIR : bT;
    }

    public void setBlockType(int x, int y, int z, BlockType blockType) {
        if (getBlockType(x, y, z) != blockType) {
            markDirty(ChunkDirty.SELF);
        }


        if (getBlockType(x,y,z).isTransparent() && !blockType.isTransparent()) {
            ++solidBlocks;
        }

        if (!getBlockType(x,y,z).isTransparent() && blockType.isTransparent()) {
            --solidBlocks;
        }

        blocks[x][y][z] = blockType;
    }

    public void setBlockType(Vector3i pos, BlockType blockType) {
        setBlockType(pos.x, pos.y, pos.z, blockType);
    }

    public Range2d getWorldXZBlocksRange() {
        return new Range2d(
                new Range1d((long) chunkSpacePos.x() * SIZE, (long) chunkSpacePos.x() * SIZE + SIZE - 1),
                new Range1d((long) chunkSpacePos.z() * SIZE, (long) chunkSpacePos.z() * SIZE + SIZE - 1)
        );
    }


    public static int posWorldWrapToChunk(int number) {
        return number & SIZE-1;
    }


    private Range3d createWorldSpaceRange() {
        return new Range3d(
            new Range1d((long) chunkSpacePos.x() * SIZE, (long) chunkSpacePos.x() * SIZE + SIZE - 1),
            new Range1d((long) chunkSpacePos.y() * SIZE, (long) chunkSpacePos.y() * SIZE + SIZE - 1),
            new Range1d((long) chunkSpacePos.z() * SIZE, (long) chunkSpacePos.z() * SIZE + SIZE - 1)
        );
    }


}

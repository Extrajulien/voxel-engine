package demo_game.WorldGen;

import demo_game.BlockType;
import doctrina.Utils.Range2d;
import org.joml.Vector3i;

public class TerrainGenerator {
    long seed;
    public TerrainGenerator(long seed) {
        this.seed = seed;
    }


    public void CreateChunk(Chunk chunk) {
        Range2d range = chunk.getWorldXZBlocksRange();


        if (chunk.getChunkPos().equals(0,-1,0) || chunk.getChunkPos().equals(0,0,0) || chunk.getChunkPos().equals(0,-1,2)) {
            chunk.fill(BlockType.DIRT);
            chunk.setBlockType(10, 15, 10, BlockType.AIR);
            chunk.setBlockType(10, 0, 10, BlockType.AIR);
        } else {
            chunk.setBlockType(0,0,0, BlockType.DIRT);
        }

    }

    private boolean isChunkIntersectingSurface(Chunk chunk) {
        return true;
    }

}

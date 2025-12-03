package demo_game.WorldGen;

import demo_game.BlockType;
import doctrina.Utils.Range2d;

public class TerrainGenerator {
    long seed;
    public TerrainGenerator(long seed) {
        this.seed = seed;
    }


    public void CreateChunk(Chunk chunk) {
        Range2d range = chunk.getXZBlocksRange();


        if (chunk.getChunkPos().equals(0,-1,0)) {
            chunk.fill(BlockType.DIRT);
        }

    }

    private boolean isChunkIntersectingSurface(Chunk chunk) {
        return true;
    }

}

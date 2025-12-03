package demo_game.WorldGen;

import demo_game.BlockType;

public class TerrainGenerator {
    long seed;
    public TerrainGenerator(long seed) {
        this.seed = seed;
    }


    public void CreateChunk(Chunk chunk) {
        if (chunk.getChunkPos().equals(0,-1,0)) {
            chunk.fill(BlockType.DIRT);
        }

    }

    private boolean isChunkIntersectingSurface(Chunk chunk) {
        return true;
    }

}

package demo_game.WorldGen;

import demo_game.BlockType;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range2d;
import org.joml.SimplexNoise;
import org.joml.Vector2i;

import java.util.HashMap;

public class TerrainGenerator {
    private long seed;
    private static final Range1d NOISE_RANGE = new Range1d(-5,5);

    public TerrainGenerator(long seed) {
        this.seed = seed;
    }
    private final static HashMap<ChunkPos, Range1d> noiseHeightPerChunk = new HashMap<>();


    public void CreateChunk(Chunk chunk) {

        if (!isChunkInNoiseRange(chunk)) {
            if (isChunkUnderNoiseRange(chunk)) {
                chunk.fill(BlockType.DIRT);
            }
            return;
        }

        ChunkPos pos = new ChunkPos(chunk.getChunkPos().x, 0, chunk.getChunkPos().z);
        if (!noiseHeightPerChunk.containsKey(pos)) {

            Noise2d noise = getNoiseFromRange(chunk.getWorldXZBlocksRange());
            noiseHeightPerChunk.put(pos, noise.getValuesRange());
        }


        if (noiseHeightPerChunk.get(pos).isIntersectingRange(new Range1d(chunk.getWorldSpaceMinY(), chunk.getWorldSpaceMaxY()))) {

        }


    }

    private boolean isChunkIntersectingSurface(Chunk chunk) {
        return true;
    }

    private Noise2d getNoiseFromRange(Range2d range) {
        int[][] noise = new int[Chunk.SIZE][Chunk.SIZE];
        for (Vector2i vec : range) {
            float fraction = SimplexNoise.noise(vec.x * seed, vec.y * seed);
            noise[Chunk.posWorldWrapToChunk(vec.x)][Chunk.posWorldWrapToChunk(vec.y)] = (int) (NOISE_RANGE.getLowerThreshold() + fraction * (NOISE_RANGE.getHigherThreshold() - NOISE_RANGE.getLowerThreshold()));
        }

        return new Noise2d(noise);
    }


    private boolean isChunkInNoiseRange(Chunk chunk) {
        Range1d chunkYRange = new Range1d(chunk.getWorldSpaceMinY(), chunk.getWorldSpaceMaxY());
        return NOISE_RANGE.isIntersectingRange(chunkYRange);
    }

    private boolean isChunkUnderNoiseRange(Chunk chunk) {
        Range1d chunkYRange = new Range1d(chunk.getWorldSpaceMinY(), chunk.getWorldSpaceMaxY());
        return NOISE_RANGE.isGreater(chunkYRange);
    }

}

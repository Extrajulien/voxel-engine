package demo_game.World.Generation;

import demo_game.BlockType;
import demo_game.World.Chunk.Chunk;
import demo_game.World.Chunk.ChunkPos;
import doctrina.Utils.RangeI1d;
import doctrina.Utils.RangeI2d;
import org.joml.SimplexNoise;
import org.joml.Vector2i;

import java.util.HashMap;

public class TerrainGenerator {
    private static final float RandomStrength = 10;
    private long seed;
    private static final RangeI1d NOISE_RANGE = new RangeI1d(-5, 5);

    public TerrainGenerator(long seed) {
        if (seed == 0) {
            seed = 1;
        }
        this.seed = seed;
    }
    private final static HashMap<ChunkPos, Noise2d> noisePerChunk = new HashMap<>();


    public void CreateChunk(Chunk chunk) {

        ChunkPos pos = new ChunkPos(chunk.getChunkPos().x(), 0, chunk.getChunkPos().z());
        if (!noisePerChunk.containsKey(pos)) {
            createNoise(chunk, pos);
        }

        if (isChunkIntersectingNoise(chunk, pos)) {
            Noise2d noise = noisePerChunk.get(pos);
            for (Vector2i vec2 : noise.getEntryRange()) {
                if (chunk.getWorldSpaceYRange().isNumberInRange(noise.getValues()[vec2.x][vec2.y])) {
                    int y = Chunk.posWorldWrapToChunk(noise.getValues()[vec2.x][vec2.y]);
                    chunk.setBlockType(vec2.x, y, vec2.y, BlockType.DIRT);
                }
            }
        }
    }

    private boolean isChunkIntersectingSurface(Chunk chunk) {
        return true;
    }

    private void createNoise(Chunk chunk, ChunkPos pos) {
        Noise2d noise = createNoiseFromRange(chunk.getWorldXZBlocksRange());
        noisePerChunk.put(pos, noise);
    }

    private boolean isChunkIntersectingNoise(Chunk chunk, ChunkPos pos) {
        return noisePerChunk.get(pos).getValuesRange().isIntersectingRange(chunk.getWorldSpaceYRange());
    }

    private Noise2d createNoiseFromRange(RangeI2d range) {

        int[][] noise = new int[Chunk.SIZE][Chunk.SIZE];
        for (Vector2i vec : range) {
            float fraction = SimplexNoise.noise(vec.x * seed * (RandomStrength / 1000), (vec.y * seed * RandomStrength / 1000));
            noise[Chunk.posWorldWrapToChunk(vec.x)][Chunk.posWorldWrapToChunk(vec.y)] = (int) (NOISE_RANGE.getLowerThreshold() + fraction * (NOISE_RANGE.getHigherThreshold() - NOISE_RANGE.getLowerThreshold()));
        }
        return new Noise2d(noise);
    }


    private boolean isChunkInNoiseRange(Chunk chunk) {
        RangeI1d chunkYRange = new RangeI1d(chunk.getWorldSpaceMinY(), chunk.getWorldSpaceMaxY());
        return NOISE_RANGE.isIntersectingRange(chunkYRange);
    }

    private boolean isChunkUnderNoiseRange(Chunk chunk) {
        RangeI1d chunkYRange = chunk.getWorldSpaceYRange();
        return NOISE_RANGE.isGreater(chunkYRange);
    }

}

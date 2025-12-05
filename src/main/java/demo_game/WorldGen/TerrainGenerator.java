package demo_game.WorldGen;

import demo_game.BlockType;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range2d;
import org.joml.SimplexNoise;
import org.joml.Vector2i;

import java.util.HashMap;

public class TerrainGenerator {
    private static final float RandomStrength = 15;
    private long seed;
    private static final Range1d NOISE_RANGE = new Range1d(-5,5);

    public TerrainGenerator(long seed) {
        if (seed == 0) {
            seed = 1;
        }
        this.seed = seed;
    }
    private final static HashMap<ChunkPos, Range1d> noiseHeightPerChunk = new HashMap<>();


    public void CreateChunk(Chunk chunk) {





        ChunkPos pos = new ChunkPos(chunk.getChunkPos().x, 0, chunk.getChunkPos().z);
        if (!noiseHeightPerChunk.containsKey(pos)) {

            Noise2d noise = getNoiseFromRange(chunk.getWorldXZBlocksRange());
            noiseHeightPerChunk.put(pos, noise.getValuesRange());
        }




        if (noiseHeightPerChunk.get(pos).isIntersectingRange(chunk.getWorldSpaceYRange())) {
            Noise2d noise = getNoiseFromRange(chunk.getWorldXZBlocksRange());

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

    private Noise2d getNoiseFromRange(Range2d range) {

        int[][] noise = new int[Chunk.SIZE][Chunk.SIZE];
        for (Vector2i vec : range) {
            float fraction = SimplexNoise.noise(vec.x * seed * (RandomStrength / 1000), (vec.y * seed * RandomStrength / 1000));
            noise[Chunk.posWorldWrapToChunk(vec.x)][Chunk.posWorldWrapToChunk(vec.y)] = (int) (NOISE_RANGE.getLowerThreshold() + fraction * (NOISE_RANGE.getHigherThreshold() - NOISE_RANGE.getLowerThreshold()));
        }

        return new Noise2d(noise);
    }


    private boolean isChunkInNoiseRange(Chunk chunk) {
        Range1d chunkYRange = new Range1d(chunk.getWorldSpaceMinY(), chunk.getWorldSpaceMaxY());
        return NOISE_RANGE.isIntersectingRange(chunkYRange);
    }

    private boolean isChunkUnderNoiseRange(Chunk chunk) {
        Range1d chunkYRange = chunk.getWorldSpaceYRange();
        return NOISE_RANGE.isGreater(chunkYRange);
    }

}

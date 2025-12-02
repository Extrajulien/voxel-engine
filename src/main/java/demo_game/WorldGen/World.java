package demo_game.WorldGen;

import demo_game.Player.Player;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range3d;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;

public class World {
    Map<ChunkPos, Chunk> chunks;
    TerrainGenerator terrainGenerator;

    public World(long seed) {
        chunks = new HashMap<>();
        terrainGenerator = new TerrainGenerator(seed);
    }
    public void loadChunks(Player player) {
        CreateChunksNearPlayer(player);
    }

    private void CreateChunksNearPlayer(Player player) {
        for (Vector3i pos : player.getChunkLoadingRange()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            if (!chunks.containsKey(chunkPos)) {
                createChunk(chunkPos);
            }
        }
    }

    private void createChunk(ChunkPos chunkPos) {
        Chunk chunk = new Chunk(chunkPos);
        terrainGenerator.loadChunk(chunk);
        chunks.put(chunkPos, chunk);
    }
}

package demo_game.World;

import demo_game.Player.Player;
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

        Vector3i playerChunk = getPlayerChunkPos(player);

        for (int x = -player.getChunkLoadingRadius() + playerChunk.x;
             x <= player.getChunkLoadingRadius() + playerChunk.x; x++) {

            for (int y = -player.getChunkLoadingRadius() + playerChunk.y;
                 y <= player.getChunkLoadingRadius() + playerChunk.y; y++) {

                for (int z = -player.getChunkLoadingRadius() + playerChunk.z;
                     z <= player.getChunkLoadingRadius() + playerChunk.z; z++) {

                    ChunkPos chunkPos = new ChunkPos(x, y, z);
                    if (!chunks.containsKey(chunkPos)) {
                        createChunk(chunkPos);
                    }
                }
            }
        }
    }

    private void createChunk(ChunkPos chunkPos) {
        Chunk chunk = new Chunk(chunkPos);
        terrainGenerator.loadChunk(chunk);
        chunks.put(chunkPos, chunk);
    }

    private Vector3i getPlayerChunkPos(Player player) {
        return Chunk.positionToChunkCoordinate(player.getPosition());
    }
}

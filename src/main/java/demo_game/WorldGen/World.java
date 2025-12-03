package demo_game.WorldGen;

import demo_game.Player.Player;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class World {
    Map<ChunkPos, Chunk> chunks;
    Map<ChunkPos, ChunkMesh> chunkMeshes;
    TerrainGenerator terrainGenerator;
    Vector3i playerChunk;

    public World(long seed, Player player) {
        chunks = new HashMap<>();
        chunkMeshes = new HashMap<>();
        terrainGenerator = new TerrainGenerator(seed);
        playerChunk = new Vector3i(Chunk.positionToChunkCoordinate(player.getPosition()));
    }
    public void loadChunks(Player player) {
        if (playerChunk != Chunk.positionToChunkCoordinate(player.getPosition())) {
            playerChunk = new Vector3i(Chunk.positionToChunkCoordinate(player.getPosition()));
            CreateChunksNearPlayer(player);
        }
    }

    public void draw(Player player) {
        for (ChunkMesh chunk : chunkMeshes.values()) {
            chunk.draw(player);
        }
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
        terrainGenerator.CreateChunk(chunk);
        chunks.put(chunkPos, chunk);
        updateChunkMesh(chunkPos);
    }

    private void updateChunkMesh(ChunkPos chunkPos) {

        ChunkMesh mesh = new ChunkMesh(chunks.get(chunkPos), findNeighboringChunks(chunkPos));
        chunkMeshes.put(chunkPos, mesh);
    }



    private NeighboringChunks findNeighboringChunks(ChunkPos pos) {
        ChunkPos top    = new ChunkPos(pos.x(), pos.y() + 1, pos.z());
        ChunkPos bottom = new ChunkPos(pos.x(), pos.y() - 1, pos.z());
        ChunkPos north  = new ChunkPos(pos.x(), pos.y(), pos.z() - 1);
        ChunkPos south  = new ChunkPos(pos.x(), pos.y(), pos.z() + 1);
        ChunkPos east   = new ChunkPos(pos.x() + 1, pos.y(), pos.z());
        ChunkPos west   = new ChunkPos(pos.x() - 1, pos.y(), pos.z());


        return new NeighboringChunks(chunks.get(top), chunks.get(bottom), chunks.get(north),
                chunks.get(south), chunks.get(east), chunks.get(west));
    }
}

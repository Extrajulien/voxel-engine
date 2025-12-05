package demo_game.WorldGen;

import demo_game.Player.Player;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;

public class World {
    private boolean isBoundingBoxShown = false;
    private Map<ChunkPos, Chunk> chunks;
    private Map<ChunkPos, ChunkMesh> chunkMeshes;
    private TerrainGenerator terrainGenerator;
    private Vector3i playerChunk;

    public World(long seed, Player player) {
        chunks = new HashMap<>();
        chunkMeshes = new HashMap<>();
        terrainGenerator = new TerrainGenerator(seed);
        playerChunk = new Vector3i(Chunk.positionToChunk(player.getPosition()));
        CreateChunksNearPlayer(player);
    }

    public void loadChunks(Player player) {
        if (!playerChunk.equals(Chunk.positionToChunk(player.getPosition()))) {
            playerChunk.set(Chunk.positionToChunk(player.getPosition()));
            CreateChunksNearPlayer(player);
        }
    }

    public void draw(Player player) {
        for (ChunkMesh chunk : chunkMeshes.values()) {
            chunk.draw(player);
        }
        if (isBoundingBoxShown) {
            drawAllChunkBounds(player);
        }
    }

    public void setBoundingBoxShown(boolean boundingBoxShown) {
        isBoundingBoxShown = boundingBoxShown;
    }

    public boolean isBoundingBoxShown() {
        return isBoundingBoxShown;
    }

    public void drawChunkBounds(ChunkPos pos, Player player) {
        if (!chunks.containsKey(pos)) {
            System.out.println("Chunk " + pos + " does not exist");
            return;
        }
        chunks.get(pos).drawBounds(player);
    }

    private void drawAllChunkBounds(Player player) {
        for (Chunk chunk : chunks.values()) {
            chunk.drawBounds(player);
        }
    }

    private void CreateChunksNearPlayer(Player player) {
        for (Vector3i pos : player.getChunkLoadingRange()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            if (!chunks.containsKey(chunkPos)) {
                createChunk(chunkPos);
            }
        }
        for (Vector3i pos : player.getChunkLoadingRange()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            createChunkMesh(chunkPos);
        }
    }

    private void createChunk(ChunkPos chunkPos) {
        Chunk chunk = new Chunk(chunkPos);
        terrainGenerator.CreateChunk(chunk);
        chunks.put(chunkPos, chunk);
    }

    private void createChunkMesh(ChunkPos chunkPos) {
        if (chunks.get(chunkPos).isEmpty()) {
            return;
        }
        if (!chunkMeshes.containsKey(chunkPos)) {
            ChunkMesh mesh = new ChunkMesh(chunks.get(chunkPos), findNeighboringChunks(chunkPos));
            chunkMeshes.put(chunkPos, mesh);
        }
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

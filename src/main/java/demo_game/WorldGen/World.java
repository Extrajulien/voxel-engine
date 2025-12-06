package demo_game.WorldGen;

import demo_game.Player.Player;
import demo_game.WorldGen.Chunk.*;
import org.joml.Vector3i;

public class World {
    private boolean isBoundingBoxShown = false;
    private final ChunkRegister register;
    private TerrainGenerator terrainGenerator;
    private Vector3i playerChunk;

    public World(long seed, Player player) {

        register = new ChunkRegister();
        terrainGenerator = new TerrainGenerator(seed);
        playerChunk = new Vector3i(Chunk.worldToChunkSpace(player.getPosition()));
        CreateChunksNearPlayer(player);
    }

    public void loadChunks(Player player) {
        if (!playerChunk.equals(Chunk.worldToChunkSpace(player.getPosition()))) {
            playerChunk.set(Chunk.worldToChunkSpace(player.getPosition()));
            CreateChunksNearPlayer(player);
        }
    }

    public void draw(Player player) {
        for (Chunk chunk : register.getAllChunks()) {
            chunk.draw(player);
        }
    }

    public void setBoundingBoxShown(boolean boundingBoxShown) {
        isBoundingBoxShown = boundingBoxShown;
    }

    public boolean isBoundingBoxShown() {
        return isBoundingBoxShown;
    }

    private void CreateChunksNearPlayer(Player player) {
        for (Vector3i pos : player.getChunkLoadingRange()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            if (!register.hasChunk(chunkPos)) {
                createChunk(chunkPos);
            }
        }
    }

    public void updateChunks() {
        for (Chunk chunk : register.getAllChunks()) {
            chunk.updateMesh(register);
        }
    }

    private void createChunk(ChunkPos chunkPos) {
        Chunk chunk = new Chunk(chunkPos, register);
        terrainGenerator.CreateChunk(chunk);
        chunk.updateMesh(register);
        updateNeighbours(chunk);
    }

    private void updateNeighbours(Chunk chunk) {
        NeighboringChunks neighbours = register.getNeighboringChunks(chunk.getChunkPos());
        for (Direction direction : Direction.values()) {
            Chunk neighbour = neighbours.get(direction);
            if (neighbour != null) {
                neighbour.markDirty(ChunkDirty.getDirtySide(direction));
            }
        }
    }
}

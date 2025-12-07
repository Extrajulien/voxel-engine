package demo_game.WorldGen;

import demo_game.Player.Player;
import demo_game.WorldGen.Chunk.*;
import demo_game.debug.LogEntry;
import demo_game.debug.Logger;
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

    public void update(Player player, double deltaTime) {
        loadChunks(player);
        updateChunks();
        log();
    }

    public void draw(Player player, ChunkRenderingMode renderingMode) {
        switch (renderingMode) {
            case NORMAL -> drawNormal(player);
            case HIGHLIGHT_PLAYER_CHUNK -> drawHighlighted(player);
            case WIREFRAME_CHUNKS -> drawWireframe(player);
            case CHUNK_LOADED_CHUNKS -> drawChunkLoadedBounds(player);
        }
    }

    private void loadChunks(Player player) {
        if (!playerChunk.equals(Chunk.worldToChunkSpace(player.getPosition()))) {
            playerChunk.set(Chunk.worldToChunkSpace(player.getPosition()));
            CreateChunksNearPlayer(player);
        }
    }

    private void log() {
        Logger.getInstance().Log(LogEntry.CHUNK_POS, playerChunk);
    }

    private void CreateChunksNearPlayer(Player player) {
        for (Vector3i pos : player.getChunkLoadingRange()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            if (!register.hasChunk(chunkPos)) {
                createChunk(chunkPos);
            }
        }
    }

    private void updateChunks() {
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

    private void drawWireframe(Player player) {
        for (Chunk chunk : register.getAllChunks()) {
            chunk.drawWireframe(player);
        }
    }

    private void drawHighlighted(Player player) {
        drawNormal(player);
        register.getChunk(new ChunkPos(playerChunk)).drawHighlighted(player);
    }

    private void drawNormal(Player player) {
        for (Chunk chunk : register.getAllChunks()) {
            chunk.draw(player);
        }
    }

    private void drawChunkLoadedBounds(Player player) {
        drawNormal(player);
        for (Vector3i pos : player.getChunkLoadingRange()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            if (register.hasChunk(chunkPos)) {
                register.getChunk(chunkPos).drawBounds(player);
            }
        }
    }
}

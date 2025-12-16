package demo_game.World;

import demo_game.Player.Player;
import demo_game.World.Chunk.Chunk;
import demo_game.World.Chunk.ChunkPos;
import demo_game.World.Chunk.ChunkRegister;
import demo_game.World.Chunk.ChunkRenderingMode;
import org.joml.Vector3i;

public class WorldRenderer {


    public void draw(Player player, ChunkRenderingMode renderingMode, ChunkRegister register) {
        switch (renderingMode) {
            case NORMAL -> drawNormal(player, register);
            case HIGHLIGHT_PLAYER_CHUNK -> drawHighlighted(player, register);
            case WIREFRAME_CHUNKS -> drawWireframe(player, register);
            case CHUNK_LOADED_CHUNKS -> drawChunkLoadedBounds(player, register);
        }
    }



    private void drawWireframe(Player player, ChunkRegister register) {
        for (Chunk chunk : register.getAllChunks()) {
            chunk.drawWireframe(player);
        }
    }

    private void drawHighlighted(Player player, ChunkRegister register) {
        drawNormal(player, register);
        register.getChunk(new ChunkPos(Chunk.worldToChunkSpace(player.getPosition()))).drawHighlighted(player);
    }

    private void drawNormal(Player player, ChunkRegister register) {
        for (Chunk chunk : register.getAllChunks()) {
            chunk.draw(player);
        }
    }

    private void drawChunkLoadedBounds(Player player, ChunkRegister register) {
        drawNormal(player, register);
        for (Vector3i pos : player.getChunkLoadingRange()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            if (register.hasChunk(chunkPos)) {
                register.getChunk(chunkPos).drawBounds(player);
            }
        }
    }
}

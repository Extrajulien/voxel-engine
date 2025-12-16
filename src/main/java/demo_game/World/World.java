package demo_game.World;

import demo_game.BlockType;
import demo_game.Player.Player;
import demo_game.RaycastHit;
import demo_game.World.Chunk.*;
import demo_game.World.Generation.TerrainGenerator;
import demo_game.debug.LogEntry;
import demo_game.debug.Logger;
import doctrina.Entities.MovableEntity;
import doctrina.Utils.Ray;
import doctrina.physic.CollisionCandidates;
import org.joml.RoundingMode;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class World implements WorldQuery,  WorldAction {
    private final ChunkRegister register;
    private final WorldCollider collider;
    private final WorldRenderer renderer;
    private final TerrainGenerator terrainGenerator;
    private final Vector3i playerChunk;

    public World(long seed, Player player) {
        register = new ChunkRegister();
        terrainGenerator = new TerrainGenerator(seed);
        collider = new WorldCollider();
        renderer = new WorldRenderer();
        playerChunk = new Vector3i(Chunk.worldToChunkSpace(player.getPosition()));
        CreateChunksNearPlayer(player);
    }

    public void update(Player player, double deltaTime) {
        loadChunks(player);
        updateChunks();
        log();
    }

    public CollisionCandidates getCollisionCandidates(MovableEntity entity) {
        return collider.getEntityCollision(entity, register);
    }

    public void draw(Player player, ChunkRenderingMode renderingMode) {
        renderer.draw(player, renderingMode, register);
    }

    public void drawCollisionBlocks(Player player) {
        collider.drawBlocksHitbox(player, register);
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
        updateChunkMesh(chunk);
    }


    private void updateChunkMesh(Chunk chunk) {
        chunk.updateMesh(register);
        register.markNeighboursDirty(chunk.getChunkPos());
    }

    @Override
    public void breakBlock(Vector3i pos) {
        register.setBlock(pos, BlockType.AIR);
    }

    @Override
    public void placeBlock(Vector3i pos, BlockType blockType) {
        register.setBlock(pos, blockType);
    }

    @Override
    public RaycastHit raycast(Ray ray, float maxDistance) {
        return null;
    }
}

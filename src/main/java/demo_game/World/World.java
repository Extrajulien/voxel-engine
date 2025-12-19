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
        Chunk chunk = register.getChunk(new ChunkPos (Chunk.worldToChunkSpace(pos)));
        register.markNeighboursDirty(chunk.getChunkPos());
    }

    @Override
    public void placeBlock(Vector3i pos, BlockType blockType) {
        register.setBlock(pos, blockType);
        Chunk chunk = register.getChunk(new ChunkPos (Chunk.worldToChunkSpace(pos)));
        register.markNeighboursDirty(chunk.getChunkPos());
    }



    // looked into the dda algorithm using the sources: https://lodev.org/cgtutor/raycasting.html and
    // https://www.youtube.com/watch?v=NbSee-XM7WA
    @Override
    public RaycastHit raycast(Ray ray, float maxDistance) {
        Vector3i sign = incrementSigns(ray);
        Vector3f rayStartOffset = rayStartOffset(ray);
        float xRayLength = 0;
        float yRayLength = 0;
        float zRayLength = 0;

        int hitAxis;

        Vector3i grid = new Vector3i();


        // how much the length of the ray scales when moved by 1 on the axis
        float xDelta = Math.abs(1.0f / ray.direction().x());
        float yDelta = Math.abs(1.0f / ray.direction().y());
        float zDelta = Math.abs(1.0f / ray.direction().z());

        int stepX = sign.x();
        int stepY = sign.y();
        int stepZ = sign.z();

        xRayLength += xDelta * rayStartOffset.x();
        yRayLength += yDelta * rayStartOffset.y();
        zRayLength += zDelta * rayStartOffset.z();

        while (true) {
            if (xRayLength < yRayLength && xRayLength < zRayLength) {
                xRayLength += xDelta;
                grid.x += stepX;
                hitAxis = 0;
            } else if (yRayLength < zRayLength) {
                yRayLength += yDelta;
                grid.y += stepY;
                hitAxis = 1;
            } else {
                zRayLength += zDelta;
                grid.z += stepZ;
                hitAxis = 2;
            }

            float t = Math.min(Math.min(xRayLength, yRayLength),  zRayLength);
            if (t > maxDistance) break;
            Logger.getInstance().Log(LogEntry.RAYCASTING_TARGET, grid);
            //Logger.getInstance().Log(LogEntry.RAYCASTING_DDA_RAY_LENGTHS, new Vector3f(xRayLength, yRayLength, zRayLength));
            //Logger.getInstance().Log(LogEntry.RAYCASTING_TARGET, grid);

            if (!register.getBlock(new Vector3i(grid).add(new Vector3i(ray.position(), RoundingMode.FLOOR))).isTransparent()) break;
        }


        Vector3i currentBlockPos = new Vector3i(grid).add(new Vector3i(ray.position(), RoundingMode.FLOOR));

        // the block face that was intersected
        Vector3i blockNormalSide = (hitAxis == 0 ? new Vector3i(-stepX,0,0)
        : hitAxis == 1 ? new Vector3i(0,-stepY,0)
                       : new Vector3i(0,0,-stepZ));


        return new RaycastHit(!register.getBlock(currentBlockPos).isTransparent(),
                currentBlockPos,
                blockNormalSide,
                register.getBlock(currentBlockPos));
    }

    private Vector3i incrementSigns(Ray ray) {
         Vector3i signs = new Vector3i(1,1,1);
         if (ray.direction().x() < 0) {
             signs.x = -1;
         }
        if (ray.direction().y() < 0) {
            signs.y = -1;
        }
        if (ray.direction().z() < 0) {
            signs.z = -1;
        }
        return signs;
    }

    private Vector3f rayStartOffset(Ray ray) {
        float rayX = ray.position().x();
        int mapX = (int)Math.floor(rayX);

        float deltaDistX = Math.abs(1.0f / ray.direction().x());

        float sideDistX;
        if (ray.direction().x() < 0) {
            sideDistX = (rayX - mapX) * deltaDistX;
        } else {
            sideDistX = (mapX + 1.0f - rayX) * deltaDistX;
        }

        float rayY = ray.position().y();
        int mapY = (int)Math.floor(rayY);

        float deltaDistY = Math.abs(1.0f / ray.direction().y());

        float sideDistY;
        if (ray.direction().y() < 0) {
            sideDistY = (rayY - mapY) * deltaDistY;
        } else {
            sideDistY = (mapY + 1.0f - rayY) * deltaDistY;
        }

        float rayZ = ray.position().z();
        int mapZ = (int)Math.floor(rayZ);

        float deltaDistZ = Math.abs(1.0f / ray.direction().z());

        float sideDistZ;
        if (ray.direction().z() < 0) {
            sideDistZ = (rayZ - mapZ) * deltaDistZ;
        } else {
            sideDistZ = (mapZ + 1.0f - rayZ) * deltaDistZ;
        }

        return new Vector3f(sideDistX, sideDistY, sideDistZ);
    }

}

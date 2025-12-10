package demo_game.World;

import demo_game.BlockType;
import demo_game.Models;
import demo_game.Uniforms.ChunkUniform;
import demo_game.World.Chunk.ChunkRegister;
import doctrina.Entities.MovableEntity;
import doctrina.Utils.Range1d;
import doctrina.Utils.Range3d;
import doctrina.rendering.Model;
import org.joml.Matrix4f;
import org.joml.RoundingMode;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class WorldCollider {
    private static final int entityCollisionRadius = 2;
    private static final int collisionDiameter = entityCollisionRadius * 2 + 1;

    private final Range3d[][][] collisionBlocks;
    private int currentPos;

    public WorldCollider() {
        currentPos = 0;
        collisionBlocks = new Range3d[collisionDiameter][collisionDiameter][collisionDiameter];
    }

    public void drawBlocksHitbox(Matrix4f view, Matrix4f projection) {
        Model<ChunkUniform> model = Models.makeChunkBoundingBox();
        Matrix4f modelMat = new Matrix4f();
        for (int x = 0; x < collisionBlocks.length; x++) {
            for (int y = 0; y < collisionBlocks[x].length; y++) {
                for (int z = 0; z < collisionBlocks[x][y].length; z++) {
                    Range3d range = collisionBlocks[x][y][z];
                    if (range != null) {
                        modelMat.translation(new Vector3f(range.getMaxX()- 0.5f, range.getMaxY()- 0.5f, range.getMaxZ()- 0.5f));
                        model.getMaterial().use();
                        model.drawBoundingBox(modelMat, view, projection);

                        collisionBlocks[x][y][z] = null;
                    }
                }
            }
        }
        currentPos = 0;
    }

    public void getCheckEntityCollision(MovableEntity entity, ChunkRegister register) {
        Vector3i entityPos = new Vector3i(entity.getPosition(), RoundingMode.FLOOR);
        Range3d collisionRange = new Range3d(
                new Range1d(entityPos.x - entityCollisionRadius, entityPos.x + entityCollisionRadius),
                new Range1d(entityPos.y - entityCollisionRadius, entityPos.y + entityCollisionRadius),
                new Range1d(entityPos.z - entityCollisionRadius, entityPos.z + entityCollisionRadius)
        );
        for (Vector3i pos : collisionRange) {
            BlockType block = register.getBlock(pos);
            if (!block.isTransparent()) {
                collisionChecks(entity, pos);
            }
        }
    }

    private void collisionChecks (MovableEntity entity, Vector3i solidBlockPos) {
        Range3d range3d = new Range3d(
                new Range1d(solidBlockPos.x, solidBlockPos.x + 1),
                new Range1d(solidBlockPos.y, solidBlockPos.y + 1),
                new Range1d(solidBlockPos.z, solidBlockPos.z + 1)
        );
        int x = currentPos % collisionDiameter;
        int y = currentPos / (collisionDiameter);
        int z = currentPos / (collisionDiameter * collisionDiameter);

        collisionBlocks[x][y][z] = range3d;
        ++currentPos;
        entity.collide(range3d);
    }
}

package demo_game.World;

import demo_game.BlockData;
import demo_game.BlockType;
import demo_game.Player.Player;
import demo_game.World.Chunk.ChunkRegister;
import doctrina.Entities.MovableEntity;
import doctrina.Utils.BoundingBox;
import doctrina.Utils.RangeI1d;
import doctrina.Utils.RangeI3d;
import doctrina.debug.Color;
import doctrina.physic.CollisionCandidates;
import doctrina.physic.HitBox;
import org.joml.RoundingMode;
import org.joml.Vector3i;

public class WorldCollider {
    private static final int entityCollisionRadius = 2;

    public WorldCollider() {
    }


    public void drawBlocksHitbox(Player player, ChunkRegister register) {


        CollisionCandidates candidates = getEntityCollision(player, register);

        for (BoundingBox candidate : candidates) {
            HitBox block = new HitBox(candidate);
            block.update(candidate.center());
            block.setColor(Color.RED);
            block.drawBounds(player.getCameraView().viewMatrix(), player.getCameraView().projectionMatrix());
        }
    }

    public CollisionCandidates getEntityCollision(MovableEntity entity, ChunkRegister register) {
        CollisionCandidates collisionCandidates = new CollisionCandidates();
        Vector3i entityPos = new Vector3i(entity.getPosition(), RoundingMode.FLOOR);
        RangeI3d collisionRange = new RangeI3d(
                new RangeI1d(entityPos.x - entityCollisionRadius, entityPos.x + entityCollisionRadius),
                new RangeI1d(entityPos.y - entityCollisionRadius, entityPos.y + entityCollisionRadius),
                new RangeI1d(entityPos.z - entityCollisionRadius, entityPos.z + entityCollisionRadius)
        );
        for (Vector3i pos : collisionRange) {
            BlockType block = register.getBlock(pos);
            if (block.isOn(BlockData.IS_SOLID)) {
                collisionCandidates.add(getBlockBoundingBox(pos));
            }
        }
        return collisionCandidates;
    }

    private BoundingBox getBlockBoundingBox(Vector3i pos) {
        return new BoundingBox(pos.x, pos.x + 1, pos.y, pos.y + 1, pos.z, pos.z + 1);
    }
}

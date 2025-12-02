package demo_game.World;

import org.joml.Vector3i;

public record ChunkPos(int x, int y, int z) {

    public ChunkPos(Vector3i vec) {
        this(vec.x, vec.y, vec.z);
    }
}

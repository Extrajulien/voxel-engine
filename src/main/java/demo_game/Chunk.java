package demo_game;

import org.joml.Vector3f;
import org.joml.Vector3i;

public class Chunk {
    private final static Vector3i size = new Vector3i(32, 32, 32);
    private final BlockType[][][] blocks = new BlockType[size.x][size.y][size.z];

    public Chunk(Vector3f worldPosition) {

    }
}

package demo_game.World;

import demo_game.BlockType;
import org.joml.Vector3i;

public interface WorldAction {
    void breakBlock(Vector3i pos);
    void placeBlock(Vector3i pos,  BlockType blockType);
}

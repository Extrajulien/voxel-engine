package demo_game;

import org.joml.Vector3i;

public record RaycastHit(boolean hit, Vector3i position, Vector3i normal, BlockType blockType) {
}

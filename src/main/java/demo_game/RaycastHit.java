package demo_game;

import org.joml.Vector3f;

public record RaycastHit(boolean hit, Vector3f position, Vector3f normal, BlockType blockType) {
}

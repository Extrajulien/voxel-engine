package demo_game.World.Chunk;

import demo_game.BlockType;
import demo_game.World.Direction;
import doctrina.rendering.Mesh;
import org.joml.Vector3i;

import java.util.ArrayList;

public class ChunkMesher {
    private final ArrayList<Float> vertices;
    private final ArrayList<Integer> indices;
    private final Chunk chunk;
    private Mesh mesh;

    public ChunkMesher(Chunk chunk) {
        vertices = new ArrayList<>();
        indices = new ArrayList<>();
        this.chunk = chunk;
    }

    public void updateSide(ChunkRegister register, Direction direction) {
        Chunk neighbor = register.getNeighbour(chunk.getChunkPos(), direction);
        vertices.clear();
        indices.clear();
        create(register);

    }

    public void create(ChunkRegister register) {
        for (Vector3i pos : chunk.getBlocksRange()) {
            BlockType block = chunk.getBlockType(pos);
            if (!block.isTransparent()) {
                for (Direction direction : Direction.values()) {
                    if (getNeighboringBlock(pos, register.getNeighboringChunks(chunk.getChunkPos()), direction).isTransparent()) {
                        generateFace(block, pos, direction);
                    }
                }
            }
        }
        mesh = new Mesh(vertices, indices);
    }

    public Mesh getMesh() {
        return mesh;
    }

    private BlockType getNeighboringBlock(Vector3i pos, NeighboringChunks neighbors, Direction direction) {
        int x = pos.x + direction.x;
        int y = pos.y + direction.y;
        int z = pos.z + direction.z;

        if (chunk.getBlocksRange().isPointInRange(x, y, z)) {
            return chunk.getBlockType(x, y, z);
        }

        Chunk neighbor = neighbors.get(direction);
        if (neighbor == null) {
            return BlockType.AIR;
        }
        x = Chunk.posWorldWrapToChunk(x);
        y = Chunk.posWorldWrapToChunk(y);
        z = Chunk.posWorldWrapToChunk(z);

        return neighbor.getBlockType(x, y, z);
    }


    public void generateFace(BlockType block, Vector3i blockPos, Direction direction) {
        float x = blockPos.x;
        float y = blockPos.y;
        float z = blockPos.z;

        int startIndex = vertices.size() / 5;

        vertices.add(x + direction.getBottomLeftCorner().x);
        vertices.add(y + direction.getBottomLeftCorner().y);
        vertices.add(z + direction.getBottomLeftCorner().z);
        vertices.add(0f);
        vertices.add(0f);

        vertices.add(x + direction.getBottomRightCorner().x);
        vertices.add(y + direction.getBottomRightCorner().y);
        vertices.add(z + direction.getBottomRightCorner().z);
        vertices.add(1f);
        vertices.add(0f);

        vertices.add(x + direction.getTopRightCorner().x);
        vertices.add(y + direction.getTopRightCorner().y);
        vertices.add(z + direction.getTopRightCorner().z);
        vertices.add(1f);
        vertices.add(1f);

        vertices.add(x + direction.getTopLeftCorner().x);
        vertices.add(y + direction.getTopLeftCorner().y);
        vertices.add(z + direction.getTopLeftCorner().z);
        vertices.add(0f);
        vertices.add(1f);

        indices.add(startIndex);
        indices.add(startIndex + 1);
        indices.add(startIndex + 2);

        indices.add(startIndex + 2);
        indices.add(startIndex + 3);
        indices.add(startIndex);
    }
}

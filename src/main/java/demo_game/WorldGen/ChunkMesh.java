package demo_game.WorldGen;

import demo_game.BlockType;
import demo_game.Player.Player;
import demo_game.Uniforms.WorldUniform;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3i;

import java.util.ArrayList;

public class ChunkMesh {
    private final static Texture dirtBlock = new Texture(BlockType.DIRT.getTexturePaths()[0]);
    private final static Shader<WorldUniform> shader = new Shader<>(WorldUniform.class, "vertex.glsl", "WorldFragment.glsl");
    private final Material<WorldUniform> material = new Material<>(shader, dirtBlock);
    private final ArrayList<Float> vertices;
    private final ArrayList<Integer> indices;
    private Mesh mesh;
    private Model<WorldUniform> chunkModel;
    private final Chunk chunk;


    public ChunkMesh(Chunk chunk, NeighboringChunks surroundingChunks) {
        this.chunk = chunk;
        vertices = new ArrayList<>();
        indices = new ArrayList<>();


        for (Vector3i pos : chunk.getBlocksRange()) {
            BlockType block = chunk.getBlockType(pos);

            if (!block.isTransparent()) {
                for (Direction direction : Direction.values()) {
                    if (getNeighboringBlock(pos, surroundingChunks, direction).isTransparent()) {
                        generateFace(pos, direction);
                    }
                }
            }
        }



        if (!chunk.isEmpty()) {
            mesh = new Mesh(vertices, indices);
        }


        if (mesh != null) {
            chunkModel = new Model<>(mesh, material);
        }
    }

    public void draw(Player player) {
        if (chunkModel != null) {
            chunkModel.draw(
                    new Matrix4f().translate(chunk.getChunkPos().x * 16, chunk.getChunkPos().y * 16, chunk.getChunkPos().z * 16),
                    player.getCameraView().viewMatrix(),
                    player.getCameraView().projectionMatrix());
        }
    }

    private BlockType getNeighboringBlock(Vector3i position, NeighboringChunks neighbors, Direction direction) {
        int x = position.x + direction.x;
        int y = position.y + direction.y;
        int z = position.z + direction.z;

        if (chunk.getBlocksRange().isPointInRange(x, y, z)) {
            return chunk.getBlockType(x, y, z);
        }

        Chunk neighbor = neighbors.get(direction);
        if (neighbor == null) {
            return BlockType.AIR;
        }
        x = wrapNumAroundChunk(x);
        y = wrapNumAroundChunk(y);
        z = wrapNumAroundChunk(z);

        return neighbor.getBlockType(x, y, z);
    }

    private int wrapNumAroundChunk(int number) {
        if (number >= Chunk.SIZE) {
            number -= Chunk.SIZE;
        }
        if (number < 0) {
            number += Chunk.SIZE;
        }

        return number;
    }

    private BlockType getBlockUnder(Vector3i position, NeighboringChunks surroundingChunks) {
        int x = position.x;
        int y = position.y;
        int z = position.z;
        if (chunk.getBlocksRange().getMinY() <= y - 1) {
            return chunk.getBlockType(x, y - 1, z);
        }

        Chunk bottom = surroundingChunks.get(Direction.BOTTOM);
        if (bottom == null) {
            return BlockType.AIR;
        }

        return bottom.getBlockType(x, (int) bottom.getBlocksRange().getMaxY(), z);
    }

    private BlockType getBlockEast(Vector3i position, NeighboringChunks surroundingChunks) {
        if (!chunk.getBlocksRange().isPointInRange(new Vector3i(position.x + 1, position.y, position.z))) {
            if (surroundingChunks.get(Direction.EAST) == null) {
                return BlockType.AIR;
            }
            return surroundingChunks.get(Direction.EAST).getBlockType(new Vector3i(0, position.y, position.z));
        }

        return chunk.getBlockType(new Vector3i(position.x + 1, position.y, position.z));
    }

    private BlockType getBlockWest(Vector3i position, NeighboringChunks surroundingChunks) {
        if (!chunk.getBlocksRange().isPointInRange(new Vector3i(position.x - 1, position.y, position.z))) {
            if (surroundingChunks.get(Direction.WEST) == null) {
                return BlockType.AIR;
            }
            return surroundingChunks.get(Direction.WEST).getBlockType(new Vector3i(Chunk.SIZE-1, position.y, position.z));
        }

        return chunk.getBlockType(new Vector3i(position.x - 1, position.y, position.z));
    }

    private void generateFace(Vector3i blockPos, Direction direction) {
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

        indices.add(startIndex + 0);
        indices.add(startIndex + 1);
        indices.add(startIndex + 2);

        indices.add(startIndex + 2);
        indices.add(startIndex + 3);
        indices.add(startIndex + 0);
    }
}

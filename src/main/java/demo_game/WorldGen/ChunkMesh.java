package demo_game.WorldGen;

import demo_game.BlockType;
import demo_game.Player.Player;
import demo_game.Uniforms.WorldUniform;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3i;

import java.util.ArrayList;

public class ChunkMesh {
    private final static Texture dirtBlock = new Texture("block/dirt.jpg");
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
                if (getBlockAbove(pos, surroundingChunks).isTransparent()) {
                    generateTopFace(pos);
                }
                if (getBlockUnder(pos, surroundingChunks).isTransparent()) {
                    generateBottomFace(pos);
                }
                if (getBlockEast(pos, surroundingChunks).isTransparent()) {
                    generateEastFace(pos);
                }
                if (getBlockWest(pos, surroundingChunks).isTransparent()) {
                    generateWestFace(pos);
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

    private BlockType getBlockAbove(Vector3i pos, NeighboringChunks neighbors) {
        int x = pos.x;
        int y = pos.y;
        int z = pos.z;

        if (y + 1 <= chunk.getBlocksRange().getMaxY()) {
            return chunk.getBlockType(x, y + 1, z);
        }

        Chunk top = neighbors.get(NeighboringChunks.Direction.TOP);
        if (top == null) {
            return BlockType.AIR;
        }

        if (0 < top.getBlocksRange().getMaxY()) {
            return top.getBlockType(x, 0, z);
        }

        return BlockType.AIR;
    }

    private BlockType getBlockUnder(Vector3i position, NeighboringChunks surroundingChunks) {
        if (!chunk.getBlocksRange().isPointInRange(new Vector3i(position.x, position.y-1, position.z))) {
            if (surroundingChunks.get(NeighboringChunks.Direction.BOTTOM) == null) {
                return BlockType.AIR;
            }
            return surroundingChunks.get(NeighboringChunks.Direction.BOTTOM).getBlockType(new Vector3i(position.x, Chunk.SIZE-1, position.z));
        }

        return chunk.getBlockType(new Vector3i(position.x, position.y - 1, position.z));
    }

    private BlockType getBlockEast(Vector3i position, NeighboringChunks surroundingChunks) {
        if (!chunk.getBlocksRange().isPointInRange(new Vector3i(position.x + 1, position.y, position.z))) {
            if (surroundingChunks.get(NeighboringChunks.Direction.EAST) == null) {
                return BlockType.AIR;
            }
            return surroundingChunks.get(NeighboringChunks.Direction.EAST).getBlockType(new Vector3i(0, position.y, position.z));
        }

        return chunk.getBlockType(new Vector3i(position.x + 1, position.y, position.z));
    }

    private BlockType getBlockWest(Vector3i position, NeighboringChunks surroundingChunks) {
        if (!chunk.getBlocksRange().isPointInRange(new Vector3i(position.x - 1, position.y, position.z))) {
            if (surroundingChunks.get(NeighboringChunks.Direction.WEST) == null) {
                return BlockType.AIR;
            }
            return surroundingChunks.get(NeighboringChunks.Direction.WEST).getBlockType(new Vector3i(Chunk.SIZE-1, position.y, position.z));
        }

        return chunk.getBlockType(new Vector3i(position.x - 1, position.y, position.z));
    }


    private void generateTopFace(Vector3i pos) {
        generateYFace(new Vector3i(pos.x, pos.y + 1, pos.z));
    }

    private void generateBottomFace(Vector3i pos) {
        generateYFace(pos);
    }

    private void generateEastFace(Vector3i pos) {
        generateXFace(new Vector3i(pos.x + 1, pos.y, pos.z));
    }

    private void generateWestFace(Vector3i pos) {
        generateXFace(pos);
    }

    private void generateYFace(Vector3i pos) {
        float x = pos.x;
        float y = pos.y; // top face plane
        float z = pos.z;

        int startIndex = vertices.size() / 5; // each vertex = 5 floats

        // v0 (bottom-left in UV space)
        vertices.add(x);      // x
        vertices.add(y);      // y
        vertices.add(z);      // z
        vertices.add(0f);     // u
        vertices.add(0f);     // v

        // v1 (bottom-right)
        vertices.add(x + 1);
        vertices.add(y);
        vertices.add(z);
        vertices.add(1f);
        vertices.add(0f);

        // v2 (top-right)
        vertices.add(x + 1);
        vertices.add(y);
        vertices.add(z + 1);
        vertices.add(1f);
        vertices.add(1f);

        // v3 (top-left)
        vertices.add(x);
        vertices.add(y);
        vertices.add(z + 1);
        vertices.add(0f);
        vertices.add(1f);

        // Indices (two triangles)
        indices.add(startIndex + 0);
        indices.add(startIndex + 1);
        indices.add(startIndex + 2);

        indices.add(startIndex + 2);
        indices.add(startIndex + 3);
        indices.add(startIndex + 0);
    }

    private void generateXFace(Vector3i pos) {
        float x = pos.x;
        float y = pos.y;
        float z = pos.z;

        int startIndex = vertices.size() / 5; // each vertex = 5 floats

        // v0 (bottom-left)
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);
        vertices.add(0f); // u
        vertices.add(0f); // v

        // v1 (bottom-right)
        vertices.add(x);
        vertices.add(y);
        vertices.add(z + 1);
        vertices.add(1f);
        vertices.add(0f);

        // v2 (top-right)
        vertices.add(x);
        vertices.add(y + 1);
        vertices.add(z + 1);
        vertices.add(1f);
        vertices.add(1f);

        // v3 (top-left)
        vertices.add(x);
        vertices.add(y + 1);
        vertices.add(z);
        vertices.add(0f);
        vertices.add(1f);

        // Indices
        indices.add(startIndex + 0);
        indices.add(startIndex + 1);
        indices.add(startIndex + 2);

        indices.add(startIndex + 2);
        indices.add(startIndex + 3);
        indices.add(startIndex + 0);
    }

}

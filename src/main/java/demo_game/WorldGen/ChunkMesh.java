package demo_game.WorldGen;

import demo_game.BlockType;
import demo_game.Player.Player;
import demo_game.Uniforms.WorldUniform;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3i;

public class ChunkMesh {
    private final static Texture dirtBlock = new Texture("block/dirt.jpg");
    private final static Shader<WorldUniform> shader = new Shader<>(WorldUniform.class, "vertex.glsl", "WorldFragment.glsl");
    private final Material<WorldUniform> material = new Material<>(shader, dirtBlock);
    private Mesh mesh;
    private Model<WorldUniform> chunkModel;
    private final Chunk chunk;


    public ChunkMesh(Chunk chunk, NeighboringChunks surroundingChunks) {
        this.chunk = chunk;


        if (!chunk.isEmpty()) {
            mesh = new Mesh.Builder().cube().build();
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





}

package demo_game.WorldGen.Chunk;

import demo_game.BlockType;
import demo_game.Player.Player;
import demo_game.Uniforms.WorldUniform;
import demo_game.WorldGen.Direction;
import doctrina.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3i;

import java.util.ArrayList;

public class ChunkMesh {
    private final static Texture dirtBlock = new Texture(BlockType.DIRT.getTexturePaths()[0]);
    private final static Shader<WorldUniform> shader = new Shader<>(WorldUniform.class, "vertex.glsl", "WorldFragment.glsl");
    private final Material<WorldUniform> material = new Material<>(shader, dirtBlock);
    private final Matrix4f modelMatrix;
    private Mesh mesh;
    private Vector3i worldSpacePos;
    private Model<WorldUniform> chunkModel;
    private final ChunkMesher mesher;


    public ChunkMesh(Chunk chunk) {
        mesher = new ChunkMesher(chunk);
        worldSpacePos = Chunk.chunkToWorldSpace(chunk.getChunkPos());
        modelMatrix = new Matrix4f().translate(worldSpacePos.x, worldSpacePos.y, worldSpacePos.z);
    }

    public void draw(Player player) {
        if (chunkModel != null) {
            chunkModel.draw(
                    modelMatrix,
                    player.getCameraView().viewMatrix(),
                    player.getCameraView().projectionMatrix()
            );
        }
    }

    public void drawWireframe(Player player) {
        if (chunkModel != null) {
            chunkModel.drawWireFrame(
                    modelMatrix,
                    player.getCameraView().viewMatrix(),
                    player.getCameraView().projectionMatrix()
            );
        }
    }

    public void update(int dirtyMask, ChunkRegister register) {
        boolean isSelfUpdated = (dirtyMask & ChunkDirty.SELF) == ChunkDirty.SELF;
        boolean meshNotInit = mesh == null;

        if (isSelfUpdated || meshNotInit) {
            mesher.create(register);
        } else {
            for (Direction direction : Direction.values()) {
                if (ChunkDirty.hasFlag(dirtyMask, direction)) {
                    mesher.updateSide(register, direction);
                }
            }
        }

        this.mesh = mesher.getMesh();
        this.chunkModel = new Model<>(mesh, material);
    }
}

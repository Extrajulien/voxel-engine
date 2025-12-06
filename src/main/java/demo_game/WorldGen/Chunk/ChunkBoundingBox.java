package demo_game.WorldGen.Chunk;

import demo_game.Models;
import demo_game.Player.Player;
import demo_game.Uniforms.ChunkUniform;
import doctrina.rendering.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11C.glLineWidth;

public class ChunkBoundingBox {
    private static final Model<ChunkUniform> bounds = Models.makeChunkBoundingBox();
    private final Matrix4f modelMatrix;
    private final ChunkPos worldPos;

    public ChunkBoundingBox(ChunkPos worldPos) {
        this.worldPos = worldPos;
        modelMatrix = makeModelMatrix();
    }

    public void draw(Player player) {
        glLineWidth(2.0f);
        bounds.drawBoundingBox(modelMatrix, player.getCameraView().viewMatrix(), player.getCameraView().projectionMatrix());
    }

    public void setColor(Vector3f color) {
        bounds.getMaterial().setUniform(ChunkUniform.LINE_COLOR, color);
    }



    private Matrix4f makeModelMatrix() {
        return new Matrix4f().scale(Chunk.SIZE).setTranslation(
                worldPos.x() * Chunk.SIZE + (float) Chunk.SIZE / 2,
                worldPos.y() * Chunk.SIZE + (float) Chunk.SIZE / 2,
                worldPos.z() * Chunk.SIZE + (float) Chunk.SIZE / 2
        );
    }
}

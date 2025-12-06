package demo_game;

import demo_game.Uniforms.*;
import doctrina.rendering.*;
import org.joml.Vector3f;

public class Models {
    public static Model<PlayerUniform> makePlayer() {
        Shader<PlayerUniform> shader = new Shader<>(PlayerUniform.class, "vertex.glsl", "imageFragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("block/dirt.jpg");
        Material<PlayerUniform> dirt = new Material<>(shader, dirtTex);
        Mesh cube = new Mesh.Builder().cube().build();
        return new Model<>(cube, dirt);
    }

    public static Model<ChunkUniform> makeChunkBoundingBox() {
        Shader<ChunkUniform> shader = new Shader<>(ChunkUniform.class, "vertex.glsl", "chunkBoundingBoxFragment.glsl");
        shader.use();
        Material<ChunkUniform> material = new Material<>(shader);
        material.setUniform(ChunkUniform.LINE_COLOR, new Vector3f(0,0,0));
        Mesh cube = new Mesh.Builder().boundingBox().build();
        return new Model<>(cube, material);
    }

    public static Model<EnemyUniform> makeEnemy() {
        Shader<EnemyUniform> shader = new Shader<>(EnemyUniform.class, "vertex.glsl", "imageFragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("toaster.png");
        Material<EnemyUniform> dirt = new Material<>(shader, dirtTex);
        Mesh cube = new Mesh.Builder().cube().build();
        return new Model<>(cube, dirt);
    }
}

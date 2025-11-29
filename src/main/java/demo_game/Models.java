package demo_game;

import demo_game.Uniforms.CubeUniform;
import doctrina.rendering.*;

public class Models {
    public static Model<CubeUniform> makePlayer() {
        Shader<CubeUniform> shader = new Shader<>(CubeUniform.class, "vertex.glsl", "imageFragment.glsl");
        shader.use();
        Texture dirtTex = new Texture("block/dirt.jpg");
        Material<CubeUniform> dirt = new Material<>(shader, dirtTex);
        Mesh cube = new Mesh.Builder().cube().build();
        return new Model<>(cube, dirt);
    }
}

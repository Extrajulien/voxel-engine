package doctrina.rendering;

import org.joml.Matrix4f;

public class Material {
    private final Shader shader;
    private final Texture[] textures;

    public Material(Shader shader, Texture... textures) {
        this.shader = shader;
        this.textures = textures;
    }

    public void use() {
        shader.use();
    }

    public void bindTextures() {
        for (Texture texture : textures) {
            texture.bind();
        }
    }

    public void setModelMatrix(Matrix4f matrix) {
        shader.setUniform("model", matrix);
    }

    public void setViewMatrix(Matrix4f matrix) {
        shader.setUniform("view", matrix);
    }

    public void setProjectionMatrix(Matrix4f matrix) {
        shader.setUniform("projection", matrix);
    }
}

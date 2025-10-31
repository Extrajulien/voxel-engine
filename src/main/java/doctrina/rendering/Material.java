package doctrina.rendering;

import org.joml.Matrix4f;

public class Material {
    private Shader shader;
    private Texture[] textures;

    public Material(Shader shader, Texture... textures) {
        this.shader = shader;
        this.textures = textures;
    }

    public void use() {
        for (Texture texture : textures) {
            texture.bind();
        }
        shader.use();
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

package doctrina.rendering;

import doctrina.Uniform.EngineUniform;
import doctrina.Uniform.Uniform;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Material<U extends Enum<U> & Uniform> {
    private final Shader<U> shader;
    private final Texture[] textures;

    public Material(Shader<U> shader, Texture... textures) {
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

    public void setUniform(U uniform, Matrix4f matrix) {
        shader.setUniform(uniform, matrix);
    }

    public void setUniform(U uniform, Vector3f vector3f) {
        shader.setUniform(uniform, vector3f);
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        shader.setUniform(EngineUniform.MODEL, modelMatrix);
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        shader.setUniform(EngineUniform.VIEW, viewMatrix);
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        shader.setUniform(EngineUniform.PROJECTION, projectionMatrix);
    }
}

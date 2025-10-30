package doctrina.rendering;

public class Material {
    Shader shader;
    Texture[] textures;

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
}

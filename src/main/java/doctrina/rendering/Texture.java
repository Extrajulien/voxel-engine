package doctrina.rendering;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;

public class Texture {
    private final int textureID;

    public Texture(String fileName) {
        try (Image image = new Image(fileName)) {
            textureID = glGenTextures();
            bind();
            assignImageToTexture(image);
            setTextureWrap();
            setScalingFilters();
        }
    }

    public int getTextureID() {
        return textureID;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    private void setScalingFilters() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    }

    private void setTextureWrap() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    }

    private void assignImageToTexture(Image image) {
        image.uploadToGPU();
        glGenerateMipmap(GL_TEXTURE_2D);
    }
}

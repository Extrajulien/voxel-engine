package doctrina.rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private final int textureID;
    private int width;
    private int height;

    public Texture(String fileName) {
        ByteBuffer imageData;
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer nbChannels = BufferUtils.createIntBuffer(1);
        String tempTexturePath = generateTempFile(fileName);
        imageData = stbi_load(tempTexturePath, width, height, nbChannels, 0);
        deleteTempFile(tempTexturePath);
        this.width = width.get(0);
        this.height = height.get(0);


        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        if (imageData == null) {
            System.err.println("ERROR Failed to load image: " + STBImage.stbi_failure_reason());
            System.exit(1);
        }
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                0, GL_RGB, GL_UNSIGNED_BYTE, imageData);
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(imageData);
    }

    public int getTextureID() {
        return textureID;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    private String generateTempFile(String file) {
        Path temp = null;
        InputStream iStream = Objects.requireNonNull(
                getClass().getResourceAsStream("/textures/" + file)
        );
        try {
            temp = Files.createTempFile(file, null);
            Files.copy(iStream, temp, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("ERROR Failed to make Temporary image");
            System.exit(1);
        }

        return temp.toString();
    }

    private void deleteTempFile(String filePath) {
        try {
            Files.delete(Path.of(filePath));
        } catch (IOException e) {
            System.err.println("ERROR Failed to delete Temporary image");
            System.exit(1);
        }
    }
}

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
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Image implements AutoCloseable {
    private ByteBuffer data;
    private int width;
    private int height;
    private int format;

    public Image(String fileName) {
        loadImage(fileName);
    }

    @Override
    public void close() {
        stbi_image_free(data);
    }

    public void uploadToGPU() {
        assertData();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                format, GL_UNSIGNED_BYTE, data);
    }

    private void assertData() throws RuntimeException {
        if (data == null) {
            throw new RuntimeException("ERROR Failed to load image: " + STBImage.stbi_failure_reason());
        }
    }

    private void loadImage(String fileName) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer nbChannels = BufferUtils.createIntBuffer(1);
        String tempTexturePath = generateTempFile(fileName);
        this.data = stbi_load(tempTexturePath, width, height, nbChannels, 0);
        deleteTempFile(tempTexturePath);
        this.width = width.get(0);
        this.height = height.get(0);
        this.format = getImageFormat(nbChannels.get(0));
    }

    private int getImageFormat(int nbChannels) {
        if (nbChannels == 3) {
            return GL_RGB;
        }
        if (nbChannels == 4) {
            return GL_RGBA;
        }
        return GL_RED;
    }

    private String generateTempFile(String file) throws RuntimeException {
        Path temp;
        InputStream iStream = Objects.requireNonNull(
                getClass().getResourceAsStream("/textures/" + file)
        );
        try {
            temp = Files.createTempFile(file, null);
            Files.copy(iStream, temp, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Failed to make Temporary image");
        }

        return temp.toString();
    }

    private void deleteTempFile(String filePath) {
        try {
            Files.delete(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete Temporary image");
        }
    }
}

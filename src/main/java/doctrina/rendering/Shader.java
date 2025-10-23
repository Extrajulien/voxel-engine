package doctrina.rendering;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20C.*;

public class Shader {
    private int shaderProgramId;

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        try {
            shaderProgramId = glCreateProgram();
            int vertexShader =  compileShader(vertexShaderFile, GL_VERTEX_SHADER);
            int fragmentShader = compileShader(fragmentShaderFile, GL_FRAGMENT_SHADER);
            linkShadersToProgram();

            glDeleteShader(vertexShader);
            glDeleteShader(fragmentShader);
        } catch (Exception e) {
            stopProgram();
        }
    }

    public void use() {
        glUseProgram(shaderProgramId);
    }

    public void setUniform(String uniform, float value) {
        glUniform1f(glGetUniformLocation(shaderProgramId, uniform), value);
    }

    private int compileShader(String shaderFile, int shaderType) throws IOException {
        String fragmentCode = Files.readString(Paths.get(shaderFile));
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, fragmentCode);
        glCompileShader(shader);
        checkShaderCompileErrors(shader, shaderType);
        glAttachShader(shaderProgramId, shader);
        return shader;
    }


    private void checkShaderCompileErrors(int shaderId, int shaderType) {
        int success = glGetShaderi(shaderId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            String infoLog = glGetShaderInfoLog(shaderId);
            System.err.println("ERROR shader compilation error of type: " +
                    getShaderNameFromShaderType(shaderType) + "\n" + infoLog);
            System.exit(1);
        }
    }

    private String getShaderNameFromShaderType(int shaderType) {
        switch (shaderType) {
            case GL_FRAGMENT_SHADER -> {
                return "Fragment";
            }
            case GL_VERTEX_SHADER -> {
                return "Vertex";
            }
            default -> {
                return "Unknown";
            }
        }
    }

    private void linkShadersToProgram() {
        glLinkProgram(shaderProgramId);
        checkShaderLinkingErrors();
    }

    private void stopProgram() {
        System.err.println("ERROR shader could no be read");
        System.exit(1);
    }

    private void checkShaderLinkingErrors() {
        int success = glGetProgrami(shaderProgramId, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            String infoLog = glGetProgramInfoLog(shaderProgramId);
            System.err.println("ERROR shaderProgram Linking error\n" + infoLog);
            System.exit(1);
        }
    }
}

package doctrina.rendering;

import doctrina.Uniform.EngineUniform;
import doctrina.Uniform.Uniform;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20C.*;

public class Shader<U extends Enum<U> & Uniform> {
    private int shaderProgramId;
    private final ShaderLUT<U> uniformLocationLUT;
    public final Class<U> Uniform;

    public Shader(Class<U> enumClass, String vertexShaderFile, String fragmentShaderFile) {
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

        uniformLocationLUT = new ShaderLUT<>(enumClass, shaderProgramId);
        this.Uniform = enumClass;

    }

    public int getShaderProgramId() {
        return shaderProgramId;
    }

    public void use() {
        glUseProgram(shaderProgramId);
    }

    public void setUniform(U uniform, Matrix4f matrix) {
        float[] matrixData = new float[16];
        matrix.get(matrixData);
        glUniformMatrix4fv(uniformLocationLUT.getLocation(uniform),false,  matrixData);
    }

    public void setUniform(EngineUniform uniform, Matrix4f matrix) {
        float[] matrixData = new float[16];
        matrix.get(matrixData);
        glUniformMatrix4fv(uniformLocationLUT.getLocation(uniform),false,  matrixData);
    }

    public void setUniform(U uniform, Vector3f vector3f) {
        glUniform3f(uniformLocationLUT.getLocation(uniform), vector3f.x, vector3f.y, vector3f.z);
    }


    private int compileShader(String shaderFile, int shaderType) {
        String fragmentCode = getResourcesShaderCode(shaderFile);
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

    private String getResourcesShaderCode(String shaderFile) {
        String fragmentCode = "";
        try (InputStream in = getClass().getResourceAsStream("/shaders/" + shaderFile)) {
            if (in == null) {
                throw new IOException("Shader resource not found: " + shaderFile);
            }
            fragmentCode = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            System.err.println("Wrong Shader File");
        }
        return fragmentCode;
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

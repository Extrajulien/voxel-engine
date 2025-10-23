package doctrina.rendering;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20C.*;

public class Shader {
    private int shaderProgramId;

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        try {
            String vertexCode = Files.readString(Paths.get(vertexShaderFile));
            int vertexShader = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vertexShader, vertexCode);
            glCompileShader(vertexShader);
            checkShaderCompileErrors(vertexShader, "Vertex");


            String fragmentCode = Files.readString(Paths.get(fragmentShaderFile));
            int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fragmentShader, fragmentCode);
            glCompileShader(fragmentShader);
            checkShaderCompileErrors(fragmentShader, "Fragment");


            shaderProgramId = glCreateProgram();
            glAttachShader(shaderProgramId, vertexShader);
            glAttachShader(shaderProgramId, fragmentShader);
            glLinkProgram(shaderProgramId);
            checkShaderLinkingErrors();

            glDeleteShader(vertexShader);
            glDeleteShader(fragmentShader);
        } catch (Exception e) {
            System.err.println("ERROR shader could no be read");
            System.exit(1);
        }
    }

    public void use() {
        glUseProgram(shaderProgramId);
    }

    public void setUniform(String uniform, float value) {

    }


    private void checkShaderCompileErrors(int shaderId, String Label) {
        int success = glGetShaderi(shaderId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            String infoLog = glGetShaderInfoLog(shaderId);
            System.err.println("ERROR shader compilation error of type: " + Label + "\n" + infoLog);
            System.exit(1);
        }
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

package demo_game;

import doctrina.Game;
import doctrina.renderingEngine.RenderingEngine;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class DemoGame extends Game {
    int triangleVAO;
    int triangleVBO;


    String vertexSource = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            void main()
            {
               gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
            }""";

    String fragmentSource = """
            #version 330 core
            out vec4 FragColor;
            
            void main()
            {
                FragColor = vec4(0.2f, 0.5f, 0.2f, 1.0f);
            }""";

    int vertexShader;
    int fragmentShader;
    int shaderProgram;

    float[] triangle = {
            0.0f,  0.5f, 0f,   // top vertex
            -0.5f, -0.5f, 0f,   // bottom-left
            0.5f, -0.5f, 0f    // bottom-right
    };
    @Override
    public void initialize() {

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexSource);
        glCompileShader(vertexShader);
        checkCompileErrors(vertexShader, "Vertex");

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentSource);
        glCompileShader(fragmentShader);
        checkCompileErrors(fragmentShader, "Fragment");

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        glUseProgram(shaderProgram);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        triangleVAO = glGenVertexArrays();
        glBindVertexArray(triangleVAO);
        triangleVBO = glGenBuffers();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(triangle.length);
        buffer.put(triangle).flip();
        glBindBuffer(GL_ARRAY_BUFFER, triangleVBO);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);

    }

    @Override
    public void update() {
        if (glfwGetKey(RenderingEngine.getWindow(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            stop();
        }
    }

    @Override
    public void draw() {
        glBindVertexArray(triangleVAO);
            glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    private void checkCompileErrors(int shader, String type) {
        int success;
        if (type.equals("PROGRAM")) {
            success = glGetProgrami(shader, GL_LINK_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetProgramInfoLog(shader);
                System.err.println("ERROR::PROGRAM_LINKING_ERROR of type: " + type + "\n" + infoLog);

            }
        } else {
            success = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (success == GL_FALSE) {
                String infoLog = glGetShaderInfoLog(shader);
                System.err.println("ERROR::SHADER_COMPILATION_ERROR of type: " + type + "\n" + infoLog);
                System.exit(1);
            }
        }
    }
}

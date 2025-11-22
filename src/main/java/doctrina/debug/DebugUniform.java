package doctrina.debug;
import doctrina.rendering.Shader;
import doctrina.rendering.Uniform;

import static org.lwjgl.opengl.GL20C.glGetUniformLocation;

public enum DebugUniform implements Uniform {
    HITBOX_COLOR("hitboxColor");

    final String uniformName;
    int uniformLocation;

    DebugUniform(String name) {
        uniformName = name;

    }

    @Override
    public String getUniformName() {
        return uniformName;
    }

    @Override
    public int getUniformLocation() {
        return 0;
    }

    @Override
    public void loadPositionLUT(Shader shader) {
        for (DebugUniform uniform : DebugUniform.values()) {
            //uniform.uniformLocation = glGetUniformLocation(shaderProgramId, uniform.uniformName);
        }
    }
}

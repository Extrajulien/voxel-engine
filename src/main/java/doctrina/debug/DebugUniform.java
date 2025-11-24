package doctrina.debug;
import doctrina.rendering.Shader;
import doctrina.rendering.Uniform;

import static org.lwjgl.opengl.GL20C.glGetUniformLocation;

public enum DebugUniform implements Uniform {
    HITBOX_COLOR("hitboxColor");

    private final static int NO_LOCATION = -1;
    private static Shader shader;
    private static boolean isInitialized = false;

    final String uniformName;

    int uniformLocation = NO_LOCATION;

    DebugUniform(String name) {
        uniformName = name;
    }

    @Override
    public String getUniformName() {
        return uniformName;
    }

    public static boolean isIsInitialized() {
        return isInitialized;
    }

    @Override
    public int getUniformLocation() {
        if (uniformLocation == NO_LOCATION) {
            throw new NullPointerException("Uniform location is not tied to shader program");
        }
        return uniformLocation;
    }

    @Override
    public boolean isCorrectShader(Shader shader) {
        return DebugUniform.shader == shader;
    }

    @Override
    public void loadPositionLUT(Shader shader) {
        isInitialized = true;
        DebugUniform.shader = shader;
        int programId = shader.getShaderProgramId();
        for (DebugUniform uniform : DebugUniform.values()) {
            uniform.uniformLocation = glGetUniformLocation(programId, uniform.uniformName);
        }
    }
}

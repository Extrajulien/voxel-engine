package doctrina.rendering;

import doctrina.Uniform.EngineUniform;
import doctrina.Uniform.Uniform;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20C.glGetUniformLocation;

public class ShaderLUT<U extends Enum<U> & Uniform> {
    private final U[] uniforms;
    private final int shaderProgram;
    private final HashMap<Uniform, Integer> locations;

    ShaderLUT(Class<U> enumClass, int shaderProgram) {
        this.uniforms = enumClass.getEnumConstants();
        this.locations = new HashMap<>(uniforms.length + EngineUniform.values().length);
        this.shaderProgram = shaderProgram;
        initializeLocations();
    }

    public int getLocation(U uniform) {
        return locations.get(uniform);
    }

    public int getLocation(EngineUniform uniform) {
        return locations.get(uniform);
    }

    private void initializeLocations() {
        for (U uniform : uniforms) {
            addUniformToLUT(uniform);
        }
        for (EngineUniform uniform : EngineUniform.values()) {
            addUniformToLUT(uniform);
        }
    }

    private void addUniformToLUT(Uniform uniform) {
        int location = glGetUniformLocation(shaderProgram, uniform.getUniformName());
        locations.put(uniform, location);
    }
}

package demo_game.Uniforms;

import doctrina.Uniform.Uniform;

public enum CubeUniform implements Uniform {
    ;

    private final String name;

    CubeUniform(String name) {
        this.name = name;
    }

    @Override
    public String getUniformName() {
        return name;
    }
}

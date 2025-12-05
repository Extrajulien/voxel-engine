package demo_game.Uniforms;

import doctrina.Uniform.Uniform;

public enum PlayerUniform implements Uniform {
    ;

    private final String name;

    PlayerUniform(String name) {
        this.name = name;
    }

    @Override
    public String getUniformName() {
        return name;
    }
}

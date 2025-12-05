package demo_game.Uniforms;

import doctrina.Uniform.Uniform;

public enum ChunkUniform implements Uniform {
    LINE_COLOR("line_color");

    private final String name;
    ChunkUniform(String name) {
        this.name = name;
    }

    @Override
    public String getUniformName() {
        return name;
    }
}

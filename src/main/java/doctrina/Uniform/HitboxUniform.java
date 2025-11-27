package doctrina.Uniform;

public enum HitboxUniform implements Uniform {
    HITBOX_COLOR("hitboxColor");

    final String uniformName;

    HitboxUniform(String name) {
        uniformName = name;
    }

    @Override
    public String getUniformName() {
        return uniformName;
    }
}

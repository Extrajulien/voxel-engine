package doctrina.Uniform;

public enum EngineUniform implements Uniform {
    MODEL("model"),
    VIEW("view"),
    PROJECTION("projection");

    private final String name;

    EngineUniform(String name) {
        this.name = name;
    }
    @Override
    public String getUniformName() {
        return name;
    }
}

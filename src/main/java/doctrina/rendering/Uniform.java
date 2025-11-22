package doctrina.rendering;

/**
 * the Uniform Interface define the required functions to play with the Uniforms
 *
 */
public interface Uniform {
    public String getUniformName();
    public int getUniformLocation();
    public void loadPositionLUT(Shader shader);
}

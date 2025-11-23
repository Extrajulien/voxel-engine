package doctrina.debug;

import org.joml.Vector3f;

public enum Color {
    RED       (new Vector3f(255f,   0f,   0f)),
    GREEN     (new Vector3f(  0f, 255f,   0f)),
    BLUE      (new Vector3f(  0f,   0f, 255f)),
    YELLOW    (new Vector3f(255f, 255f,   0f)),
    CYAN      (new Vector3f(  0f, 255f, 255f)),
    MAGENTA   (new Vector3f(255f,   0f, 255f)),
    WHITE     (new Vector3f(255f, 255f, 255f)),
    BLACK     (new Vector3f(  0f,   0f,   0f)),
    ORANGE    (new Vector3f(255f, 165f,   0f)),
    PURPLE    (new Vector3f(128f,   0f, 128f)),
    GRAY      (new Vector3f(128f, 128f, 128f)),
    BROWN     (new Vector3f(165f,  42f,  42f));

    final Vector3f color;
    private final static int colorRange = 255;

    Color(Vector3f color) {
        this.color = color;
    }

    public Vector3f getValue() {
        return color.div(colorRange);
    }
}

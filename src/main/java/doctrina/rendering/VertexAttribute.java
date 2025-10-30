package doctrina.rendering;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;


/**
 * List of all the vertex Attributes<br>
 * When using in shader layout should match the order of vertexAttributes
 */
public enum VertexAttribute {
    POSITION(3),
    TEXCOORD(2);

    // Start from 1!
    private final int size;

    VertexAttribute(int size) {
        this.size = size;
    }

    public void enable() {
        glVertexAttribPointer(getIndex(), size, GL_FLOAT, false, getStride(), getStartPosition());
        glEnableVertexAttribArray(getIndex());
    }

    public static void enableAll() {
        for (VertexAttribute attr : VertexAttribute.values()) {
            attr.enable();
        }
    }

    private static int getStride() {
        int stride = 0;
        for (VertexAttribute attr : VertexAttribute.values()) {
            stride += attr.size;
        }
        return stride * Float.BYTES;
    }

    private int getStartPosition() {
        int position = 0;
        for (VertexAttribute attr : VertexAttribute.values()) {
            if (attr == this) {
                break;
            }
            position += attr.size;
        }
        return position * Float.BYTES;
    }

    private int getIndex() {
        return this.ordinal();
    }

}

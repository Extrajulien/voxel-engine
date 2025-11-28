package doctrina.rendering;

import org.joml.Matrix4f;

import java.util.Objects;

public record CameraView(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
    public CameraView {
        Objects.requireNonNull(viewMatrix);
        Objects.requireNonNull(projectionMatrix);
    }
}

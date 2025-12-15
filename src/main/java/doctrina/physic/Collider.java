package doctrina.physic;

import doctrina.Utils.BoundingBox;
import org.joml.Vector3d;
import org.joml.Vector3f;


public class Collider {

    private final AxisSweptAABB speedXBox;
    private final AxisSweptAABB speedYBox;
    private final AxisSweptAABB speedZBox;

    private final BoundingBox ownerBoundingBox;


    public Collider(BoundingBox originalSize) {
        ownerBoundingBox = originalSize;
        speedXBox = new AxisSweptAABB(Axis.X, originalSize);
        speedYBox = new AxisSweptAABB(Axis.Y, originalSize);
        speedZBox = new AxisSweptAABB(Axis.Z, originalSize);
    }


    public Vector3f getAllowedX(Vector3f inputSpeed, CollisionCandidates collisionCandidates) {
        speedXBox.refreshRangeFromSpeed(ownerBoundingBox, inputSpeed);
        for (BoundingBox boundingBox : collisionCandidates) {
            if (speedXBox.isIntersecting(boundingBox) && isYInRange(boundingBox) && isZInRange(boundingBox)) {
                speedXBox.updatePossibleSpeed(boundingBox);
            }
        }
        return new Vector3f((float) speedXBox.getPossibleSpeed(), 0, 0);
    }

    public Vector3f getAllowedY(Vector3f inputSpeed, CollisionCandidates collisionCandidates) {
        speedYBox.refreshRangeFromSpeed(ownerBoundingBox, inputSpeed);
        for (BoundingBox boundingBox : collisionCandidates) {
            if (speedYBox.isIntersecting(boundingBox) && isXInRange(boundingBox) && isZInRange(boundingBox)) {
                speedYBox.updatePossibleSpeed(boundingBox);
            }
        }
        return new Vector3f(0, (float) speedYBox.getPossibleSpeed(), 0);
    }

    public Vector3f getAllowedZ(Vector3f inputSpeed, CollisionCandidates collisionCandidates) {
        speedZBox.refreshRangeFromSpeed(ownerBoundingBox, inputSpeed);
        for (BoundingBox boundingBox : collisionCandidates) {
            if (speedZBox.isIntersecting(boundingBox) && isXInRange(boundingBox) && isYInRange(boundingBox)) {
                speedZBox.updatePossibleSpeed(boundingBox);
            }
        }
        return new Vector3f(0, 0, (float) speedZBox.getPossibleSpeed());
    }



    private boolean isYInRange(BoundingBox other) {
        return !(ownerBoundingBox.maxY() < other.minY() || ownerBoundingBox.minY() > other.maxY());
    }

    private boolean isXInRange(BoundingBox other) {
        return !(ownerBoundingBox.maxX() < other.minX() || ownerBoundingBox.minX() > other.maxX());
    }

    private boolean isZInRange(BoundingBox other) {
        return !(ownerBoundingBox.maxZ() < other.minZ() || ownerBoundingBox.minZ() > other.maxZ());
    }


}



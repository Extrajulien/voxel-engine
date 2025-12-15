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

    public Vector3d collide(Vector3f inputSpeed, CollisionCandidates collisionCandidates) {
        updateSpeedBox(inputSpeed);
        for (BoundingBox boundingBox : collisionCandidates) {
            checkCollisionDirection(boundingBox);
        }
        return new Vector3d(speedXBox.getPossibleSpeed(), speedYBox.getPossibleSpeed(), speedZBox.getPossibleSpeed());
    }


    private void checkCollisionDirection(BoundingBox other) {
        if (speedXBox.isIntersecting(other) && speedYBox.isIntersecting(other) && speedZBox.isIntersecting(other)) {
            speedXBox.updatePossibleSpeed(other);
            speedYBox.updatePossibleSpeed(other);
            speedZBox.updatePossibleSpeed(other);
        }
    }



    private AxisSweptAABB getSpeedBox(MovementDir dir) {
        return switch (dir.getAxis()) {
            case X -> speedXBox;
            case Y -> speedYBox;
            case Z -> speedZBox;
        };
    }


    private MovementDir getDirectionOnAxis(Vector3f speed, Axis axis) {
        return switch (axis) {
            case X -> speed.x > 0 ? MovementDir.EAST : speed.x < 0 ? MovementDir.WEST : null;
            case Y -> speed.y > 0 ? MovementDir.UP : speed.y < 0 ? MovementDir.DOWN : null;
            case Z -> speed.z > 0 ? MovementDir.SOUTH : speed.z < 0 ? MovementDir.NORTH : null;
        };
    }




    private void updateSpeedBox(Vector3f currentSpeed) {
        speedXBox.refreshRangeFromSpeed(ownerBoundingBox, currentSpeed);
        speedYBox.refreshRangeFromSpeed(ownerBoundingBox, currentSpeed);
        speedZBox.refreshRangeFromSpeed(ownerBoundingBox, currentSpeed);
    }


}



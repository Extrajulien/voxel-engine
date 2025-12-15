package doctrina.physic;

import doctrina.Utils.BoundingBox;
import org.joml.Vector3f;

public class AxisSweptAABB {
    private final Axis axis;

    private double min;
    private double max;

    private boolean isSpeedSignPositive;

    public AxisSweptAABB(Axis axis, BoundingBox base) {
        this.axis = axis;
        isSpeedSignPositive = true;
    }


    public void refreshRangeFromSpeed(BoundingBox boundingBox, Vector3f speed) {
        if (speed.get(axis.getValue()) == 0) {
            min = 0;
            max = 0;
            return;
        }
        if (speed.get(axis.getValue()) > 0) {
            isSpeedSignPositive = true;
            min = boundingBox.getAxisRange(axis).max();
            max = boundingBox.getAxisRange(axis).max() + speed.get(axis.getValue());
            return;
        }
        if (speed.get(axis.getValue()) < 0) {
            isSpeedSignPositive = false;
            min = boundingBox.getAxisRange(axis).min() + speed.get(axis.getValue());
            max = boundingBox.getAxisRange(axis).min();
        }
    }

    public boolean isIntersecting(BoundingBox other) {
        if (isSpeedSignPositive) {
            return (other.getAxisRange(axis).min() < max);
        }
        return (other.getAxisRange(axis).max() > min);
    }

    public void updatePossibleSpeed(BoundingBox other) {
        if (min == max) {
            return;
        }
        if (isSpeedSignPositive) {
            if (other.getAxisRange(axis).min() < max) {
                max = other.getAxisRange(axis).min();
            }
            return;
        }
        if (other.getAxisRange(axis).max() > min) {
            min = other.getAxisRange(axis).max();
        }
    }


    public double getPossibleSpeed() {
        if (isSpeedSignPositive) {
            return max - min;
        }
        return min - max;
    }



}

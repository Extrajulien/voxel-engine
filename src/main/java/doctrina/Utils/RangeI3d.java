package doctrina.Utils;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;

import java.util.Iterator;

/**
 * Represent 3 Ranges, <b>All ranges are Inclusive<b/>
 */
public record RangeI3d(RangeI1d rangeX, RangeI1d rangeY, RangeI1d rangeZ) implements Iterable<Vector3i> {

    public boolean isPointInRange(Vector3i num) {
        return isPointInRange(num.x, num.y, num.z);
    }

    public boolean isPointInRange(int x, int y, int z) {
        return rangeX.isNumberInRange(x) && rangeY.isNumberInRange(y)
                && rangeZ.isNumberInRange(z);
    }

    public boolean isIntersectingRange(RangeI3d other) {
        return rangeX.isIntersectingRange(other.rangeX) || rangeY.isIntersectingRange(other.rangeY)
                || rangeZ.isIntersectingRange(other.rangeZ);
    }

    public long getMaxY() {
        return rangeY.getHigherThreshold();
    }

    public long getMinY() {
        return rangeY.getLowerThreshold();
    }

    public long getMaxX() {
        return rangeX.getHigherThreshold();
    }

    public long getMinX() {
        return rangeX.getLowerThreshold();
    }

    public long getMaxZ() {
        return rangeZ.getHigherThreshold();
    }

    public long getMinZ() {
        return rangeZ.getLowerThreshold();
    }

    public Vector3i getCenter() {
        return new Vector3i(
                (int)(rangeX.getHigherThreshold() - rangeX.getLowerThreshold()),
                (int)(rangeY.getHigherThreshold() - rangeY.getLowerThreshold()),
                (int)(rangeZ.getHigherThreshold() - rangeZ.getLowerThreshold())
        );
    }

    @NotNull
    @Override
    public Iterator<Vector3i> iterator() {
        return new Iterator<>() {
            int curX = (int) rangeX.getLowerThreshold();
            int curY = (int) rangeY.getLowerThreshold();
            int curZ = (int) rangeZ.getLowerThreshold();

            @Override
            public boolean hasNext() {
                return curX <= rangeX.getHigherThreshold();
            }

            @Override
            public Vector3i next() {
                Vector3i current = new Vector3i(curX, curY, curZ);

                ++curZ;
                if (curZ > rangeZ.getHigherThreshold()) {
                    curZ = (int) rangeZ.getLowerThreshold();
                    ++curY;
                    if (curY > rangeY.getHigherThreshold()) {
                        curY = (int) rangeY.getLowerThreshold();
                        ++curX;
                    }
                }
                return current;
            }
        };
    }
}

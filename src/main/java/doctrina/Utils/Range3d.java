package doctrina.Utils;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Iterator;

/**
 * Represent 3 Ranges, <b>All ranges are Inclusive<b/>
 */
public class Range3d implements Iterable<Vector3i> {

    private final Range1d rangeX;
    private final Range1d rangeY;
    private final Range1d rangeZ;

    public Range3d(Range1d rangeX, Range1d rangeY, Range1d rangeZ) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
    }

    public boolean isPointInRange(Vector3i num) {
        return rangeX.isNumberInRange(num.x) && rangeY.isNumberInRange(num.y)
                && rangeZ.isNumberInRange(num.z);
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

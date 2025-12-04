package doctrina.Utils;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import java.util.Iterator;

public class Range2d implements Iterable<Vector2i> {

    private final Range1d rangeX;
    private final Range1d rangeY;

    public Range2d(Range1d rangeX, Range1d rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }

    public boolean isPointInRange(Vector2i num) {
        return rangeX.isNumberInRange(num.x) && rangeY.isNumberInRange(num.y);
    }

    public Range1d getRangeX() {
        return rangeX;
    }

    public Range1d getRangeY() {
        return rangeY;
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

    @NotNull
    @Override
    public Iterator<Vector2i> iterator() {
        return new Iterator<>() {
            int curX = (int) rangeX.getLowerThreshold();
            int curY = (int) rangeY.getLowerThreshold();

            @Override
            public boolean hasNext() {
                return curX <= rangeX.getHigherThreshold();
            }

            @Override
            public Vector2i next() {
                Vector2i current = new Vector2i(curX, curY);

                ++curY;
                if (curY > rangeY.getHigherThreshold()) {
                    curY = (int) rangeY.getLowerThreshold();
                    ++curX;
                }
                return current;
            }
        };
    }
}
package doctrina.Utils;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import java.util.Iterator;

public class RangeI2d implements Iterable<Vector2i> {

    private final RangeI1d rangeX;
    private final RangeI1d rangeY;

    public RangeI2d(RangeI1d rangeX, RangeI1d rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }

    public boolean isPointInRange(Vector2i num) {
        return rangeX.isNumberInRange(num.x) && rangeY.isNumberInRange(num.y);
    }

    public RangeI1d getRangeX() {
        return rangeX;
    }

    public RangeI1d getRangeY() {
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
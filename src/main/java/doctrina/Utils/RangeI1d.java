package doctrina.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * Represent two numbers that are threshold, <b>Both are Inclusive<b/>
 */
public class RangeI1d implements Iterable<Long> {
    private final long low;
    private final long high;

    public RangeI1d(long lowerThreshold, long higherThreshold) {
        low = lowerThreshold;
        high = higherThreshold;
    }

    public long getSize() {
        return high - low;
    }

    public boolean isNumberInRange(long num) {
        return num >= low && num <= high;
    }

    public boolean hasRange(RangeI1d other) {
        return low <= other.low && high >= other.high;
    }

    public boolean isIntersectingRange(RangeI1d other) {
        return !(low > other.high || high < other.low);
    }

    /**
     * Is the range completely above the other
     */
    public boolean isGreater(RangeI1d other) {
        return low > other.high;
    }

    /**
     * Is the range completely under the other
     */
    public boolean isLower(RangeI1d other) {
        return high < other.low;
    }

    public long getLowerThreshold() {
        return low;
    }

    public long getHigherThreshold() {
        return high;
    }

    @NotNull
    @Override
    public Iterator<Long> iterator() {
        return new Iterator<>() {
            long current = low;

            @Override
            public boolean hasNext() {
                return current <= high;
            }

            @Override
            public Long next() {
                return current++;
            }
        };
    }
}

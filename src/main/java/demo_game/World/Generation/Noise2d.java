package demo_game.World.Generation;

import doctrina.Utils.Range1d;
import doctrina.Utils.Range2d;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class Noise2d implements Iterable<Integer> {
    private final Range2d range;
    private final int[][] values;
    private final Range1d valueRanges;

    public Noise2d(int[][] values) {
        range = new Range2d(new Range1d(0, values.length-1), new Range1d(0, values[0].length-1));
        this.values = values;
        valueRanges = createValueRanges();
    }

    public Range2d getEntryRange() {
        return range;
    }

    public Range1d getValuesRange() {
        return valueRanges;
    }

    public int[][] getValues() {
        return values;
    }



    private Range1d createValueRanges() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : this) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }
        return new Range1d(min, max);
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            int x = 0;
            int y = 0;

            @Override
            public boolean hasNext() {
                return x <= range.getMaxX();
            }

            @Override
            public Integer next() {
                int value = values[x][y];

                ++y;
                if (y > range.getMaxY()) {
                    y = (int) range.getMinY();
                    ++x;
                }
                return value;
            }
        };
    }


}

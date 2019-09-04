package game.menaphos.looting.model;

import java.util.Random;

public class Range {

    private final int min;
    private final int max;

    public static int getIntFromRange(Range range) {
        final Random random = new Random();
        return ((random.nextInt((range.getMax() - range.getMin())) + range.getMin()) > 0) ? (random.nextInt((range.getMax() - range.getMin())) + range.getMin()) : 1;
    }

    public static boolean within(int value, Range range) {
        return (value >= range.getMin() && value <= range.getMax());
    }

    public static boolean within(double value, Range range) {
        return (value >= range.getMin() && value <= range.getMax());
    }

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}

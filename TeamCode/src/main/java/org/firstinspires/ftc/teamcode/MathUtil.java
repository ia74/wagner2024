package org.firstinspires.ftc.teamcode;


public class MathUtil {
    public static class Position {
        public double low;
        public double high;
        public Position(double low, double high) {
            this.low = low;
            this.high = high;
        }
    }
    public enum RangeState {
        BELOW_MINIMUM,
        WITHIN_RANGE,
        ABOVE_MAXIMUM
    }
    public static RangeState isInRange(double current, double minimum, double maximum) {
        if (current < minimum) {
            return RangeState.BELOW_MINIMUM;
        } else if (current > maximum) {
            return RangeState.ABOVE_MAXIMUM;
        } else {
            return RangeState.WITHIN_RANGE;
        }
    }
}

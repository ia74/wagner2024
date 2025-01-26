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
    public static boolean isInRange(double a, double b, double range) {
        return b - range <= a && a <= b + range;
    }
}

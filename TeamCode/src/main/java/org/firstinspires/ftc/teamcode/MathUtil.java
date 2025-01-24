package org.firstinspires.ftc.teamcode;


public class MathUtil {
    public static boolean isInRange(double a, double b, double range) {
        return b - range <= a && a <= b + range;
    }
}

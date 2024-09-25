package org.firstinspires.ftc.teamcode.wagner;

import androidx.annotation.NonNull;

public enum PartsMap {
    CLAW("claw"),
    WRIST("wrist"),

    ARM_LEFT("armLeft"),
    ARM_RIGHT("armRight"),
    ARM_ELBOW("elbow"),
    ARM_SHOULDER("shoulder"),

    DRIVE_FL("leftFront"),
    DRIVE_FR("rightFront"),
    DRIVE_BL("leftBack"),
    DRIVE_BR("leftRight"),

    ODO_PARALLEL("par"),
    ODO_PERPENDICULAR("perp"),

    IMU("imu"),

    HANGER("hanger");

    public final String mapped;
    private PartsMap(String mapped) {
        this.mapped = mapped;
    }

    public String getMapped() {
        return mapped;
    }
    @NonNull
    public String toString() {return mapped;}
}

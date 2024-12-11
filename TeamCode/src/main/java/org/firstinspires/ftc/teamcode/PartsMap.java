package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

public enum PartsMap {
    CLAW("claw"),
    WRIST("wrist"),
    LIGHT_BLINKIN("blinkin"),

    ARM_LEFT("armLeft"),
    ARM_RIGHT("armRight"),
    ARM_ELBOW("elbow"),
    ARM_SHOULDER("shoulder"),

    DRIVE_FL("leftFront"),
    DRIVE_FR("rightFront"),
    DRIVE_BL("leftBack"),
    DRIVE_BR("leftRight"),

    IMU("imu"),

    HANGER("hanger"),
    HANGER_HOOK("hanger");

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

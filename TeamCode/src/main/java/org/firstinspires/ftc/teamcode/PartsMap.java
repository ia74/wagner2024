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

    INTAKE("intake"),

    HANG_WINCH("winch"),
    HANG_DEPLOYER_LEFT("depc_l"),
    HANG_DEPLOYER_RIGHT("depc_r"),

    DRIVE_FL("leftFront"),
    DRIVE_FR("rightFront"),
    DRIVE_BL("leftBack"),
    DRIVE_BR("leftRight"),

    IMU("imu"),

    HANGER("hanger"),
    HANGER_HOOK("hanger");

    public final String hardwareMapDefinition;

    PartsMap(String hardwareMapDefinition) {
        this.hardwareMapDefinition = hardwareMapDefinition;
    }

    @NonNull
    public String toString() {
        return hardwareMapDefinition;
    }
}

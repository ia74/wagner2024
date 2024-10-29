package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
public interface Mechanism {
    void init(HardwareMap hardwareMap);
    @NonNull
    String toString();

    static String motorIfo(DcMotor motor, String title) {
        return title + ":\n" +
                "\tName: " + motor.getDeviceName() + "\n" +
                "\tCPosition: " + motor.getCurrentPosition() + "\n" +
                "\tTPosition: " + motor.getTargetPosition() + "\n" +
                "\tPower: " + motor.getPower() + "\n" +
                "\tBusy: " + motor.isBusy() + "\n";
    }
    static String servoIfo(Servo motor, String title) {
        return title + ":\n" +
                "\tName: " + motor.getDeviceName() + "\n" +
                "\tCPosition: " + motor.getPosition() + "\n" +
                "\tDirection: " + motor.getDirection() + "\n";
    }
    static String servoIfo(CRServo motor, String title) {
        return title + ":\n" +
                "\tName: " + motor.getDeviceName() + "\n" +
                "\tPower: " + motor.getPower() + "\n";
    }
}

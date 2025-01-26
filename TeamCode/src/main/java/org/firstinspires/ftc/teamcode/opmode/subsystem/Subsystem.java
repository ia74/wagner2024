package org.firstinspires.ftc.teamcode.opmode.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PartsMap;
import org.firstinspires.ftc.teamcode.opmode.subsystem.pid.PIDFController;

public class Subsystem {
    HardwareMap hardwareMap;
    public Subsystem(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }
    public <T> T getHardware(Class<? extends T> classOrInterface, PartsMap deviceName) {
        return hardwareMap.get(classOrInterface, deviceName.toString());
    }
    static void resetMotor(DcMotor motor) {
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    static void initializeMotors(DcMotor... motors) {
        for(DcMotor i : motors) {
            Subsystem.resetMotor(i);
            i.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
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

    static String pidControllerIfo(PIDFController pidfController, String title, String powers) {
        return title + ":\n" +
                pidfController.toString() + "\n" +
                "\tPower: " + powers;
    }

    static PIDFController pidfController(double kP, double kI, double kD, double kF) {
        return new PIDFController(
                kP, kI, kD, kF
        );
    }
}

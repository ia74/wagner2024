package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
public class Claw implements Mechanism {
    CRServo servo;
    public static int power = 0;

    @Override
    public void init(HardwareMap hardwareMap) {
        servo = hardwareMap.get(CRServo.class, "claw");
    }

    public void open() {
        servo.setPower(1);
    }

    public void stop() {
        servo.setPower(0);
    }

    @Override
    public void run(Gamepad gamepad1, Gamepad gamepad2, MecanumDrive drive, Telemetry telemetry) {
        if(gamepad1.a) power = 1;
        else power = 0;
        servo.setPower(power);
    }
}

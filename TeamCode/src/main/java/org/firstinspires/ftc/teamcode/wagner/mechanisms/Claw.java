package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Claw implements Mechanism {
    Servo servo;

    @Override
    public void init(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, "servo");
    }

    @Override
    public void run(Gamepad gamepad1, Gamepad gamepad2, MecanumDrive drive) {
        if(gamepad1.a) servo.setPosition(1);
        if(gamepad1.b) servo.setPosition(-1);
    }
}

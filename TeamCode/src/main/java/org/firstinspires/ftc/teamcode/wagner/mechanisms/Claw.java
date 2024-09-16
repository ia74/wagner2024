package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Claw implements Mechanism {
    DcMotor leftArm;
    DcMotor rightArm;
    CRServo claw;

    @Override
    public void init(HardwareMap hardwareMap) {
        claw = hardwareMap.get(CRServo.class, PartsMap.CLAW_SERVO.toString());
        leftArm = hardwareMap.get(DcMotor.class, PartsMap.CLAW_ARM_LEFT.toString());
        rightArm = hardwareMap.get(DcMotor.class, PartsMap.CLAW_ARM_RIGHT.toString());
    }

    public void openClaw(double power) {
        claw.setPower(power);
    }

    @Override
    public void run(Gamepad gamepad1, Gamepad gamepad2, MecanumDrive drive, Telemetry telemetry) {
        if(gamepad1.a) openClaw(0.4);
        else openClaw(0);


    }
}

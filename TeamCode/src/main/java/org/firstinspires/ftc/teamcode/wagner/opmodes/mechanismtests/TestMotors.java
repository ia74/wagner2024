package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;

public class TestMotors extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive robot = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        waitForStart();

        while(opModeIsActive()) {
            DO(gamepad1.square, robot.leftFront);
            DO(gamepad1.triangle, robot.rightFront);
            DO(gamepad1.cross, robot.leftBack);
            DO(gamepad1.circle, robot.rightBack);
            telemetry.addData("FrontLeft", get(robot.leftFront));
            telemetry.addData("FrontRight", get(robot.rightFront));
            telemetry.addData("BackLeft", get(robot.leftBack));
            telemetry.addData("BackRight", get(robot.rightBack));
            telemetry.update();
        }
    }

    public String get(DcMotor mot) {
        return mot.getDirection() + " : " + mot.getCurrentPosition();
    }

    public void DO(boolean trigger, DcMotor mot) {
        if(trigger) mot.setPower(1);
        else mot.setPower(0);
    }
}

package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;

public class TestMotors extends LinearOpMode {
    int power = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive robot = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.right_trigger > 0.2) power = -1;
            else power = 1;
            DO(gamepad1.square, robot.leftFront);
            DO(gamepad1.triangle, robot.rightFront);
            DO(gamepad1.cross, robot.leftBack);
            DO(gamepad1.circle, robot.rightBack);
            telemetry.addData("Front Left", get(robot.leftFront));
            telemetry.addData("Front Right", get(robot.rightFront));
            telemetry.addData("Back Left", get(robot.leftBack));
            telemetry.addData("Back Right", get(robot.rightBack));
            telemetry.addData("Power", power);
            telemetry.update();
        }
    }

    public String get(DcMotor mot) {
        return mot.getDirection() + " : " + mot.getCurrentPosition();
    }

    public void DO(boolean trigger, DcMotor mot) {
        if(trigger) mot.setPower(power);
        else mot.setPower(0);
    }
}

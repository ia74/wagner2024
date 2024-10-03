package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.PartsMap;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;

public class TestMotors extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_FL.toString());
        DcMotor frontRight = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_FR.toString());
        DcMotor backLeft = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_BL.toString());
        DcMotor backRight = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_BR.toString());

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Using", "Gamepad 1");
            telemetry.addData("Square", "Front L eft");
            telemetry.addData("Cross", "Back Left");
            telemetry.addData("Triangle", "Front Right");
            telemetry.addData("Circle", "Back Right");
            telemetry.addLine();
            telemetry.addData("FrontLeft", "Port 0");
            telemetry.addData("FrontRight", "Port 1");
            telemetry.addData("BackLeft", "Port 2");
            telemetry.addData("BackRight", "Port 3");
            telemetry.update();

            motor(gamepad1.square, frontLeft);
            motor(gamepad1.cross, backLeft);
            motor(gamepad1.triangle, frontRight);
            motor(gamepad1.circle, backRight);
        }
    }
    void motor(boolean gamepad, DcMotor motor) {
        if(gamepad) motor.setPower(1);
        else motor.setPower(0);
    }
}

package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Lights;

@Config
public class TestMotorsInch extends LinearOpMode {
    public static int finches = 24;
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive robot = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        Arm arm = new Arm();
        Claw claw = new Claw();
        Lights lights = new Lights();
        lights.init(hardwareMap);
        arm.init(hardwareMap);
        claw.init(hardwareMap);
        lights.breathRed();
        waitForStart();
        robot.leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        reset(robot);

        while(positionAverage(robot) * MecanumDrive.PARAMS.inPerTick < finches) {
            power(robot, 1);
        }
        power(robot,0);
    }

    public static double positionAverage(MecanumDrive robot) {
        return (double) (robot.leftFront.getCurrentPosition() + robot.rightFront.getCurrentPosition() + robot.leftBack.getCurrentPosition() + robot.rightBack.getCurrentPosition()) / 4;
    }
    public static void power(MecanumDrive rbt, double pwr) {
        rbt.leftBack.setPower(pwr);
        rbt.rightBack.setPower(pwr);
        rbt.rightFront.setPower(pwr);
        rbt.leftFront.setPower(pwr);
    }
    public static void reset(MecanumDrive rbt) {
        rbt.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rbt.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rbt.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rbt.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rbt.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rbt.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rbt.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rbt.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepad;

public class TestArm extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Arm arm = new Arm();
        arm.init(hardwareMap);

        waitForStart();

        telemetry.addData("Please wait", "Testing arm");
        telemetry.addData("Arm: Elbow", "Testing elbow");
        telemetry.update();

        sleep(500);
        arm.setElbow(0.5);
        test(1, arm.elbow.getPosition() == 0.5, telemetry);
        telemetry.addData("Arm: Shoulder", "Elbow should be at 0.5");
        telemetry.update();

        sleep(500);
        arm.setElbow(0);
        test(2, arm.elbow.getPosition() == 0, telemetry);
        telemetry.addData("Arm: Shoulder", "Elbow should be at 0");
        telemetry.update();

        sleep(500);
        arm.setElbow(1);
        test(2, arm.elbow.getPosition() == 1, telemetry);

        telemetry.addData("Arm: Shoulder", "Elbow should be at 1");
        telemetry.addData("Arm: Shoulder", "Testing shoulder");
        telemetry.update();

        sleep(500);

        arm.setShoulder(0.5);
        test(1, arm.shoulder.getPosition() == 0.5, telemetry);
        telemetry.addData("Arm: Shoulder", "Shoulder should be at 0.5");
        telemetry.update();

        sleep(500);
        arm.setShoulder(0);
        test(2, arm.shoulder.getPosition() == 0, telemetry);
        telemetry.addData("Arm: Shoulder", "Shoulder should be at 0");
        telemetry.update();

        sleep(500);
        arm.setShoulder(1);
        test(2, arm.shoulder.getPosition() == 1, telemetry);
        telemetry.addData("Arm: Shoulder", "Shoulder shoud be at 1");

        telemetry.addData("Tests", "Finished");
        telemetry.update();
        sleep(1500);

            arm.setShoulder(0);
            arm.setElbow(0);

        while(opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Shoulder", arm.shoulder.getPosition());
            telemetry.addData("Elbow", arm.elbow.getPosition());
            telemetry.update();

            arm.setElbow(gamepad1.left_stick_x);
            arm.setShoulder(gamepad1.right_stick_y);
        }
    }
    public void test(int c, boolean f, Telemetry t) {
        t.addData("Test " + c, f ? "Pass" : "Fail");
        t.update();
    }
}

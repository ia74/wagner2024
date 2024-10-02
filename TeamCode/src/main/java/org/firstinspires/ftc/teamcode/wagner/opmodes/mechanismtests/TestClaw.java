package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepad;

public class TestClaw extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Claw claw = new Claw();
        Arm arm = new Arm();
        claw.init(hardwareMap);
        arm.init(hardwareMap);

        waitForStart();

        telemetry.addData("Please wait", "Testing claw");
        telemetry.update();

        claw.openClaw(0);
        test(1, claw.claw.getPosition() == 0, telemetry);
        telemetry.addData("Claw should be at", 0);
        telemetry.update();
        sleep(1000);

        claw.openClaw(1);
        test(2, claw.claw.getPosition() == 1, telemetry);

        telemetry.addData("Claw should be at", 1);
        telemetry.update();
        sleep(1000);

        claw.openClaw(0.5);
        test(3, claw.claw.getPosition() == 0.5, telemetry);
        telemetry.addData("Claw should be at", 0.5);
        telemetry.update();
        sleep(1000);

        claw.openClaw(-1);
        test(1, claw.claw.getPosition() == 0, telemetry);
        telemetry.addData("Claw should be at", -1);
        telemetry.update();

        sleep(1000);
        claw.openClaw(-0.5);
        telemetry.addData("Claw should be at", -1);
        test(2, claw.claw.getPosition() == 1, telemetry);
        telemetry.update();

        sleep(1000);

        claw.openClaw(0);
        telemetry.addData("Claw should be at", 0.5);
        telemetry.update();
        test(3, claw.claw.getPosition() == 0.5, telemetry);

        sleep(1000);
        telemetry.addData("Claw should be at", Claw.clawOpenPosition);
        telemetry.update();

        claw.openClaw(Claw.clawOpenPosition);
        test(4, claw.claw.getPosition() == Claw.clawOpenPosition, telemetry);

        sleep(1000);

        claw.setWrist(0);
        test(5, claw.wrist.getPosition() == 0, telemetry);

        telemetry.addData("Wrist should be at", 0);
        telemetry.update();

        sleep(1000);

        claw.setWrist(0.5);
        test(5, claw.wrist.getPosition() == 0.5, telemetry);
        telemetry.addData("Wrist should be at", 0.5);
        telemetry.update();


        sleep(1000);

        claw.setWrist(-1);
        test(5, claw.wrist.getPosition() == -1, telemetry);

        telemetry.addData("Wrist should be at", -1);
        telemetry.update();


        sleep(1000);

        claw.setWrist(1);
        test(5, claw.wrist.getPosition() == 1, telemetry);
        telemetry.addData("Wrist should be at", 1);
        telemetry.addData("Tests", "Finished");
        telemetry.update();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Wrist", claw.wrist.getPosition());
            telemetry.addData("Claw", claw.claw.getPosition());
            claw.setWrist(gamepad1.right_stick_y);
            claw.openClaw(gamepad1.left_stick_x);
            arm.setShoulder(gamepad2.right_stick_y);
            telemetry.update();
        }
    }
    public void test(int c, boolean f, Telemetry t) {
        t.addData("Test " + c, f ? "Pass" : "Fail");
        t.update();
    }
}

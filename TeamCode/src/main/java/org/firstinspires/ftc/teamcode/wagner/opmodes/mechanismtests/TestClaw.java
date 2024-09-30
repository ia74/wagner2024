package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepad;

public class TestClaw extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Claw claw = new Claw();
        claw.init(hardwareMap);

        waitForStart();

        telemetry.addData("Please wait", "Testing claw");
        telemetry.update();

        claw.openClaw(0);
        test(1, claw.claw.getPosition() == 0, telemetry);

        sleep(500);

        claw.openClaw(1);
        test(2, claw.claw.getPosition() == 1, telemetry);

        sleep(500);

        claw.openClaw(0.5);
        test(3, claw.claw.getPosition() == 0.5, telemetry);

        claw.openClaw(-1);
        test(1, claw.claw.getPosition() == 0, telemetry);

        sleep(500);

        claw.openClaw(-0.5);
        test(2, claw.claw.getPosition() == 1, telemetry);

        sleep(500);

        claw.openClaw(0);
        test(3, claw.claw.getPosition() == 0.5, telemetry);

        sleep(500);

        claw.openClaw(Claw.clawOpenPosition);
        test(4, claw.claw.getPosition() == Claw.clawOpenPosition, telemetry);

        sleep(500);

        claw.setWrist(0);
        test(5, claw.wrist.getPosition() == 0, telemetry);

        sleep(500);

        claw.setWrist(0.5);
        test(5, claw.wrist.getPosition() == 0.5, telemetry);

        sleep(500);

        claw.setWrist(-1);
        test(5, claw.wrist.getPosition() == -1, telemetry);

        sleep(500);

        claw.setWrist(1);
        test(5, claw.wrist.getPosition() == 1, telemetry);

        telemetry.addData("Tests", "Finished");
        telemetry.update();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Wrist", claw.wrist.getPosition());
            telemetry.addData("Claw", claw.claw.getPosition());

            telemetry.update();
        }
    }
    public void test(int c, boolean f, Telemetry t) {
        t.addData("Test " + c, f ? "Pass" : "Fail");
        t.update();
    }
}

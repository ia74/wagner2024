package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

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
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        NGGamepad ng_gamepad1 = new NGGamepad(gamepad1);
        Claw claw = new Claw();
        claw.init(hardwareMap);

        waitForStart();

        telemetry.addData("Please wait", "Testing claw");

        claw.openClaw(0);
        test(1, claw.claw.getPosition() == 0, telemetry);

        claw.openClaw(1);
        test(2, claw.claw.getPosition() == 1, telemetry);

        claw.openClaw(0.5);
        test(3, claw.claw.getPosition() == 0.5, telemetry);

        claw.openClaw(Claw.clawOpenPosition);
        test(4, claw.claw.getPosition() == Claw.clawOpenPosition, telemetry);


        while(opModeIsActive() && !isStopRequested()) {
            PoseVelocity2d driveInput = ng_gamepad1.driveWithThis();
            drive.setDrivePowers(driveInput);
        }
    }
    public void test(int c, boolean f, Telemetry t) {
        t.addData("Test " + c, f ? "Pass" : "Fail");
    }
}

package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

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
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        NGGamepad ng_gamepad1 = new NGGamepad(gamepad1);
        Arm arm = new Arm();
        arm.init(hardwareMap);

        waitForStart();

        telemetry.addData("Please wait", "Testing arm");
        telemetry.addData("Arm: Elbow", "Testing elbow");
        telemetry.update();

        arm.setElbow(0.5);
        test(1, arm.elbow.getPosition() == 0.5, telemetry);

        arm.setElbow(0);
        test(2, arm.elbow.getPosition() == 0, telemetry);

        arm.setElbow(1);
        test(2, arm.elbow.getPosition() == 1, telemetry);

        telemetry.addData("Arm: Shoulder", "Testing shoulder");
        telemetry.update();

        arm.setShoulder(0.5);
        test(1, arm.shoulder.getPosition() == 0.5, telemetry);

        arm.setShoulder(0);
        test(2, arm.shoulder.getPosition() == 0, telemetry);

        arm.setShoulder(1);
        test(2, arm.shoulder.getPosition() == 1, telemetry);

        telemetry.addData("Tests", "Finished");
        telemetry.update();
        while(opModeIsActive() && !isStopRequested()) {
            PoseVelocity2d driveInput = ng_gamepad1.driveWithThis();
            drive.setDrivePowers(driveInput);
        }
    }
    public void test(int c, boolean f, Telemetry t) {
        t.addData("Test " + c, f ? "Pass" : "Fail");
        t.update();
    }
}

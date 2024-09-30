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

        arm.setElbow(0.5);
        sleep(500);
        test(1, arm.elbow.getPosition() == 0.5, telemetry);

        arm.setElbow(0);
        sleep(500);
        test(2, arm.elbow.getPosition() == 0, telemetry);

        arm.setElbow(1);
        sleep(500);
        test(2, arm.elbow.getPosition() == 1, telemetry);

        telemetry.addData("Arm: Shoulder", "Testing shoulder");
        telemetry.update();

        arm.setShoulder(0.5);
        sleep(500);
        test(1, arm.shoulder.getPosition() == 0.5, telemetry);

        arm.setShoulder(0);
        sleep(500);
        test(2, arm.shoulder.getPosition() == 0, telemetry);

        arm.setShoulder(1);
        sleep(500);
        test(2, arm.shoulder.getPosition() == 1, telemetry);

        telemetry.addData("Tests", "Finished");
        telemetry.update();
        sleep(1500);

        while(opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Shoulder", arm.shoulder.getPosition());
            telemetry.addData("Elbow", arm.elbow.getPosition());
            telemetry.update();
        }
    }
    public void test(int c, boolean f, Telemetry t) {
        t.addData("Test " + c, f ? "Pass" : "Fail");
        t.update();
    }
}

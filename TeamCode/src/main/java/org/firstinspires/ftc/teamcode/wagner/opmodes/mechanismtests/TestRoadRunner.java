package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepad;

public class TestRoadRunner extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        waitForStart();

        telemetry.addData("Please wait", "Attempting to self-drive.");
        telemetry.update();
        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                        .turn(Math.toRadians(90))
                        .build()
        );
        test("1", inRange(drive.pose.heading.toDouble(), 89, 91), telemetry);
        test("1a (NOT STRICT)", inRange(drive.pose.heading.toDouble(), 85, 93), telemetry);
        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                        .turn(Math.toRadians(90))
                        .build()
        );
    }

    public void test(String c, boolean f, Telemetry t) {
        t.addData("Test " + c, f ? "Pass" : "Fail");
        t.update();
    }

    public boolean inRange (double num, double low, double up) {
        return num > low && num < up;
    }
}

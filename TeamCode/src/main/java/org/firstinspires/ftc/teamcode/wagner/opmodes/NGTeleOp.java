package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

@TeleOp(name = "New Gen TeleOp", group = "estoy")
public class NGTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        waitForStart();

        PoseVelocity2d input = new PoseVelocity2d(
                new Vector2d(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x
                ),
                drive.pose.heading.toDouble()
        );

        drive.setDrivePowers(input);
    }
}

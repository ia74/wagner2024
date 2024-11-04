package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;

@Autonomous(name = "Simple Auto", group="!!Autonomous")
public class SimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        GlobalStorage.pose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);
        waitForStart();

        Actions.runBlocking(
            drive.actionBuilder(GlobalStorage.pose)
                 .lineToX(70)
                 .build()
        );

    }
}

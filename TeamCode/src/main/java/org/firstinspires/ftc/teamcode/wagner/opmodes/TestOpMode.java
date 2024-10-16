package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

//@Autonomous(name = "Test OpMode")
public class TestOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);
        waitForStart();

        Action action = drive.actionBuilder(drive.pose)
                             .lineToY(5)
                             .turn(360)
                             .build();
        Actions.runBlocking(action);

    }
}

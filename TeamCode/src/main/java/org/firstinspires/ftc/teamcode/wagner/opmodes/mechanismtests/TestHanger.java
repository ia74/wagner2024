package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;

public class TestHanger extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Hanger hanger = new Hanger();
        hanger.init(hardwareMap);

        waitForStart();

        
    }
}

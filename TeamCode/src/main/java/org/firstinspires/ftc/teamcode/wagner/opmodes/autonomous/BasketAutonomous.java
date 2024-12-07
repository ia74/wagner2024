package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;

@Config
public class BasketAutonomous extends LinearOpMode {
    Claw claw = new Claw();
    Arm arm = new Arm();
    MecanumDrive drive;
    int timeForSlideNew = 2500;
    public Pose2d startPosition() {
        return new Pose2d(0, 0, Math.toRadians(0));
    }
    public Pose2d parkPosition() {
        return new Pose2d(0, 0, Math.toRadians(0));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        GlobalStorage.pose = startPosition();
        drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        arm.init(hardwareMap);
        claw.init(hardwareMap);

        onInitialize();

        waitForStart();
        onStart();
    }

    public void onInitialize() {

    }
    public void onStart() {

    }

    void grabFromBelow() {
        claw.down();
        claw.fullDown();
        claw.open();
        sleep(700);
        claw.close();
        claw.fullClose();
        sleep(200);
        claw.up();
        claw.close();
    }

    void score() {
        arm.slidePower(1);
        sleep(timeForSlideNew);
        arm.slidePower(0);
        claw.down();
        sleep(250);
        claw.open();
        sleep(700);
        claw.close();
        claw.up();
        sleep(150);
        arm.slidePower(-1);
        sleep(timeForSlideNew);
        arm.slidePower(0);
    }
}

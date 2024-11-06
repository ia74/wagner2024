package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;

public class BasketAutonomous extends LinearOpMode {
    Claw claw = new Claw();
    Arm arm = new Arm();
    MecanumDrive drive;
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
        claw.open();
        sleep(700);
        claw.close();
        claw.up();
    }

    void score() {
        arm.slidePower(1);
        sleep(2500);
        arm.slidePower(0);
        claw.down();
        claw.open();
        sleep(600);
        claw.close();
        claw.up();
        arm.slidePower(-1);
        sleep(2000);
        arm.slidePower(0);
    }
}

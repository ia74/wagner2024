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

@Autonomous(name = "Basket Blue", group="!!Autonomous")
public class BasketBlue extends LinearOpMode {
    Claw claw = new Claw();
    Arm arm = new Arm();
    @Override
    public void runOpMode() throws InterruptedException {
        GlobalStorage.pose = new Pose2d(36, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);
        arm.init(hardwareMap);
        claw.init(hardwareMap);

        waitForStart();
        claw.close();
        claw.up();
        Actions.runBlocking(
            drive.actionBuilder(GlobalStorage.pose)
                 .lineToY(55)
                 .splineToLinearHeading( new Pose2d(54, 56, Math.toRadians(45)), 0.0 )
                 .stopAndAdd(this::score)
                 .strafeToLinearHeading(new Vector2d(-48, 38), Math.toRadians(270))
                 .stopAndAdd(() -> {
                     claw.down();
                     claw.open();
                     sleep(150);
                     claw.close();
                     claw.up();
                 })
                 .strafeToLinearHeading( new Vector2d(54, 56), Math.toRadians(45))
                 .stopAndAdd(this::score)
                 .build()
        );

    }

    void score() {
        arm.slidePower(1);
        sleep(2000);
        arm.slidePower(0);
        claw.down();
        claw.open();
        sleep(250);
        claw.close();
        claw.up();
        arm.slidePower(-1);
        sleep(2000);
        arm.slidePower(0);
    }
}

package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

@Autonomous(name = "Basket Blue", group="!!Autonomous")
@Disabled
@Config
public class BasketBlue extends BasketAutonomous {
    public static double basketPosX = 85;
    public static double basketPosY = 73;
    public static double basketPosHead = 45;

    public static double floorPosX = 50;
    public static double floorPosY = 35;
    public static double floorPosHaed = -90;

    public static Pose2d basketPosition = new Pose2d(basketPosX, basketPosY, Math.toRadians(basketPosHead));
    public static Pose2d startPosition = new Pose2d(35.5, 61.5, Math.toRadians(-90));
    public static int timeForShoulder = 3000;
    public static int timeForSlideNewDosGood = 500;

    @Override
    public void onStart() {
        basketPosition = new Pose2d(basketPosX, basketPosY, Math.toRadians(basketPosHead));
        claw.close();
        claw.up();
        Actions.runBlocking(
            drive.actionBuilder(startPosition)
                    .lineToY(55)
                    .splineToLinearHeading(basketPosition, 0.0)
                    .stopAndAdd(this::score)
                    .splineToLinearHeading(new Pose2d(floorPosX, floorPosY, Math.toRadians(floorPosHaed)),0)
                    .stopAndAdd(this::grab)
                    .splineToLinearHeading(basketPosition, 0.0)
                    .stopAndAdd(this::score)
                        .build()
        );
    }
    void score() {
        arm.slidePower(1);
        sleep(timeForShoulder);
        arm.slidePower(0);
        claw.down();
        sleep(250);
        claw.open();
        sleep(700);
        claw.close();
        claw.up();
        sleep(150);
        arm.slidePower(-1);
        sleep(timeForShoulder);
        arm.slidePower(0);
    }
    void grab() {
        claw.down();
        arm.setShoulder(1);
        sleep(timeForSlideNewDosGood);
        arm.setShoulder(0);
        claw.close();
        sleep(150);
        arm.setShoulder(-1);
        sleep(timeForSlideNewDosGood);
        arm.setShoulder(0);
        claw.up();
    }
}

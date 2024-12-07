package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Drawing;

@Autonomous(name = "Basket Blue Test", group="!!Autonomous")
@Disabled
@Config
public class BasketBlueHelper extends BasketAutonomous {
    public static int basketPosX = 85;
    public static int basketPosY = 73;
    public static double basketPosHead = 45;

    public static int floorPosX = 50;
    public static int floorPosY = 35;
    public static double floorPosHaed = -90;

    public static Pose2d basketPosition = new Pose2d(basketPosX, basketPosY, Math.toRadians(basketPosHead));
    public static Pose2d startPosition = new Pose2d(35.5, 61.5, Math.toRadians(-90));
    public static int timeForShoulder = 3000;
    public static int timeForSlideNewDosGood = 500;
    enum HState {
        BASKET,
        FLOOR
    };
    HState state = HState.BASKET;
    @Override
    public void onStart() {
        state = HState.BASKET;
        basketPosition = new Pose2d(basketPosX, basketPosY, Math.toRadians(basketPosHead));
        while(opModeIsActive()) {
            boolean press = gamepad1.a;
            if(state == HState.BASKET && press) {
                BasketBlue.basketPosX = drive.pose.position.x;
                BasketBlue.basketPosY = drive.pose.position.y;
                BasketBlue.basketPosHead = drive.pose.heading.real;
                state = HState.FLOOR;
            } else if(state == HState.FLOOR && press) {
                BasketBlue.floorPosX = drive.pose.position.x;
                BasketBlue.floorPosY = drive.pose.position.y;
                BasketBlue.floorPosHaed = drive.pose.heading.real;
            }
            telemetry.addLine("State: " + state);
            telemetry.addLine("Press Gamepad1 A to record into BasketBlue.");

            telemetry.update();
            drive.updatePoseEstimate();
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }
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

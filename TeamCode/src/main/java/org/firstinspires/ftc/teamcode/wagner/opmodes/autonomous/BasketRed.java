package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

@Autonomous(name = "Basket Red", group="!!Autonomous")
public class BasketRed extends BasketAutonomous {
    Pose2d basketPosition = new Pose2d(54, 53, Math.toRadians(45));
    @Override
    public Pose2d startPosition() {
        return new Pose2d(13, -60, Math.toRadians(90));
    }
    @Override
    public void onStart() {
        claw.close();
        claw.up();
        Actions.runBlocking(
            drive.actionBuilder(GlobalStorage.pose)
                .lineToY(55)
                .strafeToLinearHeading(basketPosition.position, basketPosition.heading)
                .stopAndAdd(this::score)
                .strafeToLinearHeading(new Vector2d(48, -37), Math.toRadians(-270))
                .stopAndAdd(this::grabFromBelow)
                .waitSeconds(0.7)
                .strafeToLinearHeading(basketPosition.position, basketPosition.heading)
                .stopAndAdd(this::score)
                .build()
        );
    }
}

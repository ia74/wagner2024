package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

@Autonomous(name = "Basket Red", group="!!Autonomous")
public class BasketRed extends BasketAutonomous {
    Pose2d basketPosition = new Pose2d(-53, -54, Math.toRadians(225));
    @Override
    public Pose2d startPosition() {
        return new Pose2d(-35.5, -61.5, Math.toRadians(90));
    }
    @Override
    public void onStart() {
        claw.close();
        claw.up();
        Actions.runBlocking(
                drive.actionBuilder(startPosition())
                        .strafeToLinearHeading(basketPosition.position, basketPosition.heading)
                        .stopAndAdd(this::score)
                        .turnTo(Math.toRadians(90))

                        .strafeTo(new Vector2d( 58, -60))
                        .build()
        );
        this.score();
    }
}

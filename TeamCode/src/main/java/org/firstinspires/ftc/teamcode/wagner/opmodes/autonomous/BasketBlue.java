package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

@Autonomous(name = "Basket Blue", group="!!Autonomous")
public class BasketBlue extends BasketAutonomous {
    Pose2d basketPosition = new Pose2d(52, 53, Math.toRadians(45));
    @Override
    public Pose2d startPosition() {
        return new Pose2d(35.5, 61.5, Math.toRadians(-90));
    }
    @Override
    public void onStart() {
        claw.close();
        claw.up();
        Actions.runBlocking(
            drive.actionBuilder(startPosition())
                    .lineToY(55)
                    .splineToLinearHeading(basketPosition, 0.0)
                    .stopAndAdd(this::score)
                    .strafeToLinearHeading(new Vector2d( -60, 58), Math.toRadians(0))
                        .build()
        );
        this.score();
    }
}

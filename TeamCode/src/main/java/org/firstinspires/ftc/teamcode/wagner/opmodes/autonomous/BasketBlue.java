package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

@Autonomous(name = "Basket Blue", group="!!Autonomous")
public class BasketBlue extends BasketAutonomous {
    Pose2d basketPosition = new Pose2d(53, 48, Math.toRadians(45));
    @Override
    public Pose2d startPosition() {
        return new Pose2d(36, 60, Math.toRadians(-90));
    }
    @Override
    public void onStart() {
        claw.close();
        claw.up();
        Actions.runBlocking(
            drive.actionBuilder(startPosition())
                .lineToY(55)
                .splineToLinearHeading(basketPosition, 0.0)
                    .build());
        this.score();
        drive.updatePoseEstimate();
        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(-57.5, 36), Math.toRadians(270))
                        .build());
        drive.updatePoseEstimate();
        this.grabFromBelow();
        sleep(700);
        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                        .lineToY(basketPosition.position.y - 10)
                        .strafeTo(basketPosition.position)
                        .turnTo(basketPosition.heading)
                        .build()
        );
        this.score();
    }
}

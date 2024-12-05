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
                    .splineToLinearHeading(new Pose2d(47, 41, Math.toRadians(-90)),0)
                    .stopAndAdd(this::grab)
                    .splineToLinearHeading(basketPosition, 0.0)
                    .stopAndAdd(this::score)
                        .build()
        );
        this.score();
    }
    int timeForShoulder = 250;
    void grab() {
        arm.setShoulder(1);
        sleep(timeForShoulder);
        arm.setShoulder(0);
        claw.close();
        sleep(15);
        arm.setShoulder(-1);
        sleep(timeForShoulder);
        arm.setShoulder(0);
    }
}

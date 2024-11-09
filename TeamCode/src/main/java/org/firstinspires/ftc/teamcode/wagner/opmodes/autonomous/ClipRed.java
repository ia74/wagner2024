package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "Clip Red", group="!!Autonomous")
public class ClipRed extends BasketAutonomous {
    Pose2d barPosition = new Pose2d(-3, -33.0, Math.toRadians(90));
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
                        .strafeToLinearHeading(barPosition.position, barPosition.heading)
                        .stopAndAdd(() -> {
                            arm.slidePower(1);
                            sleep(1000);
                            arm.slidePower(0);
                            claw.down();
                            sleep(850);
                            claw.open();
                        })
                        .strafeTo(new Vector2d( 58, -60))
                        .build()
        );
        this.score();
    }
}

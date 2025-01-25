package org.firstinspires.ftc.teamcode.opmode.autonomous;

import static org.firstinspires.ftc.teamcode.opmode.autonomous.Clip.State.GOTO_FIRST_CLIP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.GlobalStorage;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

@Config
@Autonomous(name="0+0 SIMPLE AUTO / Miguel Antimaneuvering", group="!!! Auton")
public class Simple extends OpMode {

    // other is 1400
    public enum State {
        WEEE,
    }

    boolean ready = false;

    Timer pathTimer = new Timer();
    Timer opModeTimer = new Timer();
    Timer actionTimer = new Timer();
    Timer buildTimer = new Timer();

    long buildTime = 0;

    Follower follower;

    State state = State.WEEE;

    Pose startPose = new Pose(10.372881355932204, 60.55932203389831, Math.toRadians(0));
    Pose clipOne = new Pose(10.677966101694915, 11.7457627118644, Math.toRadians(0)); // x 125 -> 124

    PathChain runStartToClipOne;


    public void buildPaths() {
        ready = false;
        buildTimer.resetTimer();
        GlobalStorage.currentPose = startPose;
        follower.setPose(startPose);

        runStartToClipOne = createConstantPathChainForTwoPoints(startPose, clipOne);

        buildTime = buildTimer.getElapsedTime();
        buildTimer = null;
        ready = true;
    }

    PathChain createConstantPathChainForTwoPoints(Pose point1, Pose point2) {
        return follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        new Point(point1), new Point(point2)
                )))
                .setConstantHeadingInterpolation(point2.getHeading())
                .build();
    }


    @Override
    public void init() {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new Follower(hardwareMap);

        buildPaths();
    }

    @Override
    public void init_loop() {
        telemetry.addLine(ready ? "Built paths in " + buildTime + "ms. Ready." : "Paths not built yet! If you see this, press Gamepad1 A to attempt to build the paths.");
        telemetry.update();
        if (gamepad1.a) buildPaths();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        actionTimer.resetTimer();
        follower.followPath(runStartToClipOne);
    }

    @Override
    public void loop() {
        follower.update();


        telemetry.addData("Auto State", state);
        telemetry.addLine();
        telemetry.addData("action time (s)", actionTimer.getElapsedTime());
        telemetry.addData("path time (s)", pathTimer.getElapsedTime());
        telemetry.addData("opmode time (s)", opModeTimer.getElapsedTime());
        telemetry.update();
    }

    @Override
    public void stop() {
        GlobalStorage.currentPose = follower.getPose();
    }

}

package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@Disabled
@Autonomous(name="Miguel Antimaneuvering - Clipperton", group="!!! Auton")
public class Clip extends OpMode {
    public static int scoringBasketRaisePos = 1020;
    // other is 1400
    public enum State {
        NOOP,
        INIT,
        GOTO_FIRST_CLIP,
        CLIP_RISE,
        CLIP_LOWER,
        GOTO_OBSERVATION,
        SECOND_CLIP,
        THIRD_CLIP
    }

    boolean ready = false;

    Timer pathTimer = new Timer();
    Timer opModeTimer = new Timer();
    Timer actionTimer = new Timer();
    Timer buildTimer = new Timer();

    long buildTime = 0;

    Follower follower;
    Arm arm;
    Claw claw;

    State state = State.INIT;
    int whichClip = 1;

    Pose startPose = new Pose(10.220338983050848, 60.40677966101695, Math.toRadians(0));
    Pose clipOne = new Pose(32.03389830508475, 78.25423728813558, Math.toRadians(0)); // x 125 -> 124
    Pose clipTwo = new Pose(32.03389830508475, 74.59322033898306, Math.toRadians(0)); // x 125 -> 124
    
    Pose observationZone = new Pose(10.677966101694915, 12.508474576271185, Math.toRadians(180));

    PathChain runStartToClipOne;
    PathChain runClipOneToObservationZone;

    PathChain runObservationZoneToClipTwo;
    PathChain runClipTwoToObservationZone;

    Pose scorePose;
    PathChain goingToObservation;

    public void buildPaths() {
        ready = false;
        buildTimer.resetTimer();
        GlobalStorage.currentPose = startPose;
        follower.setPose(startPose);

        runStartToClipOne = createPathChainForTwoPoints(startPose, clipOne);
        runClipOneToObservationZone = createConstantPathChainForTwoPoints(clipOne, observationZone);

        runObservationZoneToClipTwo = createPathChainForTwoPoints(observationZone, clipTwo);
        runClipTwoToObservationZone = createPathChainForTwoPoints(clipTwo, observationZone);

        buildTime = buildTimer.getElapsedTime();
        buildTimer = null;
        ready = true;
    }

    PathChain createPathChainForTwoPoints(Pose point1, Pose point2) {
        return follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        new Point(point1), new Point(point2)
                )))
                .setLinearHeadingInterpolation(point1.getHeading(), point2.getHeading())
                .build();
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
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);

        buildPaths();
    }

    @Override
    public void init_loop() {
        telemetry.addLine(ready ? "Built paths in " + buildTime +"ms. Ready." : "Paths not built yet! If you see this, press Gamepad1 A to attempt to build the paths.");
        telemetry.update();
        if(gamepad1.a) buildPaths();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        actionTimer.resetTimer();
        setState(State.INIT);
    }

    @Override
    public void loop() {
        follower.update();
        arm.update();
        updateAutonomousState();

        telemetry.addData("Auto State", state);
        telemetry.addLine();
        telemetry.addData("arm position", arm.getArmPosition());
        telemetry.addData("arm ticks away from basket score", scoringBasketRaisePos - arm.getArmPosition());
        telemetry.addData("action time (s)", actionTimer.getElapsedTime());
        telemetry.addData("path time (s)", pathTimer.getElapsedTime());
        telemetry.addData("opmode time (s)", opModeTimer.getElapsedTime());
        telemetry.update();
    }

    @Override
    public void stop() {
        GlobalStorage.currentPose = follower.getPose();
    }

    public void updateAutonomousState() {
        switch(state) {
            case NOOP:
                break;
            case INIT:
                claw.close();
                claw.up();
                setState(State.GOTO_FIRST_CLIP);
                break;
            case GOTO_FIRST_CLIP:
                follower.followPath(runStartToClipOne);
                scorePose = clipOne;
                setState(State.CLIP_RISE);
                break;
            case CLIP_RISE:
                // Raise the arm, this can happen while we're moving to the path to save time (~3 sec.)
                arm.setSlidesTargetPosition(scoringBasketRaisePos);
                if(arm.getArmPosition() >= scoringBasketRaisePos) {
                    actionTimer.resetTimer();
                    claw.middle();
                    setState(State.CLIP_LOWER); // This means, after this iteration we will not go back through this.
                }
                break;
            case CLIP_LOWER:
                if(isInRangeOf(scorePose)) {
                    arm.setShoulderTargetPosition(10);
                    claw.open();
                    if(whichClip == 0) {
                        goingToObservation = runClipOneToObservationZone;
                    } else {
                        goingToObservation = runClipTwoToObservationZone;
                    }
                    whichClip++;
                    follower.followPath(goingToObservation);
                    setState(State.GOTO_OBSERVATION);
                }
                break;
            case GOTO_OBSERVATION:
                if(isInRangeOf(observationZone)) {
                    claw.close();
                    if(whichClip == 1) {
                        follower.followPath(runObservationZoneToClipTwo);
                    }
                }
                break;
        }
    }

    public boolean isInRangeOf(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 2.85) &&
                follower.getPose().getY() > (pose.getY() - 2.85);
    }
    public boolean isCloseTo(double a, double b, double range) {
        return b - range <= a && a <= b + range;
    }

    public void setState(State state) {
        this.state = state;
        pathTimer.resetTimer();
    }
}

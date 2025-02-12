package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.GlobalStorage;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import com.pedropathing.util.Timer;

@Config
@Autonomous(name="2+0 Specimen / Miguel Antimaneuvering", group="!!! Auton")
public class Clippy extends OpMode {
    public static int clipBasketHeight = 1100;
    public static int clipBasketLowerScore = 700;
    public static int clipObservePickup = 0;
    public static int lowerSlides = 10;
    // other is 1400`Z
    public enum State {
        NOOP,
        INIT,
        GOTO_FIRST_CLIP,
        PUSH_INTO_OBSERVE,
        CLIPPING_RAISE_SLIDES,
        CLIPPING_MOVE_TO_SCORE_AND_SET_WRIST,
        CLIPPING_SCORE,
        CLIPPING_OPEN_CLAW,
        CLIPPING_GOTO_OBSERVATION,
        GOTO_OBSERVATION,
        SLIDES_RAISE_FOR_PICKUP,
        SLIDES_RAISE_THEN_WAIT,
        CLAW_UP_POST_PICKUP,
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
    Lights lights;

    State state = State.INIT;
    int whichClip = 0;

    Pose startPose = new Pose(10.220338983050848, 63.37724550898203, Math.toRadians(0));
    Pose clipOne = new Pose(33.5, 74.51790900290416, Math.toRadians(0)); // x 125 -> 124

    Pose observationZone = new Pose(17.5, 11.065868263473059, Math.toRadians(180));

    PathChain runStartToClipOne;
    PathChain runClipOneToObservationZone;

    PathChain runObservationZoneToClipTwo;
    PathChain runClipTwoToObservationZone;

    PathChain pushClipsToHuman;

    Pose scorePose;
    PathChain goingToObservation;

    public void buildPaths() {
        ready = false;
        buildTimer.resetTimer();
        GlobalStorage.currentPose = startPose;
        follower.setPose(startPose);

        runStartToClipOne = createPathChainForTwoPoints(startPose, clipOne);
        runClipOneToObservationZone = createConstantPathChainForTwoPoints(clipOne, observationZone);

        runObservationZoneToClipTwo = createPathChainForTwoPoints(observationZone, clipOne);
        runClipTwoToObservationZone = createPathChainForTwoPoints(clipOne, observationZone);

        pushClipsToHuman = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(clipOne),
                                new Point(29.832, 36.440, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(29.832, 36.440, Point.CARTESIAN),
                                new Point(69.701, 40.671, Point.CARTESIAN),
                                new Point(59.066, 28.743, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(59.066, 28.743, Point.CARTESIAN),
                                new Point(20.551, 25.293, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 4
                        new BezierCurve(
                                new Point(20.551, 25.293, Point.CARTESIAN),
                                new Point(69.844, 40.096, Point.CARTESIAN),
                                new Point(59.641, 16.527, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(59.641, 16.527, Point.CARTESIAN),
                                new Point(observationZone)
                        )
                )
                .setConstantHeadingInterpolation(observationZone.getHeading())
                .build();

        buildTime = buildTimer.getElapsedTime();
        buildTimer = null;
        ready = true;
    }

    PathChain createPathChainForTwoPoints(Pose point1, Pose point2) {
        return follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        new Point(point1), new Point(point2)
                )))
                .setLinearHeadingInterpolation(point1.getHeading(), point2.getHeading())
                .build();
    }

    PathChain createConstantPathChainForTwoPoints(Pose point1, Pose point2) {
        return follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        new Point(point1), new Point(point2)
                )))
                .setConstantHeadingInterpolation(point2.getHeading())
                .build();
    }


    @Override
    public void init() {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        lights = new Lights(hardwareMap);

        buildPaths();
        claw.setClawState(Claw.ClawState.CLOSED);
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        claw.setClawState(Claw.ClawState.CLOSED);
        claw.setWristState(Claw.WristState.UP);
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

        telemetry.addData("state", state);
        telemetry.addData("whichClip", whichClip);
        telemetry.addData("path", follower.getCurrentPathNumber());
        telemetry.addData("current action time (s)", actionTimer.getElapsedTime());
        telemetry.addData("path time (s)", pathTimer.getElapsedTime());
        telemetry.addData("opmode time (s)", opModeTimer.getElapsedTime());
        telemetry.addData("Claw rizz pos", claw.wrist.getPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        GlobalStorage.currentPose = follower.getPose();
    }

    boolean shouldStage = false;
    public void updateAutonomousState() {
        switch(state) {
            case NOOP:
                break;
            case INIT:
                setState(State.GOTO_FIRST_CLIP);
                break;
            case GOTO_FIRST_CLIP:
                follower.followPath(runStartToClipOne, true);
                scorePose = clipOne;
                actionTimer.resetTimer();
                setState(State.CLIPPING_RAISE_SLIDES);
                break;
            case CLIPPING_RAISE_SLIDES:
                // Raise the arm, this can happen while we're moving to the path to save time (~3 sec.)
                arm.setSlidesTargetPosition(clipBasketHeight);
                if(arm.isPositionWithinTolerance(arm.getArmPosition(), 20, clipBasketHeight)
                        && actionTimer.getElapsedTime() > (whichClip == 0 ? 500 : 2000)) {
                    actionTimer.resetTimer();
                    setState(State.CLIPPING_MOVE_TO_SCORE_AND_SET_WRIST); // This means, after this iteration we will not go back through this.
                }
                break;
            case CLIPPING_MOVE_TO_SCORE_AND_SET_WRIST:
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_RAINBOW_PALETTE);
                if(isLikeClose(scorePose)) claw.setWristState(Claw.WristState.MIDDLE);
                if(!follower.isBusy()) {
                    actionTimer.resetTimer();
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
                    setState(State.CLIPPING_SCORE);
                }
                break;
            case CLIPPING_SCORE:
                if(actionTimer.getElapsedTime() > 750) {
                    arm.setSlidesTargetPosition(clipBasketLowerScore);
                }
                if(arm.isPositionWithinTolerance(arm.getArmPosition(), 20, clipBasketLowerScore)) {
                    actionTimer.resetTimer();
                    setState(State.CLIPPING_OPEN_CLAW);
                }
                break;
            case CLIPPING_OPEN_CLAW:
                claw.setClawState(Claw.ClawState.OPEN);
                claw.claw.setPosition(Claw.clawOpenPosition);
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                if(actionTimer.getElapsedTime() > 1000) {
                    actionTimer.resetTimer();
                    setState(State.CLIPPING_GOTO_OBSERVATION);
                }
                break;
            case CLIPPING_GOTO_OBSERVATION:
                if(whichClip == 0) {
                    setState(State.PUSH_INTO_OBSERVE);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_LIGHT_CHASE);
                    break;
                } else {
                    goingToObservation = runClipTwoToObservationZone;
                }
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
                whichClip++;
                follower.followPath(goingToObservation, true);
                actionTimer.resetTimer();
                setState(State.GOTO_OBSERVATION);
                break;
            case PUSH_INTO_OBSERVE:
                claw.setClawState(Claw.ClawState.OPEN);
                claw.setWristState(Claw.WristState.UP);
                follower.followPath(pushClipsToHuman, true);
                setState(State.GOTO_OBSERVATION);
                break;
            case GOTO_OBSERVATION:
                if(!follower.isBusy()) {
                    arm.setSlidesTargetPosition(clipObservePickup);
                    setState(State.SLIDES_RAISE_FOR_PICKUP);
                }
                break;
            case SLIDES_RAISE_FOR_PICKUP:
                if(arm.isPositionWithinTolerance(arm.getArmPosition(), 20, clipObservePickup)) {
                    claw.setWristState(Claw.WristState.MIDDLE);
                    claw.wrist.setPosition(Claw.wristMiddlePosition - 0.04);
                    actionTimer.resetTimer();
                    setState(State.SLIDES_RAISE_THEN_WAIT);
                }
                break;
            case SLIDES_RAISE_THEN_WAIT:
                if(actionTimer.getElapsedTime() > 300) {
                    claw.setClawState(Claw.ClawState.CLOSED);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
                    actionTimer.resetTimer();
                    setState(State.CLAW_UP_POST_PICKUP);
                }
                break;
            case CLAW_UP_POST_PICKUP:
                if(actionTimer.getElapsedTime() > 1400) {
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_RAINBOW_PALETTE);
                    if(whichClip == 0) {
                        whichClip++;
                        goingToObservation = runObservationZoneToClipTwo;
                        scorePose = clipOne;
                    }
                    arm.setSlidesTargetPosition(clipObservePickup + 200);
                    follower.followPath(goingToObservation, true);
                    actionTimer.resetTimer();
                    setState(State.CLIPPING_RAISE_SLIDES);
                }
                break;
        }
    }

    public boolean isInRangeOf(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 1) &&
                follower.getPose().getY() > (pose.getY() - 1);
    }

    public boolean isLikeClose(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 4) &&
                follower.getPose().getY() > (pose.getY() - 4);
    }
    public void setState(State state) {
        this.state = state;
        pathTimer.resetTimer();
    }
}

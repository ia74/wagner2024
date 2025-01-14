package org.firstinspires.ftc.teamcode.opmode.autonomous;

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
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

@Config
@Autonomous(name="Miguel Antimaneuvering - Clipperton", group="!!! Auton")
public class Clippy extends OpMode {
    public static int clipBasketHeight = 1300;
    public static int clipBasketLowerScore = 1000;
    public static int clipObservePickup = 0;
    public static int lowerSlides = 10;
    // other is 1400
    public enum State {
        NOOP,
        INIT,
        GOTO_FIRST_CLIP,
        PUSH_INTO_OBSERVE,
        AWAIT_PUSH_FINISH,
        SCORING_CLIP_RAISE_SLIDES,
        SCORING_POST_AWAIT_CLAW_OPEN,
        SCORING_CLIP_LOWER_SLIDES_AND_OPEN_CLAW,
        SCORING_POST_AWAIT_TO_MOVE_NEXT,
        GOTO_OBSERVATION,
        WAIT,
        SLIDES_RAISE_FOR_PICKUP,
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

    State state = State.INIT;
    int whichClip = 0;

    Pose startPose = new Pose(10.220338983050848, 60.40677966101695, Math.toRadians(0));
    Pose clipOne = new Pose(33.31655372700871, 65.51790900290416, Math.toRadians(0)); // x 125 -> 124
    Pose clipTwo = new Pose(36.08483896307934, 74.31893165750196, Math.toRadians(0)); // x 125 -> 124

    Pose observationZone = new Pose(10.677966101694915, 12.508474576271185, Math.toRadians(180));

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

        runObservationZoneToClipTwo = createPathChainForTwoPoints(observationZone, clipTwo);
        runClipTwoToObservationZone = createPathChainForTwoPoints(clipTwo, observationZone);

        pushClipsToHuman = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(clipOne),
                                new Point(29.832, 36.244, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(29.832, 36.244, Point.CARTESIAN),
                                new Point(60.639, 34.711, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(60.639, 34.711, Point.CARTESIAN),
                                new Point(63.009, 27.462, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(63.009, 27.462, Point.CARTESIAN),
                                new Point(17.704, 26.486, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(17.704, 26.486, Point.CARTESIAN),
                                new Point(63.009, 27.880, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(63.009, 27.880, Point.CARTESIAN),
                                new Point(60.221, 15.334, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 7
                        new BezierLine(
                                new Point(60.221, 15.334, Point.CARTESIAN),
                                new Point(18.122, 14.916, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 8
                        new BezierLine(
                                new Point(18.122, 14.916, Point.CARTESIAN),
                                new Point(60.639, 15.752, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 9
                        new BezierLine(
                                new Point(60.639, 15.752, Point.CARTESIAN),
                                new Point(61.057, 10.037, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 10
                        new BezierLine(
                                new Point(61.057, 10.037, Point.CARTESIAN),
                                new Point(16.589, 10.316, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180)).build();

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

        telemetry.addData("state", state);
        telemetry.addData("current action time (s)", actionTimer.getElapsedTime());
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
                follower.followPath(runStartToClipOne, true);
                scorePose = clipOne;
                setState(State.SCORING_CLIP_RAISE_SLIDES);
                break;
            case SCORING_CLIP_RAISE_SLIDES:
                // Raise the arm, this can happen while we're moving to the path to save time (~3 sec.)
                arm.setSlidesTargetPosition(clipBasketHeight);
                if(arm.getArmPosition() >= clipBasketHeight) {
                    actionTimer.resetTimer();
                    claw.middle();
                    setState(State.SCORING_CLIP_LOWER_SLIDES_AND_OPEN_CLAW); // This means, after this iteration we will not go back through this.
                }
                break;
            case SCORING_CLIP_LOWER_SLIDES_AND_OPEN_CLAW:
                if(isInRangeOf(scorePose) && actionTimer.getElapsedTime() > 100) {
                    arm.setSlidesTargetPosition(clipBasketLowerScore);
                    actionTimer.resetTimer();
                    setState(State.SCORING_POST_AWAIT_TO_MOVE_NEXT);
                }
                break;
            case SCORING_POST_AWAIT_TO_MOVE_NEXT:
                if(arm.getArmPosition() >= clipBasketLowerScore || actionTimer.getElapsedTime() > 1000) {
                    claw.open();
                    if(whichClip == 0) {
                        arm.setSlidesTargetPosition(lowerSlides);
                        setState(State.PUSH_INTO_OBSERVE);
                        break;
                    } else {
                        goingToObservation = runClipTwoToObservationZone;
                    }
                    whichClip++;
                    follower.followPath(goingToObservation, true);
                    setState(State.GOTO_OBSERVATION);
                }
                break;
            case PUSH_INTO_OBSERVE:
                if(arm.getArmPosition() >= lowerSlides) {
                    claw.up();
                    follower.followPath(pushClipsToHuman);
                    setState(State.WAIT);
                }
                break;
            case WAIT:
                if(!follower.isBusy()) {
                    whichClip++;
                    setState(State.GOTO_OBSERVATION);
                }
                break;
            case GOTO_OBSERVATION:
                if(isInRangeOf(observationZone)) {
                    arm.setSlidesTargetPosition(clipObservePickup);
                    setState(State.SLIDES_RAISE_FOR_PICKUP);
                }
                break;
            case SLIDES_RAISE_FOR_PICKUP:
                if(arm.getArmPosition() >= clipObservePickup) {
                    claw.close();
                    actionTimer.resetTimer();
                    setState(State.CLAW_UP_POST_PICKUP);
                }
                break;
            case CLAW_UP_POST_PICKUP:
                if(actionTimer.getElapsedTime() > 500 || claw.claw.getPosition() == Claw.clawClosedPosition) {
                    claw.up();
                    arm.setSlidesTargetPosition(lowerSlides);
                    if(whichClip == 1) {
                        goingToObservation = runObservationZoneToClipTwo;
                        scorePose = clipTwo;
                        follower.followPath(goingToObservation, true);
                        setState(State.SCORING_CLIP_RAISE_SLIDES);
                    }
                }
                break;
        }
    }

    public boolean isInRangeOf(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 1) &&
                follower.getPose().getY() > (pose.getY() - 1);
    }
    public boolean isCloseTo(double a, double b, double range) {
        return b - range <= a && a <= b + range;
    }

    public void setState(State state) {
        this.state = state;
        pathTimer.resetTimer();
    }
}

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
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

@Config
@Autonomous(name="Miguel Antimaneuvering - Preloaded Only", group="!!! Auton")
public class Preload extends OpMode {
    public static int scoringBasketRaisePos = 3550;
    // other is 1400
    public enum State {
        NOOP,
        INIT,
        START_TO_BASKET,
        BASKET_OBSERVE,
        RAISE_TO_BASKET,
        SCORE_BASKET,
        PULLOUT_BASKET,
        SCORE_OUT_BASKET,
        LOWER_ARM_BASKET,
        RUN_TO_FLOOR_PICKUP,
        WAIT_SLIDE_DOWN_FLOOR_PICKUP,
        FLOOR_PICKUP_WAIT_DOWN_AND_GOTO,
        REAL_FLOOR_PICKUP,
        RUN_TO_BASKET_FROM_FLOOR,
        PUSH_PIXEL_INTO_ZONE_GOTO,
        PUSH_PIXEL_INTO_ZONE,
        PARK_GOTO,
        PARK
    }

    // Relative to the robot facing the other side.
    /*******\ ( > is the robot direction)
     *>    *
    \*******/
    public enum FloorPickupState {
        LEFT,
        MIDDLE,
        RIGHT,
        NONE
    }

    boolean ready = false;
    boolean closedClawToPickupFromFloor = false;

    Timer pathTimer = new Timer();
    Timer opModeTimer = new Timer();
    Timer actionTimer = new Timer();
    Timer buildTimer = new Timer();

    long buildTime = 0;

    Follower follower;
    Lights lights;
    Arm arm;
    Claw claw;

    State state = State.INIT;

    Pose startPose = new Pose(107.5, 133.5, Math.toRadians(-90));
    Pose basketPosition = new Pose(124, 124, Math.toRadians(45)); // x 125 -> 124

    Pose grabFromFloorRight = new Pose(119, 111.5, Math.toRadians(-90));
    Pose grabFromFloorMiddle = new Pose(127.5, 111.5, Math.toRadians(-90)); //TODO: test this
    Pose grabFromFloorLeft = new Pose(133.7555110220441, 103.02204408817634, Math.toRadians(-180));

    Pose pushIntoZone = new Pose(133.03406813627254, 128.9939879759519, Math.toRadians(-180));
    Pose pushIntoZoneControlPoint = new Pose(135, 129, Math.toRadians(-180));
    Pose end = new Pose(96.19639278557113, 75.74148296593185, Math.toRadians(180));
    Pose observeZone = new Pose(30, 128, Math.toRadians(-90)); // x 125 -> 124

    PathChain runStartToBasket;

    PathChain runBasketToobserverZone;

    PathChain runBasketToRight;
    PathChain runRightToBasket;

    PathChain runBasketToMiddle;
    PathChain runMiddleToBasket;

    PathChain runBasketToLeft;
    PathChain runLeftToBasket;

    PathChain runLeftToZone;
    PathChain runZoneToPark;


    Pose pickingUpCurrentlyPose;
    PathChain pickingUpCurrentlyPath;
    FloorPickupState pickingUpCurrentlyState = FloorPickupState.RIGHT;

    public void buildPaths() {
        ready = false;
        buildTimer.resetTimer();
        GlobalStorage.currentPose = startPose;
        follower.setPose(startPose);

        runStartToBasket = createPathChainForTwoPoints(startPose, basketPosition);
        runBasketToobserverZone = createPathChainForTwoPoints(basketPosition, observeZone);

        // RIGHT -> BASKET , BASKET -> RIGHT
        runRightToBasket = createPathChainForTwoPoints(grabFromFloorRight, basketPosition);
        runBasketToRight = createPathChainForTwoPoints(basketPosition, grabFromFloorRight);

        // MIDDLE -> BASKET, BASKET ->  MIDDLE
        runMiddleToBasket = createPathChainForTwoPoints(grabFromFloorMiddle, basketPosition);
        runBasketToMiddle = createPathChainForTwoPoints(basketPosition, grabFromFloorMiddle);

        // LEFT -> BASKET, BASKET -> LEFT
        runLeftToBasket = createPathChainForTwoPoints(grabFromFloorLeft, basketPosition);
        runBasketToLeft = createPathChainForTwoPoints(basketPosition, grabFromFloorLeft);

        runLeftToZone = createConstantPathChainForTwoPoints(grabFromFloorLeft, pushIntoZone);
        runZoneToPark = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        new Point(pushIntoZone),
                        new Point(pushIntoZoneControlPoint),
                        new Point(end)
                )))
                .setLinearHeadingInterpolation(pushIntoZone.getHeading(), end.getHeading())
                .build();



        pickingUpCurrentlyPose = grabFromFloorRight;
        pickingUpCurrentlyPath = runBasketToRight;
        pickingUpCurrentlyState = FloorPickupState.RIGHT;
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
        lights = new Lights(hardwareMap);
        follower.setMaxPower(0.98);
        lights.breathRed();
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
        claw.close();
        claw.up();
        opModeTimer.resetTimer();
        actionTimer.resetTimer();
        setState(State.INIT);
    }

    @Override
    public void loop() {
        follower.update();
        arm.individuallyUpdateSlides();
        arm.individuallyUpdateShoulder();
        updateAutonomousState();

        telemetry.addLine(arm.toString());
        telemetry.addData("Floor Pickup State", pickingUpCurrentlyState);
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
            case BASKET_OBSERVE:
                follower.followPath(runBasketToobserverZone);
                setState(State.NOOP);
                break;
            case INIT:
                setState(State.START_TO_BASKET);
                break;
            case START_TO_BASKET:
                follower.followPath(runStartToBasket);
                pickingUpCurrentlyState = FloorPickupState.RIGHT;
                setState(State.RAISE_TO_BASKET);
                break;
            case RAISE_TO_BASKET:
                // Raise the arm, this can happen while we're moving to the path to save time (~3 sec.)
                arm.setSlidesTargetPosition(scoringBasketRaisePos);
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
                if(arm.getArmPosition() >= scoringBasketRaisePos) {
                    actionTimer.resetTimer();
                    claw.down(); // Since we're all the way up, we *should, in 99.9% cases* be able to lower the claw.
                    setState(State.SCORE_BASKET); // This means, after this iteration we will not go back through this.
                }
                break;
            case SCORE_BASKET:
                if(isInRangeOf(basketPosition)) {
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                    claw.open(); // Open the claw, as we're now raised high enough & lowered into the basket.
                    actionTimer.resetTimer(); // Start the action timer.
                    setState(State.SCORE_OUT_BASKET);
                }
                break;
            case SCORE_OUT_BASKET:
                if(actionTimer.getElapsedTime() > 750) {
                    // We've waited 500ms (half a second), so we'll raise the claw out of the bucket.
                    claw.up();
                }
                if(actionTimer.getElapsedTime() > 1200 && (claw.wrist.getPosition() == Claw.wristUpPosition || actionTimer.getElapsedTime() >2000)) {
                    // Extra wait time, so we don't grab onto the bucket & risk damaging claw/slides/etc..
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
                    follower.setMaxPower(0.6);
                    arm.setSlidesTargetPosition(10);
                    setState(State.BASKET_OBSERVE);
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

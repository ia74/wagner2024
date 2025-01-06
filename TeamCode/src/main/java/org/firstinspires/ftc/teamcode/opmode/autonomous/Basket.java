package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

import org.firstinspires.ftc.teamcode.opmode.autonomous.util.BasketAutoState;
import org.firstinspires.ftc.teamcode.opmode.autonomous.util.FloorSampleState;
import org.firstinspires.ftc.teamcode.opmode.autonomous.util.FollowerEx;
import org.firstinspires.ftc.teamcode.opmode.autonomous.util.PathChainHelper;
import org.firstinspires.ftc.teamcode.opmode.autonomous.util.PathChainHelperType;
import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.Claw;

/**
 * Welcome to the beautiful Basket autonomous.
 */
@Config
@Autonomous(name="Miguel Antimaneuvering - Basket", group="!!! Auton")
public class Basket extends OpMode {
    public static int scoringBasketRaisePos = 3500;

    boolean ready = false; // This defines if the drivers should press Start and the autonomous shouldn't break (due to pathing failure)
    boolean closedClawToPickupFromFloor = false; // This is used in a stage where we pick the sample up from the floor.

    Follower follower;
    Arm arm;
    Claw claw;
    PathChainHelper pathChainHelper;
    BasketAutoState state = BasketAutoState.INIT;

    Timer pathTimer = new Timer();
    Timer opModeTimer = new Timer();
    Timer actionTimer = new Timer();
    Timer pathBuildTimer = new Timer(); // Path build timer
    long buildTime = 0;

    /**
     * TODO: These coordinates are super incorrect (they're NOT aligned to the field properly, if you put it into a visualizer or the path generator). It works *now*, so I'm not changing it, but one day it will be fixed.
     */
    Pose startPose = new Pose(10.176185866408519, 108.31364956437561, Math.toRadians(0));

    Pose basketPosition = new Pose(16.867376573088094, 126.99322362052276, Math.toRadians(135));
    Pose basketPullout = new Pose(19.51597289448209, 123.78702807357213, Math.toRadians(135));

    Pose grabFromFloorRight = new Pose(34.431752178121975, 121.41723136495644, Math.toRadians(0));
    Pose grabFromFloorMiddle = new Pose(34.431752178121975, 131.31461761858662, Math.toRadians(0));
    Pose grabFromFloorLeft = new Pose(46.28073572120039, 134.24201355275895, Math.toRadians(-90));

    Pose pushIntoZone = new Pose(11.570183930300097, 134.24201355275895, Math.toRadians(-90));
    Pose end = new Pose(61.057115198451115, 97.99806389157794, Math.toRadians(-91));

    PathChain runStartToBasket, runBasketToPullout, runBasketToRight, runRightToBasket, runBasketToMiddle, runMiddleToBasket,
              runBasketToLeft, runLeftToBasket, runLeftToZone, runZoneToPark;

    Pose pickingUpCurrentlyPose;
    PathChain pickingUpCurrentlyPath;
    FloorSampleState pickingUpCurrentlyState = FloorSampleState.RIGHT;

    public void buildPaths() {
        ready = false;
        pathBuildTimer.resetTimer();
        FConstants.currentPose = startPose;
        follower.setPose(startPose);
        runStartToBasket = new PathChain(
                new Path(new BezierCurve(
                        new Point(10.176, 108.314, Point.CARTESIAN),
                        new Point(22.000, 126.000, Point.CARTESIAN),
                        new Point(16.867, 126.993, Point.CARTESIAN)
                ))
        );
        runStartToBasket.getPath(0).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(135));

//        runStartToBasket = pathChainHelper.fromTwoPoints(startPose, basketPosition, PathChainHelperType.LINEAR);
        runBasketToPullout = pathChainHelper.fromTwoPoints(basketPosition, basketPullout, PathChainHelperType.CONSTANT);

        // RIGHT -> BASKET , BASKET -> RIGHT
        runRightToBasket = pathChainHelper.fromTwoPoints(grabFromFloorRight, basketPosition, PathChainHelperType.LINEAR);
        runBasketToRight = pathChainHelper.fromTwoPoints(basketPosition, grabFromFloorRight, PathChainHelperType.LINEAR);

        // MIDDLE -> BASKET, BASKET ->  MIDDLE
        runMiddleToBasket = pathChainHelper.fromTwoPoints(grabFromFloorMiddle, basketPosition, PathChainHelperType.LINEAR);
        runBasketToMiddle = pathChainHelper.fromTwoPoints(basketPosition, grabFromFloorMiddle, PathChainHelperType.LINEAR);

        // LEFT -> BASKET, BASKET -> LEFT
        runLeftToBasket = pathChainHelper.fromTwoPoints(grabFromFloorLeft, basketPosition, PathChainHelperType.LINEAR);
        runBasketToLeft = pathChainHelper.fromTwoPoints(basketPosition, grabFromFloorLeft, PathChainHelperType.LINEAR);

        runLeftToZone = pathChainHelper.fromTwoPoints(grabFromFloorLeft, pushIntoZone, PathChainHelperType.CONSTANT);
        runZoneToPark = pathChainHelper.fromTwoPoints(pushIntoZone, end, PathChainHelperType.LINEAR);

        pickingUpCurrentlyPose = grabFromFloorRight;
        pickingUpCurrentlyPath = runBasketToRight;
        pickingUpCurrentlyState = FloorSampleState.RIGHT;
        buildTime = pathBuildTimer.getElapsedTime();
        pathBuildTimer = null;
        ready = true;
    }


    @Override
    public void init() {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap); // FollowerEx returns a Pedro follower, but with extra methods to keep things clean in this file & for future autonomous creations.
        follower.setPose(startPose);
        pathChainHelper = new PathChainHelper(follower);
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
        setState(BasketAutoState.INIT);
    }

    @Override
    public void loop() {
        follower.update();
        updateAutonomousState();

        telemetry.addData("Floor Pickup State", pickingUpCurrentlyState);
        telemetry.addData("Auto State", state);
        telemetry.addLine();
        telemetry.addData("arm position", arm.getArmPosition());
        telemetry.addData("arm ticks away from basket score", scoringBasketRaisePos - arm.getArmPosition());
        telemetry.addData("action time (s)", actionTimer.getElapsedTime());
        telemetry.addData("path time (s)", pathTimer.getElapsedTime());
        telemetry.addData("opmode time (s)", opModeTimer.getElapsedTime());
//        follower.telemetryDebug(telemetry);
    }

    @Override
    public void stop() {
        FConstants.currentPose = follower.getPose();
    }

    public void updateAutonomousState() {
        switch(state) {
            case NOOP:
                // This is just in case we don't need to do anything, we'll just have a No Operation (NoOp)
                break;
            case INIT:
                // This only runs once at the very beginning.
                // Our robot should be preloaded with a sample, closed and up. This just locks them in place so the robot knows it's closed.
                claw.close();
                claw.up();
                setState(BasketAutoState.START_TO_BASKET);
                break;
            case START_TO_BASKET:
                follower.followPath(runStartToBasket); // We're going to go to the basket.
                pickingUpCurrentlyState = FloorSampleState.RIGHT; // Mark the next sample to pick up as the right sample.
                setState(BasketAutoState.RAISE_TO_BASKET); // Raise the arm as we go to the basket.
                break;
            case RAISE_TO_BASKET:
                // Raise the arm, this can happen while we're moving to the path to save time (~3 sec.)
                arm.setSlidePower(1);
                if(arm.getArmPosition() >= scoringBasketRaisePos) {
                    arm.setSlidePower(0); // The arm is all the way up, so we can now stop giving it power.
                    actionTimer.resetTimer();
                    claw.down(); // Since we're all the way up, we *should, in 99.9% cases* be able to lower the claw.
                    setState(BasketAutoState.SCORE_BASKET); // This means, after this iteration we will not go back through this.
                }
                break;
            case SCORE_BASKET:
                if(isInRangeOf(basketPosition)) {
                    arm.setSlidePower(0); // Extra fallback, just in case we're still raising the arm
                    claw.open(); // Open the claw, as we're now raised high enough & lowered into the basket.
                    actionTimer.resetTimer(); // Start the action timer.
                    setState(BasketAutoState.SCORE_OUT_BASKET);
                }
                break;
            case SCORE_OUT_BASKET:
                if(actionTimer.getElapsedTime() > 750) {
                    // We've waited 750ms (half a second), so we'll raise the claw out of the bucket.
                    claw.up();
                }
                    setState(BasketAutoState.LOWER_ARM_BASKET);
//                if(actionTimer.getElapsedTime() > 1200) {
                    // Extra wait time, so we don't grab onto the bucket & risk damaging claw/slides/etc..
//                }
                break;
//            case PULLOUT_BASKET:
//                if(isInRangeOf(basketPullout)) {
//                }
//                break;
            case LOWER_ARM_BASKET:
                arm.setSlidePower(-1); // Lower the arm, we're fully out now.
                // Let's pick where to go now. Initially, we're going to the RIGHT (pickingUpCurrentlyState = FloorSampleState.RIGHT)
                if(pickingUpCurrentlyState == FloorSampleState.RIGHT) {
                    // We're at the basket, and wanna grab the right yellow sample
                    pickingUpCurrentlyPath = runBasketToRight;
                    pickingUpCurrentlyPose = grabFromFloorRight;
                } else if(pickingUpCurrentlyState == FloorSampleState.MIDDLE) {
                    // We're at the basket, scored the right sample, and wanna grab the middle sample.
                    pickingUpCurrentlyPath = runBasketToMiddle;
                    pickingUpCurrentlyPose = grabFromFloorMiddle;
                } else if(pickingUpCurrentlyState == FloorSampleState.LEFT) {
                    // We're at the basket, we've just scored the left sample.
                    setState(BasketAutoState.PUSH_PIXEL_INTO_ZONE_GOTO);
                    break;
                }
                // We use the BASKET -> X because we aren't at the sample yet
                setState(BasketAutoState.RUN_TO_FLOOR_PICKUP);
                break;
            case RUN_TO_FLOOR_PICKUP:
                // Now we've set where we wanna go, let's go there. Dynamically adjusted from the step LOWER_ARM_BASKET.
                follower.followPath(pickingUpCurrentlyPath);
                setState(BasketAutoState.WAIT_SLIDE_DOWN_FLOOR_PICKUP);
                break;
            case WAIT_SLIDE_DOWN_FLOOR_PICKUP:
                // Now, just in case, we really need to wait for this specific part.
                // We do continue travelling to the floor sample, so we don't waste time here.
                if(arm.getArmPosition() <= 175) {
                    actionTimer.resetTimer();
                    arm.setSlidePower(0);
                    claw.down();
                    // The arm has fully lowered, so we can cut power, and lower it.
                    setState(BasketAutoState.FLOOR_PICKUP_WAIT_DOWN_AND_GOTO);
                }
                break;
            case FLOOR_PICKUP_WAIT_DOWN_AND_GOTO:
                // Here we're waiting until we're within 3 x/y coordinates of range
                // because this action needs to be precise.
                // We're also gonna wait for the wrist to go all the way down.
                if(isInRangeOf(pickingUpCurrentlyPose)) {
                    // We're here, and the wrist is down.
                    actionTimer.resetTimer();
                    setState(BasketAutoState.REAL_FLOOR_PICKUP); // Now actually pick up.
                }
                break;
            case REAL_FLOOR_PICKUP:
                if(!closedClawToPickupFromFloor && actionTimer.getElapsedTime() > 600) { // We'll wait 600 ms, so we're aligned properly with the sample.
                    claw.close(); // Now we'll pick it up,
                    actionTimer.resetTimer(); // reset the timer,
                    closedClawToPickupFromFloor = true; // then move onto the second stage.
                }
                if(closedClawToPickupFromFloor && actionTimer.getElapsedTime() > 1300) { // The claw doesn't take this long to close, but we wait 1.3s (1300ms) before going up, so we're sure the sample is grabbed.
                    claw.up();
                    setState(BasketAutoState.RUN_TO_BASKET_FROM_FLOOR); // Now we'll go to the basket.
                }
                break;
            case RUN_TO_BASKET_FROM_FLOOR:
                closedClawToPickupFromFloor = false; // Reset the variable for the next use
                PathChain gotoFromFloor = runRightToBasket; // This is our default, because the OpMode starts with the right sample.
                if(pickingUpCurrentlyState == FloorSampleState.RIGHT) {
                    pickingUpCurrentlyState = FloorSampleState.MIDDLE; // For next time, we will go to the middle.
                } else if(pickingUpCurrentlyState == FloorSampleState.MIDDLE) {
                    gotoFromFloor = runMiddleToBasket; // This run, go middle -> basket
                    pickingUpCurrentlyState = FloorSampleState.LEFT; // For next time, pick up the Left sample
                } else if(pickingUpCurrentlyState == FloorSampleState.LEFT) {
                    gotoFromFloor = runLeftToBasket; // This run, go left -> basket
                    pickingUpCurrentlyState = FloorSampleState.NONE; // For next time, start parking. This doesn't really matter as it's used for semantics & never checked anyways.
                }
                follower.followPath(gotoFromFloor);
                setState(BasketAutoState.RAISE_TO_BASKET);
                break;
            case PUSH_PIXEL_INTO_ZONE_GOTO:
                // Here, we deviate from the basket because we don't wanna score this one in the high basket, as we'll run out of time.
                // This is our choice to maximize points.
                // We'll go from the score position, to the left sample.
                // Because of the way the left sample's path is built, we will turn to face the Audience side.
                // This is important because we need to strafe right (score the sample by carrying it with us on the wheels).
                follower.followPath(runBasketToLeft);
                setState(BasketAutoState.PUSH_PIXEL_INTO_ZONE);
                break;
            case PUSH_PIXEL_INTO_ZONE:
                if(isInRangeOf(grabFromFloorLeft)) {
                    // We're at the sample, so just strafe right.
                    follower.followPath(runLeftToZone);
                    setState(BasketAutoState.PARK_GOTO);
                }
                break;
            case PARK_GOTO:
                if(isInRangeOf(pushIntoZone)) {
                    // Now we're below the basket, and the left sample has been scored.
                    // We will now start going to the submersible.
                    follower.followPath(runZoneToPark);
                    claw.open(); // Also, asynchronously run these actions so we get ready for the drivers to take over.
                    claw.middle();
                    setState(BasketAutoState.PARK);
                }
                break;
            case PARK:
                // We don't need to do anything, so we'll just go to No-Op to finish execution.
                // However, if we need to add anything this will get changed.
                setState(BasketAutoState.NOOP);
                break;
        }
    }

    public boolean isInRangeOf(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 3) &&
                follower.getPose().getY() > (pose.getY() - 3);
    }

    public void setState(BasketAutoState state) {
        this.state = state;
        pathTimer.resetTimer();
    }
}

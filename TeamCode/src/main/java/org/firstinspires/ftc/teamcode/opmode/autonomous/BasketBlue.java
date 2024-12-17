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
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

@Config
@Autonomous(name="Miguel Antimaneuvering - Basket", group="!!! Auton")
public class BasketBlue extends OpMode {
    public static int scoringBasketRaisePos = 3500;
    public enum State {
        NOOP,
        INIT,
        START_TO_BASKET,
        RAISE_TO_BASKET,
        SCORE_BASKET,
        SCORE_OUT_BASKET,
        LOWER_ARM_BASKET,
        RUN_TO_FLOOR_Right,
        WAIT_SLIDE_DOWN_RIGHT,
        PICKUP_FLOOR_Right,
        RUN_TO_BASKET_FLOOR_Right
    }
    public enum ScoreState {
        NONE,
        RAISING,
        SCORING,
        LOWERING
    }

    boolean builtPaths = false;
    boolean buildState = false;

    Timer pathTimer = new Timer();
    Timer opModeTimer = new Timer();
    Timer actionTimer = new Timer();

    Follower follower;
    Arm arm;
    Claw claw;

    State state = State.INIT;
    /**
     * BASKET BLUE
     */
    Pose startPose = new Pose(35.5 + 72, 61.5 + 72, Math.toRadians(-90));
    Pose basketPosition = new Pose(52 + 72, 53 + 72, Math.toRadians(45));
    Pose grabFromFloorRight = new Pose(47+72, 41+72, Math.toRadians(-90));
    Pose grabFromFloorMiddle = new Pose(33.797392176529584, 131.86760280842526, Math.toRadians(0));

    Path fromStartToBasket;
    Path fromBasketToRight;
    Path fromRightToBasket;
    PathChain runStartToBasket;
    PathChain runBasketToRight;
    PathChain runRightToBasket;

    public void buildPaths() {
        builtPaths = false;
        buildState = true;
        GlobalStorage.currentPose = startPose;
        follower.setPose(startPose);
        fromStartToBasket = new Path(new BezierCurve(
                new Point(startPose), new Point(basketPosition)
        ));
        fromStartToBasket.setLinearHeadingInterpolation(startPose.getHeading(), basketPosition.getHeading());

        runStartToBasket = follower.pathBuilder()
                .addPath(fromStartToBasket)
                .build();

        fromRightToBasket = new Path(new BezierCurve(
                new Point(grabFromFloorRight), new Point(basketPosition)
        ));
        fromRightToBasket.setLinearHeadingInterpolation(grabFromFloorMiddle.getHeading(), basketPosition.getHeading());

        runRightToBasket = follower.pathBuilder()
                .addPath(fromRightToBasket)
                .build();

        fromBasketToRight = new Path(new BezierCurve(
                new Point(basketPosition), new Point(grabFromFloorRight)
        ));
        fromBasketToRight.setLinearHeadingInterpolation(basketPosition.getHeading(), grabFromFloorRight.getHeading());


        runBasketToRight = follower.pathBuilder()
                .addPath(fromBasketToRight)
                .build();

        buildState = false;
        builtPaths = true;
    }

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new Follower(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        scoreStage = ScoreState.NONE;

        buildPaths();
    }

    @Override
    public void init_loop() {
        telemetry.addLine(builtPaths ? "Ready!" : buildState ? "Building paths..." : "Failed to build paths! This shouldn't happen normally, but press Gamepad1 A to attempt rebuild.");
        telemetry.update();
        if(gamepad1.a) buildPaths();
    }

    int autoStage = 0;
    public void updateAutonomousState() {
        switch(state) {
            case NOOP:
                break;
            case INIT:
                claw.close();
                claw.up();
                setState(State.START_TO_BASKET);
                break;
            case START_TO_BASKET:
                follower.followPath(runStartToBasket);
                setState(State.RAISE_TO_BASKET);
                autoStage = 0;
                break;
            case RAISE_TO_BASKET:
                arm.setSlidePower(1);
                if(arm.getArmPosition() >= scoringBasketRaisePos) {
                    arm.setSlidePower(0);
                    actionTimer.resetTimer();
                    claw.down();
                    setState(State.SCORE_BASKET);
                }
                break;
            case SCORE_BASKET:
                if(isInRangeOf(basketPosition)) {
                    arm.setSlidePower(0);
                    claw.open();
                    actionTimer.resetTimer();
                    setState(State.SCORE_OUT_BASKET);
                }
                break;
            case SCORE_OUT_BASKET:
                if(actionTimer.getElapsedTime() > 720) {
                    arm.setSlidePower(0);
                    claw.up();
                }
                if(actionTimer.getElapsedTime() > 800) {
                    setState(State.LOWER_ARM_BASKET);
                }
                break;
            case LOWER_ARM_BASKET:
                arm.setSlidePower(-1);
                if(arm.getArmPosition() < 300) {
                    arm.setSlidePower(0);
                    if(autoStage == 0) {
                        // We're at the basket, and wanna grab the right yellow sample
                        autoStage = 1;
                        setState(State.RUN_TO_FLOOR_Right);
                    }
                }
                break;
            case RUN_TO_FLOOR_Right:
                follower.followPath(runBasketToRight);
                setState(State.PICKUP_FLOOR_Right);
                break;
            case WAIT_SLIDE_DOWN_RIGHT:
                if(arm.getArmPosition() <= 300) {
                    actionTimer.resetTimer();
                    arm.setSlidePower(0);
                    claw.down();
                    setState(State.PICKUP_FLOOR_Right);
                }
                break;
            case PICKUP_FLOOR_Right:
                if(isInRangeOf(grabFromFloorRight)) {
                    if(actionTimer.getElapsedTime() > 250) claw.close();
                    if(actionTimer.getElapsedTime() > 300) {
                        claw.up();
                        setState(State.RUN_TO_BASKET_FLOOR_Right);
                    }
                }
                break;
            case RUN_TO_BASKET_FLOOR_Right:
                follower.followPath(runRightToBasket);
                setState(State.RAISE_TO_BASKET);
                break;
        }
    }

    ScoreState scoreStage = ScoreState.RAISING;
    public void setScoreStage(ScoreState stage) {
        scoreStage = stage;
        actionTimer.resetTimer();
    }

    public boolean isInRangeOf(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 3) &&
                follower.getPose().getY() > (pose.getY() - 3);
    }

    public void setState(State state) {
        this.state = state;
        pathTimer.resetTimer();
    }

    @Override
    public void stop() {
        GlobalStorage.currentPose = follower.getPose();
    }

    @Override
    public void loop() {
        follower.update();
        updateAutonomousState();

        telemetry.addData("auto state", state);
        telemetry.addData("score state", scoreStage);
        telemetry.addData("arm away from score", scoringBasketRaisePos - arm.getArmPosition());
        telemetry.addData("arm position", arm.getArmPosition());
        telemetry.addData("action time (s)", actionTimer.getElapsedTime());
        telemetry.addData("path time (s)", pathTimer.getElapsedTime());
        telemetry.addData("opmode time (s)", opModeTimer.getElapsedTime());
        telemetry.addLine();
//        follower.telemetryDebug(telemetry);
        telemetry.update();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        actionTimer.resetTimer();
        setState(State.INIT);
    }
}

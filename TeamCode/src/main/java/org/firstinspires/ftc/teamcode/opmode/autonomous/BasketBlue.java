package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

@Autonomous(name="Basket Blue Miguel Antimaneuvering", group="!!! Auton")
public class BasketBlue extends OpMode {
    public static long scoringBasketSlideTime = 4500;
    public enum State {
        NOOP,
        INIT,
        START_TO_BASKET,
        SCORE_BASKET;
    }


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
    Pose startPose = Pose.fromRoadRunner(35.5, 61.5, Math.toRadians(-90));
    Pose basketPosition = Pose.fromRoadRunner(53, 54, Math.toRadians(45));
//    Pose br_otherThing = Pose.fromRoadRunner(58, 60, Math.toRadians(90));

    Path fromStartToBasket;
    PathChain runStartToBasket;

    public void buildPaths() {
        follower.setPose(startPose);
        fromStartToBasket = new Path(new BezierCurve(
                new Point(startPose), new Point(basketPosition)
        ));
        fromStartToBasket.setLinearHeadingInterpolation(startPose.getHeading(), basketPosition.getHeading());

        runStartToBasket = follower.pathBuilder()
                .addPath(fromStartToBasket)
                .build();
    }

    @Override
    public void init() {
        follower = new Follower(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);

        buildPaths();
    }

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
                setState(State.SCORE_BASKET);
                break;
            case SCORE_BASKET:
                if(isInRangeOf(basketPosition)) {
                    scoreBasket();
                }
                break;
        }
    }

    boolean scoring = false;
    public void scoreBasket() {
        scoring = true;
        arm.slidePower(1);
        if(pathTimer.getElapsedTime() > scoringBasketSlideTime) {
            arm.slidePower(0);
            claw.down();
            claw.open();
        }
        if(pathTimer.getElapsedTime() > scoringBasketSlideTime * 2) {
            claw.up();
            claw.close();
            arm.slidePower(-1);
            actionTimer.resetTimer();
        }
        if(scoring && actionTimer.getElapsedTime() > scoringBasketSlideTime) {
            arm.slidePower(0);
            claw.down();
            claw.open();
        }
    }

    public boolean isInRangeOf(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 1) &&
                follower.getPose().getY() > (pose.getY() - 1);
    }

    public boolean inRange(long a, long lower, long upper) {
        return (lower <= a && a <= upper);
    }

    public void setState(State state) {
        this.state = state;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        updateAutonomousState();

        telemetry.addData("auto state", state);
        telemetry.addData("path time (s)", pathTimer.getElapsedTimeSeconds());
        telemetry.addData("opmode time (s)", opModeTimer.getElapsedTimeSeconds());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        actionTimer.resetTimer();
        setState(State.INIT);
    }
}

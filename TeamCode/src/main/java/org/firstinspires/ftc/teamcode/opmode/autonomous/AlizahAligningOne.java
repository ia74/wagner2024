package org.firstinspires.ftc.teamcode.opmode.autonomous;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.GlobalStorage;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;

@Config
@Autonomous(name="0+0 Park+something / Alizah Aligning", group="!!! Auton")
public class AlizahAligningOne extends OpMode {
    public enum State {
        none,
        init,
        START_TO_PICKUP,
        PICKUP_WAIT_FOR_WRIST_THEN_COLLECT,
        PICKUP,
        PICKUP_RAISE,
        PICKUP_TO_END,
        PICKUP_RAISE_WRIST,
    }

    boolean ready = false;
    Timer pathTimer = new Timer();
    Timer opModeTimer = new Timer();
    Timer actionTimer = new Timer();
    Timer buildTimer = new Timer();

    long buildTime = 0;

    Follower follower;

    State state = State.none;

    Pose startPose = new Pose(10.372881355932204, 60.55932203389831, Math.toRadians(0));
    Pose pickupPose = new Pose(33.93413173652695, 60.55932203389831, Math.toRadians(0)); // x 125 -> 124
    Pose endPose = new Pose(11.353293413173652,17.96407185628742,Math.toRadians(0));

    PathChain runStartTopickupPose;
    PathChain runPickupToEnd;
    Lights lights;
    Claw claw;

    public void buildPaths() {
        ready = false;
        buildTimer.resetTimer();

        GlobalStorage.currentPose = startPose;
        follower.setPose(startPose);

        runStartTopickupPose = CreatePathChain(startPose, pickupPose);
        runPickupToEnd = CreatePathChain(pickupPose, endPose);

        buildTime = buildTimer.getElapsedTime();
        buildTimer = null;
        ready = true;
    }

    PathChain CreatePathChain(Pose point1, Pose point2) {
        return follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        new Point(point1), new Point(point2)
                )))
                .setLinearHeadingInterpolation(point1.getHeading(), point2.getHeading())
                .build();
    }


    @Override
    public void init() {
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new Follower(hardwareMap);
        claw = new Claw(hardwareMap);
        lights = new Lights(hardwareMap);

        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET);

        claw.setWristState(Claw.WristState.UP);

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
        setState(State.init);
    }

    void setState(State state){
        this.state = state;
        pathTimer.resetTimer();

    }

    void updateAutoState(){
        switch(state){
            case none: break;
            case init:
                follower.followPath(runStartTopickupPose,true);
                setState(State.START_TO_PICKUP);
                break;
            case START_TO_PICKUP:
                if (!follower.isBusy()) {
                    claw.setWristState(Claw.WristState.DOWN);
                    actionTimer.resetTimer();
                    setState(State.PICKUP_WAIT_FOR_WRIST_THEN_COLLECT);
                }
                break;
            case PICKUP_WAIT_FOR_WRIST_THEN_COLLECT:
                if(actionTimer.getElapsedTime() >= 400){
                    claw.setClawState(Claw.ClawState.CLOSED);
                }
                if(actionTimer.getElapsedTime()>= 600){
                    claw.setWristState(Claw.WristState.UP);
                }
                if(actionTimer.getElapsedTime()>= 800){
                    follower.followPath(runPickupToEnd, true);
                    setState(State.none);
                }
                break;
        }
    }

    @Override
    public void loop() {
        updateAutoState();
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

package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;

public class AutonomousOperations {
    public enum AutoOpState {
        IDLE,
        CLIPPING_START_AT_OBSERVE,
        CLIPPING_START_AT_SUBMERSIBLE,
        CLIPPING_RAISE_SLIDES,
        CLIPPING_MOVE_TO_SCORE_AND_SET_WRIST,
        CLIPPING_SCORING_LOWER_SLIDES,
        CLIPPING_SCORING_AWAIT_SLIDES,
        CLIPPING_OPEN_CLAW,
        CLIPPING_GOTO_OBSERVATION,
    }

    /** DATA **/
    public static Pose observationZone = new Pose(17.5, 11.065868263473059, Math.toRadians(180));
    public static Pose clipping = new Pose(33.5, 74.51790900290416, Math.toRadians(0));

    private AutoOpState state = AutoOpState.IDLE;
    private final Follower follower;
    private final Arm arm;
    private final Claw claw;
    private final PathChain observationZoneToClipping;

    public Timer stateTimer = new Timer();
    public Timer actionTimer = new Timer();

    private void setState(AutoOpState state) {
        this.state = state;
        stateTimer.resetTimer();
    }

    public AutoOpState getState() {return state;}

    public AutonomousOperations(Follower follower, Arm arm, Claw claw) {
        this.follower = follower;
        this.arm = arm;
        this.claw = claw;
        observationZoneToClipping = follower.pathBuilder()
                .addBezierCurve(
                        new Point(observationZone),
                        new Point(clipping)
                )
                .setPathEndTimeoutConstraint(2)
                .build();
    }

    public void scoreSpecimen(boolean clipFromCurrentPosition) {
        if(clipFromCurrentPosition) setState(AutoOpState.CLIPPING_START_AT_SUBMERSIBLE);
        else setState(AutoOpState.CLIPPING_START_AT_OBSERVE);
    }

    public void update() {
        switch(state) {
            case IDLE:break;
            case CLIPPING_START_AT_OBSERVE:
                follower.setPose(observationZone);
                follower.followPath(observationZoneToClipping, true);
                arm.setSlidesTargetPosition(Clippy.clipBasketHeight);
                setState(AutoOpState.CLIPPING_RAISE_SLIDES);
                break;
            case CLIPPING_START_AT_SUBMERSIBLE:
                arm.setSlidesTargetPosition(Clippy.clipBasketHeight);
                setState(AutoOpState.CLIPPING_RAISE_SLIDES);
                break;
            case CLIPPING_RAISE_SLIDES:
                if(arm.isPositionWithinTolerance(arm.getArmPosition(), 20, Clippy.clipBasketHeight)) {
                    setState(AutoOpState.CLIPPING_MOVE_TO_SCORE_AND_SET_WRIST);
                }
                break;
            case CLIPPING_MOVE_TO_SCORE_AND_SET_WRIST:
                if(isLikeClose(clipping)) claw.setWristState(Claw.WristState.MIDDLE);
                if(!follower.isBusy()) {
                    claw.setWristState(Claw.WristState.MIDDLE); // just in case
                    actionTimer.resetTimer();
                    setState(AutoOpState.CLIPPING_SCORING_LOWER_SLIDES);
                }
                break;
            case CLIPPING_SCORING_LOWER_SLIDES:
                if(actionTimer.getElapsedTime() > 750) {
                    arm.setSlidesTargetPosition(Clippy.clipBasketLowerScore);
                    setState(AutoOpState.CLIPPING_SCORING_AWAIT_SLIDES);
                }
                break;
            case CLIPPING_SCORING_AWAIT_SLIDES:
                if(arm.isPositionWithinTolerance(arm.getArmPosition(), 20, Clippy.clipBasketLowerScore)) {
                    actionTimer.resetTimer();
                    setState(AutoOpState.CLIPPING_OPEN_CLAW);
                }
                break;
            case CLIPPING_OPEN_CLAW:
                claw.setClawState(Claw.ClawState.OPEN);
                if(actionTimer.getElapsedTime() > 1000) {
                    actionTimer.resetTimer();
                    setState(AutoOpState.IDLE);
                }
                break;
        }
    }

    public boolean isLikeClose(Pose pose) {
        return follower.getPose().getX() > (pose.getX() - 4) &&
                follower.getPose().getY() > (pose.getY() - 4);
    }
}

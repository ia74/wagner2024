package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config //a lot of yap, good
public class Claw extends Subsystem {
    public Servo claw;
    public Servo wrist;
    public static double clawOpenPosition = 0.05;
    public static double clawClosedPosition = 0.25;
    private ClawState clawState = ClawState.UNKNOWN;

    public static double wristUpPosition = 0.1;
    public static double wristBucketPosition = 0.45;
    public static double wristMiddlePosition = 0.6;
    public static double wristDownPosition = 0.8;
    public static double wristStraightUp = 0.325;
    private WristState wristState = WristState.UNKNOWN;

    public enum WristState {
        UP,
        STRAIGHT_UP,
        BUCKET,
        MIDDLE,
        DOWN,
        UNKNOWN
    }

    public enum ClawState {
        CLOSED,
        OPEN,
        UNKNOWN
    }

    public Claw(HardwareMap hardwareMap) {
        super(hardwareMap);
        claw = hardwareMap.get(ServoImplEx.class, PartsMap.CLAW.toString());
        wrist = hardwareMap.get(Servo.class, PartsMap.WRIST.toString());
        claw.setDirection(Servo.Direction.REVERSE);
        wristState = WristState.UNKNOWN;
        clawState = ClawState.UNKNOWN;
    }

    public void setWristState(WristState state) {
        if(wristState != state) {
            switch(state) {
                case UP: wrist.setPosition(Claw.wristUpPosition); break;
                case STRAIGHT_UP: wrist.setPosition(Claw.wristStraightUp); break;
                case MIDDLE: wrist.setPosition(Claw.wristMiddlePosition); break;
                case BUCKET: wrist.setPosition(Claw.wristBucketPosition); break;
                case DOWN: wrist.setPosition(Claw.wristDownPosition); break;
            }
            wristState = state;
        }
    }

    public void setClawState(ClawState state) {
        if(clawState != state) {
            switch(state) {
                case CLOSED: claw.setPosition(Claw.clawClosedPosition); break;
                case OPEN: claw.setPosition(Claw.clawOpenPosition); break;
            }
            clawState = state;
        }
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Claw] --\n" +
                "Claw State:" + clawState + "\n" +
                "Wrist State:" + wristState + "\n" +
                "Open Position: " + Claw.clawOpenPosition + "\n" +
                "Closed Position: " + Claw.clawClosedPosition + "\n" +
                "Up Position: " + Claw.wristUpPosition + "\n" +
                "Down Position: " + Claw.wristDownPosition + "\n" +
                Subsystem.servoIfo(claw, "Claw") + "\n" +
                Subsystem.servoIfo(wrist, "Wrist") + "\n";
    }
}
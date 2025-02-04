package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PartsMap;
import org.firstinspires.ftc.teamcode.opmode.subsystem.pid.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.opmode.subsystem.pid.PIDFController;

@Config //a lot of yap, good
public class NewClaw extends Subsystem {
    public Servo claw;
    public static double clawOpenPosition = -1;
    public static double clawClosedPosition = 1;
    private ClawState clawState = ClawState.UNKNOWN;

    public enum ClawState {
        CLOSED,
        OPEN,
        UNKNOWN
    }

    public DcMotor wrist;

    public static PIDFCoefficients wristCoefficients = new PIDFCoefficients(
            0.005,
            0,
            0,
            0
    );
    public static double wristMaximumPositionLimit = 2500;
    public static double wristTargetPosition = 0;

    public PIDFController wristPid;


    public NewClaw(HardwareMap hardwareMap) {
        super(hardwareMap);
        claw = hardwareMap.get(Servo.class, PartsMap.CLAW.toString());
        wrist = hardwareMap.get(DcMotor.class, PartsMap.WRIST.toString());
        claw.setDirection(Servo.Direction.REVERSE);
        clawState = ClawState.UNKNOWN;

        Subsystem.initializeMotors(wrist);
        wrist.setDirection(DcMotorSimple.Direction.FORWARD);

        wristPid = new PIDFController(wristCoefficients, wristMaximumPositionLimit);
        wristPid.setMaxPosition(wristMaximumPositionLimit);
        wristPid.setTargetPosition(getWristPosition());

    }

    public int getWristPosition() {
        return wrist.getCurrentPosition();
    }

    public void setWristTargetPosition(int tpos) {
        wristTargetPosition = tpos;
    }

    public void updateWrist() {
        wristPid.setTargetPosition(wristTargetPosition);
        double power = wristPid.calculate(getWristPosition());
        wrist.setPower(power);
    }

    public void setClawState(ClawState state) {
        if(clawState != state) {
            switch(state) {
                case CLOSED: claw.setPosition(NewClaw.clawClosedPosition); break;
                case OPEN: claw.setPosition(NewClaw.clawOpenPosition); break;
            }
            clawState = state;
        }
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Claw] --\n" +
                "Claw State:" + clawState + "\n" +
                "Open Position: " + NewClaw.clawOpenPosition + "\n" +
                "Closed Position: " + NewClaw.clawClosedPosition + "\n" +
                Subsystem.servoIfo(claw, "Claw") + "\n" +
                Subsystem.motorIfo(wrist, "Wrist") + "\n" +
                Subsystem.pidControllerIfo(wristPid, "Wrist", String.valueOf(wrist.getPower()));
    }
}
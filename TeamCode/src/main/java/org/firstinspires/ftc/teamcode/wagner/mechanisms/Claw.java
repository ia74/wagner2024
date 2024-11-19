package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config //a lot of yap, good
public class Claw implements Mechanism {
    public Servo claw;
    public Servo wrist;
    public static double clawZero = 0;
    public static double clawOpenPosition = -1;
    public static double clawClosedPosition = 1;
    public static double clawFullOpen = -1;
    public static double clawFullClose = 1;

    public static double wristUpPosition = 0.9;
    public static double wristMiddlePosition = 0.6;
    public static double wristDownPosition = 0.5;
    public static double wristFullUp = 1;
    public static double wristFullDown = -1;

    @Override
    public void init(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, PartsMap.CLAW.toString());
        wrist = hardwareMap.get(Servo.class, PartsMap.WRIST.toString());
        claw.setDirection(Servo.Direction.REVERSE);
        wrist.setDirection(Servo.Direction.REVERSE);
    }

    public void up() {wrist.setPosition(wristUpPosition);}
    public void down() {wrist.setPosition(wristDownPosition);}
    public void middle() {wrist.setPosition(wristMiddlePosition);}
    public void fullDown() {wrist.setPosition(wristFullDown);}
    public void fullUp() {wrist.setPosition(wristFullUp);}

    public void clawZero() {claw.setPosition(clawZero);}
    public void close() {claw.setPosition(clawClosedPosition);}
    public void open() {claw.setPosition(clawOpenPosition);}
    public void fullOpen() {claw.setPosition(clawFullOpen);}
    public void fullClose() {claw.setPosition(clawFullClose);}

    public void setWrist(double pos) {
        wrist.setPosition(pos);
    }

    public void openClaw(double pos) {
        claw.setPosition(pos);
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Claw] --\n" +
                "Claw.clawOpenPosition: " + Claw.clawOpenPosition + "\n" +
                "Claw.clawClosedPosition: " + Claw.clawClosedPosition + "\n" +
                "Claw.wristUpPosition: " + Claw.wristUpPosition + "\n" +
                "Claw.wristDownPosition: " + Claw.wristDownPosition + "\n" +
                Mechanism.servoIfo(claw, "Claw") + "\n" +
                Mechanism.servoIfo(wrist, "Wrist") + "\n";
    }
}

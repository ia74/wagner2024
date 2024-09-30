package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Claw implements Mechanism {
    public Servo claw;
    public Servo wrist;
    public static double clawOpenPosition = 0.2;
    public static double clawClosedPosition = 1;
    public static double maxWrist = 0.9;

    @Override
    public void init(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, PartsMap.CLAW.toString());
        wrist = hardwareMap.get(Servo.class, PartsMap.WRIST.toString());
    }

    public void up() {wrist.setPosition(maxWrist);}
    public void down() {wrist.setPosition(-maxWrist);}
    public void middle() {wrist.setPosition(0);}
    public void fullDown() {wrist.setPosition(-1);}
    public void fullUp() {wrist.setPosition(1);}

    public void clawZero() {claw.setPosition(0);}
    public void close() {claw.setPosition(clawClosedPosition);}
    public void open() {claw.setPosition(clawOpenPosition);}
    public void fullOpen() {claw.setPosition(1);}
    public void fullClose() {claw.setPosition(-1);}

    public void setWrist(double pos) {
        wrist.setPosition(pos);
    }

    public void openClaw(double pos) {
        claw.setPosition(pos);
    }
}

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
    public static double clawOpenPosition = 0.5;
    public static double maxWrist = 0.9;

    @Override
    public void init(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, PartsMap.CLAW.toString());
        wrist = hardwareMap.get(Servo.class, PartsMap.WRIST.toString());
    }

    public void setWrist(double pos) {
        wrist.setPosition(pos);
    }

    public void openClaw(double pos) {
        claw.setPosition(pos);
    }
}

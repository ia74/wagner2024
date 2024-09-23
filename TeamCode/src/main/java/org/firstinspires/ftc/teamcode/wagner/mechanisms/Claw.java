package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Claw implements Mechanism {
    public CRServo claw;
    public static double clawOpenPosition = 0.5;

    @Override
    public void init(HardwareMap hardwareMap) {
        claw = hardwareMap.get(CRServo.class, PartsMap.CLAW.toString());
    }

    public void openClaw(double power) {
        claw.setPower(power);
    }
}

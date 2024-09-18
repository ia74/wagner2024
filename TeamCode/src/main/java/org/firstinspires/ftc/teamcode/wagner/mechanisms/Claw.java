package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Claw implements Mechanism {
    public CRServo leftClaw;
    public CRServo rightClaw;

    @Override
    public void init(HardwareMap hardwareMap) {
        leftClaw = hardwareMap.get(CRServo.class, PartsMap.CLAW_LEFT.toString());
        rightClaw = hardwareMap.get(CRServo.class, PartsMap.CLAW_RIGHT.toString());
        //TODO: REVERSE
        rightClaw.setDirection(CRServo.Direction.REVERSE);
    }

    public void openClaw(double power) {
        leftClaw.setPower(power);
        rightClaw.setPower(power);
    }
}

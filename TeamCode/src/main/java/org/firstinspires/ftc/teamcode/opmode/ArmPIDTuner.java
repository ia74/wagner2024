package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystem.Arm;

/**
 * this is a test and probably doesnt and wont worrk
 * i wrote this in like 2 seconds
 */
@Config
@Autonomous(name = "PID / Arm Tuner")
public class ArmPIDTuner extends OpMode {
    public static double targetPositionHigh = 0;
    public static double targetPositionLow = 0;
    double targetPosition = targetPositionHigh;

    Arm arm;

    @Override
    public void init() {
        arm = new Arm(hardwareMap);
    }

    @Override
    public void loop() {
        if(arm.getArmPosition() >= targetPositionHigh) {
            targetPosition = targetPositionLow;
        } else if(arm.getArmPosition() <= targetPositionLow) {
            targetPosition = targetPositionHigh;
        }

        arm.pidController.setKP(Arm.kP);
        arm.pidController.setKI(Arm.kI);
        arm.pidController.setKD(Arm.kD);
        Arm.targetPosition = targetPosition;

        arm.update();
    }
}

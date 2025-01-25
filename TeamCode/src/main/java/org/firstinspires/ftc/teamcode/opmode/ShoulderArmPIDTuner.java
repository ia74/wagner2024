package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;

@Disabled
@Config
//@Autonomous(name = "PID / Shoulder Tuner")
public class ShoulderArmPIDTuner extends OpMode {
    public static double targetPositionHigh = 2000;
    public static double targetPositionLow = 300;
    double targetPosition = targetPositionLow;
    double lastPosition;

    Arm arm;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        arm = new Arm(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.y) Arm.shoulderktargetPosition = targetPositionHigh;
        if(gamepad1.a) Arm.shoulderktargetPosition = targetPositionLow;
        double stickInput = -gamepad1.right_stick_y;
        if(Math.abs(stickInput) > 0.1) {
            double newPos = stickInput * Arm.shoulderkmaxPosition;
            if(newPos > lastPosition) {
                Arm.shoulderktargetPosition = newPos;
                if(Arm.shoulderktargetPosition > Arm.shoulderkmaxPosition) Arm.shoulderktargetPosition = Arm.shoulderkmaxPosition;
                lastPosition = newPos;

            }
        }

        arm.setShoulderTargetPosition(Arm.shoulderktargetPosition);
        arm.shoulderPid.setkP(Arm.shoulderkP);
        arm.shoulderPid.setkI(Arm.shoulderkI);
        arm.shoulderPid.setkD(Arm.shoulderkD);
        arm.shoulderPid.setkF(Arm.shoulderkF);
        arm.update();
        telemetry.addData("Target Position", Arm.shoulderktargetPosition);
        telemetry.addData("Current Position", arm.getShoulderPosition());
        telemetry.addData("kP", Arm.shoulderkP);
        telemetry.addData("kI", Arm.shoulderkI);
        telemetry.addData("kD", Arm.shoulderkD);
        telemetry.addData("kF", Arm.shoulderkF);
        telemetry.update();
    }
}

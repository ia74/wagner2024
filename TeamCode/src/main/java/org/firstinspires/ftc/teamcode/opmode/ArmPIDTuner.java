package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;

@Config
@Autonomous(name = "PID / Arm Tuner")
public class ArmPIDTuner extends OpMode {
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
        if(gamepad1.y) Arm.stargetPosition = targetPositionHigh;
        if(gamepad1.a) Arm.stargetPosition = targetPositionLow;
        double stickInput = -gamepad1.right_stick_y;
        if(Math.abs(stickInput) > 0.1) {
            double newPos = stickInput * Arm.smaxPosition;
            if(newPos > lastPosition) {
                Arm.stargetPosition = newPos;
                if(Arm.stargetPosition > Arm.smaxPosition) Arm.stargetPosition = Arm.smaxPosition;
                lastPosition = newPos;

            }
        }

        arm.slidesPid.setSetpoint(Arm.stargetPosition);
        arm.slidesPid.setkP(Arm.skP);
        arm.slidesPid.setkI(Arm.skI);
        arm.slidesPid.setkD(Arm.skD);
        arm.slidesPid.setkF(Arm.skF);
        arm.update();
        telemetry.addData("Target Position", Arm.stargetPosition);
        telemetry.addData("Current Position", arm.getArmPosition());
        telemetry.addData("kP", Arm.skP);
        telemetry.addData("kI", Arm.skI);
        telemetry.addData("kD", Arm.skD);
        telemetry.addData("kF", Arm.skF);
        telemetry.update();
    }
}

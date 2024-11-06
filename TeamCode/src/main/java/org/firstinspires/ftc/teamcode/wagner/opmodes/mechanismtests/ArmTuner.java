package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;

@Config
public class ArmTuner extends LinearOpMode {
    public static double kP = Arm.kP;
    public static double kI = Arm.kI;
    public static double kD = Arm.kD;
    public static double gravityCompensation = Arm.gravityCompensation;
    public static double targetPosition = Arm.targetPosition;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Arm arm = new Arm();
        arm.init(hardwareMap);

        telemetry.addLine("Ready for PID Tuning");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            arm.pidController.setKP(kP);
            arm.pidController.setKI(kI);
            arm.pidController.setKD(kD);
            arm.pidController.setGravityCompensation(gravityCompensation);
            arm.setTargetPosition(targetPosition);

            double joystickInput = -gamepad2.right_stick_y;

            arm.holdPositionWithJoystick(joystickInput);

            telemetry.addData("Target Position", targetPosition);
            telemetry.addData("Current Position", arm.getArmPosition());
            telemetry.addData("Joystick Input", joystickInput);
            telemetry.addData("kP", kP);
            telemetry.addData("kI", kI);
            telemetry.addData("kD", kD);
            telemetry.addData("Gravity Compensation", gravityCompensation);
            telemetry.update();
        }
    }
}

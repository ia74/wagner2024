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

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Arm arm = new Arm();
        arm.init(hardwareMap);

        telemetry.addLine("Ready for PID Tuning");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double joystickInput = -gamepad2.right_stick_y;
            telemetry.update();
        }
    }
}

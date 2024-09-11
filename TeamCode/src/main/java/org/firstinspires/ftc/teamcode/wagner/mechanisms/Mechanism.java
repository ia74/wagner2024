package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public interface Mechanism {
    void init(HardwareMap hardwareMap);
    void run(Gamepad gamepad1, Gamepad gamepad2, MecanumDrive drive, Telemetry telemetry);
}

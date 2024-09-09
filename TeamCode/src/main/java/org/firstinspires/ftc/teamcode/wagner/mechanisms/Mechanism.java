package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public interface Mechanism {
    void init();
    void run(Gamepad gamepad1, Gamepad gamepad2, MecanumDrive drive);
}

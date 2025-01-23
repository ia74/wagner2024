package org.firstinspires.ftc.teamcode.opmode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PartsMap;

public class Hang extends Subsystem{
    DcMotor hang;
    public DeployHooks hooks;

    public Hang(HardwareMap hardwareMap) {
        super(hardwareMap);

        hang = hardwareMap.get(DcMotor.class, PartsMap.HANG_WINCH.toString());
        hang.setDirection(DcMotorSimple.Direction.REVERSE);

        Subsystem.resetMotor(hang);

        hooks = new DeployHooks(hardwareMap);
    }

    public void on() {
        hang.setPower(1);
    }

    public void reverse() {
        hang.setPower(-1);
    }

    public void off() {
        hang.setPower(0);
    }
}

package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PartsMap;

public class Hang extends Subsystem{
    DcMotor hang;

    public Hang(HardwareMap hardwareMap) {
        super(hardwareMap);
        hang = hardwareMap.get(DcMotor.class, PartsMap.HANG_WINCH.getMapped());
        Subsystem.resetMotor(hang);
        hang.setDirection(DcMotorSimple.Direction.REVERSE);
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

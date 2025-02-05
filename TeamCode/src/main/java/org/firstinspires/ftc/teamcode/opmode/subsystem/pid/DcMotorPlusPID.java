package org.firstinspires.ftc.teamcode.opmode.subsystem.pid;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.NumericLimit;

public class DcMotorPlusPID {
    public DcMotor motor;
    public PIDFController pidfControl;
    public PIDFCoefficients coefficients = new PIDFCoefficients(
            0,
            0,
            0,
            0);
    public NumericLimit limit = new NumericLimit(
            0,
            1000,
            0
    );

    public DcMotorPlusPID(HardwareMap hardwareMap, String name) {
        motor = hardwareMap.get(DcMotor.class, name);
        pidfControl.setCoefficients(coefficients);
        limit.setCurrent(getPosition());
    }

    public double getPosition() {
        return motor.getCurrentPosition();
    }

    public void update() {
        pidfControl.setTargetPosition(limit.getCurrent());
        double power = pidfControl.calculate(getPosition());
        motor.setPower(power);
    }

    public void setTargetPosition(double position) {
        limit.setCurrent(position);
    }
}

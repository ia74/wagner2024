package org.firstinspires.ftc.teamcode.trilobytes.impl.usages;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmode.subsystem.pid.PIDFController;
import org.firstinspires.ftc.teamcode.trilobytes.Trilobyte;
import org.firstinspires.ftc.teamcode.trilobytes.TrilobyteState;

public class PIDControllerUser extends Trilobyte {
    private final DcMotor[] motors;
    private final PIDFController pidfController;
    public PIDControllerUser(PIDFController pidfController, DcMotor... motors) {
        super(TrilobyteState.OFF, "PID User");

        this.motors = motors;
        this.pidfController = pidfController;
    }

    @Override
    public TrilobyteState runPeriodic() {
        for(DcMotor motor : motors) {
            double currentPosition = motor.getCurrentPosition();

            double power = pidfController.calculate(currentPosition);
            motor.setPower(power);
        }
        return TrilobyteState.ON;
    }
}

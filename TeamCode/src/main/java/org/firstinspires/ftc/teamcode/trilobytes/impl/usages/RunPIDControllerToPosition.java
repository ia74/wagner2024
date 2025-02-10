package org.firstinspires.ftc.teamcode.trilobytes.impl.usages;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmode.subsystem.pid.PIDFController;
import org.firstinspires.ftc.teamcode.trilobytes.Trilobyte;
import org.firstinspires.ftc.teamcode.trilobytes.TrilobyteState;
import org.firstinspires.ftc.teamcode.trilobytes.impl.LambdaTrilobyte;

public class RunPIDControllerToPosition extends Trilobyte {
    private final DcMotor motor;
    private final PIDFController pidfController;
    private final double targetPosition;
    private final double tolerance;
    public RunPIDControllerToPosition(DcMotor motor, PIDFController pidfController, double targetPosition, double tolerance) {
        super(TrilobyteState.OFF, "PID Pos Runner");

        this.motor = motor;
        this.pidfController = pidfController;
        this.targetPosition = targetPosition;
        this.tolerance = tolerance;
    }

    @Override
    public TrilobyteState runOnce() {
        return TrilobyteState.IN_PROGRESS;
    }

    @Override
    public TrilobyteState runPeriodic() {
        pidfController.setTargetPosition(targetPosition);
        double currentPosition = motor.getCurrentPosition();

        if (Math.abs(targetPosition - currentPosition) <= tolerance) {
            setPostRunRemoval(true);
            return TrilobyteState.FINISHED;
        }
        return TrilobyteState.IN_PROGRESS;
    }
}

package org.firstinspires.ftc.teamcode.opmode.subsystem.pid;

import androidx.annotation.NonNull;

public class PIDFController {
    public PIDFCoefficients coefficients;
    private double integral,
                   previousError,
                   targetPosition,
                   maxPosition,
                   lastPosition;

    public PIDFController(double kP, double kI, double kD, double kF) {
        this.coefficients = new PIDFCoefficients(kP, kI, kD, kF);
        this.integral = 0;
        this.previousError = 0;
        this.targetPosition = 0;
    }
    public PIDFController(PIDFCoefficients coefficients) {
        this.coefficients = coefficients;
        this.integral = 0;
        this.previousError = 0;
        this.targetPosition = 0;
    }

    public void setTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
    }

    public void setMaxPosition(double maxPosition) {
        this.maxPosition = maxPosition;
    }

    public void setCoefficients(PIDFCoefficients coefficients) {
        this.coefficients = coefficients;
    }

    public double calculate(double currentPosition) {
        this.lastPosition = currentPosition;
        if(targetPosition > maxPosition) targetPosition = maxPosition;
        double error = targetPosition - currentPosition;
        integral += error;
        double derivative = error - previousError;

        double output = (coefficients.kP * error) +
                        (coefficients.kI * integral) +
                        (coefficients.kD * derivative) +
                        (coefficients.kF * targetPosition);

        previousError = error;
        return output;
    }

    @NonNull
    public String toString() {
        return "\tLast Position Read: " + lastPosition +"\n" +
               "\tTarget Position: " + targetPosition +"\n" +
               "\tCoefficients: " + coefficients.toString();
    }

    public void reset() {
        integral = 0;
        previousError = 0;
    }
}

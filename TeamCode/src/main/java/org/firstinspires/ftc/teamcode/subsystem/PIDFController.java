package org.firstinspires.ftc.teamcode.subsystem;

public class PIDFController {
    private double maxPosition;
    private double kP, kI, kD, kF;
    private double integral, previousError;
    private double setpoint;

    public PIDFController(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.integral = 0;
        this.previousError = 0;
        this.setpoint = 0;
    }

    public void setkD(double kD) {
        this.kD = kD;
    }

    public void setkF(double kF) {
        this.kF = kF;
    }

    public void setkI(double kI) {
        this.kI = kI;
    }

    public void setkP(double kP) {
        this.kP = kP;
    }

    public void setMaxPosition(double mp) {
        this.maxPosition = mp;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public double calculate(double currentPosition) {
        if(setpoint > maxPosition) setpoint = maxPosition;
        double error = setpoint - currentPosition;
        integral += error;
        double derivative = error - previousError;

        double output = (kP * error) + (kI * integral) + (kD * derivative) + (kF * setpoint);

        previousError = error;
        return output;
    }

    public void reset() {
        integral = 0;
        previousError = 0;
    }
}

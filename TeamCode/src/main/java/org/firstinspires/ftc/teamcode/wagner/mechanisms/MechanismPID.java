package org.firstinspires.ftc.teamcode.wagner.mechanisms;

public class MechanismPID {
    private double kP, kI, kD;
    private double integralSum = 0;
    private double lastError = 0;
    private double gravityCompensation;

    public MechanismPID(double kP, double kI, double kD, double gravityCompensation) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.gravityCompensation = gravityCompensation;
    }

    public double calculate(double target, double current) {
        double error = target - current;
        integralSum += error;
        double derivative = error - lastError;
        lastError = error;

        double output = (kP * error) + (kI * integralSum) + (kD * derivative) + gravityCompensation;
        return output;
    }

    public void reset() {
        integralSum = 0;
        lastError = 0;
    }

    public void setKP(double kP) { this.kP = kP; }
    public void setKI(double kI) { this.kI = kI; }
    public void setKD(double kD) { this.kD = kD; }
    public void setGravityCompensation(double gravityCompensation) { this.gravityCompensation = gravityCompensation; }
}

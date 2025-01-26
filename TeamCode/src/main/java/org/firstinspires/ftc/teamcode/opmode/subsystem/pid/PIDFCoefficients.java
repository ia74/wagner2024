package org.firstinspires.ftc.teamcode.opmode.subsystem.pid;

import androidx.annotation.NonNull;

import java.util.Locale;

public class PIDFCoefficients {
    public double kP = 0,
                  kI = 0,
                  kD = 0,
                  kF = 0;
    public PIDFCoefficients(double kP, double kI, double kD, double kF) {
        setCoefficients(kP, kI, kD, kF);
    }
    public void setCoefficients(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    @NonNull
    public String toString() {
        return "P/I/D/F:" + String.format(Locale.ENGLISH, "%.2f/%.2f/%.2f/%.2f", kP, kI, kD, kF);
    }
}

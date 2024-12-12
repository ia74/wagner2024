package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class Lights extends Subsystem {
    RevBlinkinLedDriver revBlinkinLedDriver;
    public static RevBlinkinLedDriver.BlinkinPattern pattern;

    public Lights(HardwareMap hardwareMap) {
        super(hardwareMap);
        revBlinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, PartsMap.LIGHT_BLINKIN.toString());
    }

    public void setPatternIfNot(RevBlinkinLedDriver.BlinkinPattern pattern) {
        if(Lights.pattern == pattern) return;
        revBlinkinLedDriver.setPattern(pattern);
        Lights.pattern = pattern;
    }
    public void setPattern(RevBlinkinLedDriver.BlinkinPattern pattern) {
        revBlinkinLedDriver.setPattern(pattern);
        Lights.pattern = pattern;
    }

    public void breathRed() {
        revBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
        Lights.pattern = RevBlinkinLedDriver.BlinkinPattern.BREATH_RED;
    }

    public void breathBlue() {
        revBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
        Lights.pattern = RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE;
    }

    public void green() {
        revBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        Lights.pattern = RevBlinkinLedDriver.BlinkinPattern.GREEN;
    }

    public void off() {
        revBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        Lights.pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Lights] --\n" +
                "Pattern: " + Lights.pattern +"\n";
    }
}

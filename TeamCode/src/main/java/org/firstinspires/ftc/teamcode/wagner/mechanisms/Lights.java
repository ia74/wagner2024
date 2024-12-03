package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Lights implements Mechanism {
    RevBlinkinLedDriver revBlinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    @Override
    public void init(HardwareMap hardwareMap) {
        revBlinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, PartsMap.LIGHT_BLINKIN.toString());
    }

    public void setPattern(RevBlinkinLedDriver.BlinkinPattern pattern) {
        revBlinkinLedDriver.setPattern(pattern);
    }

    public void breathRed() {
        revBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
    }

    public void green() {
        revBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    public void off() {
        revBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Lights] --\n";
    }
}

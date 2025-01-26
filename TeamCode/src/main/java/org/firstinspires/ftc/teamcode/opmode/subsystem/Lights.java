package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class Lights extends Subsystem {
    public RevBlinkinLedDriver revBlinkinLedDriver;
    public static RevBlinkinLedDriver.BlinkinPattern pattern;

    public Lights(HardwareMap hardwareMap) {
        super(hardwareMap);
        revBlinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, PartsMap.LIGHT_BLINKIN.toString());
    }

    public void setPattern(RevBlinkinLedDriver.BlinkinPattern pattern) {
        if(Lights.pattern != pattern) {
            revBlinkinLedDriver.setPattern(pattern);
            Lights.pattern = pattern;
        }
    }

    public void off() {
        setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Lights] --\n" +
                "Pattern: " + Lights.pattern +"\n";
    }
}

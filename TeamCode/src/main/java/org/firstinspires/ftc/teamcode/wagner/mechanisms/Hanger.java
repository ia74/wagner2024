package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Hanger implements Mechanism {
    public Servo hook;
    public static double EXTENDED = 0.2;
    public static double UNEXTENDED = 0;

    @Override
    public void init(HardwareMap hardwareMap) {
        hook = hardwareMap.get(Servo.class, PartsMap.HANGER_HOOK.toString());
    }

    public void extend() {hook.setPosition(EXTENDED);}
    public void unextend() {hook.setPosition(UNEXTENDED);}
    public void set(double pos) {hook.setPosition(pos);}

    @NonNull
    public String toString() {
        return "-- [Mechanism: Hanger] --\n" +
                Mechanism.servoIfo(hook, "Hook");
    }
}

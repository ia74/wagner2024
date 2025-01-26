package org.firstinspires.ftc.teamcode.opmode.colors;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.KneeSurgery;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;

@TeleOp(name = "Knee Surgery Green")
public class KSGreen extends KneeSurgery {
    public void setLightColor(Lights e) {
        e.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }
}

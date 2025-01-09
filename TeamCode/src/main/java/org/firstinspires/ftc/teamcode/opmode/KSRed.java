package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;

@TeleOp(name = "Knee Surgery Red")
public class KSRed extends KneeSurgery{
    public void setLightColor(Lights e) {
        e.breathRed();
    }
}

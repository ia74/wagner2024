package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;

@TeleOp(name = "Knee Surgery Blue")
public class KSBlue extends KneeSurgery{
    public void setLightColor(Lights e) {
        e.breathBlue();
    }
}

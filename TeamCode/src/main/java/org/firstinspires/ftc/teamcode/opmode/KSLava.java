package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;

@TeleOp(name = "Knee Surgery Lava")
public class KSLava extends KneeSurgery{
    public void setLightColor(Lights e) {
        e.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_LAVA_PALETTE);
    }
}

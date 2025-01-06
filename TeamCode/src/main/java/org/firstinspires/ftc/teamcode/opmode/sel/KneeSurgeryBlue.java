package org.firstinspires.ftc.teamcode.opmode.sel;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.KneeSurgery;

@TeleOp(name="Super Knee Surgery Blue")
public class KneeSurgeryBlue extends KneeSurgery {
    @Override
    public void setLightColor() {
        this.patternToUse = RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE;
        lights.setPattern(patternToUse);
    }
}

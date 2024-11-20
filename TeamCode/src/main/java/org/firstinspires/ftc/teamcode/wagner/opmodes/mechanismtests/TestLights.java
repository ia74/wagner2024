package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Lights;

public class TestLights extends LinearOpMode {
    public int sleepTime = 1000;
    @Override
    public void runOpMode() {
        Lights lights = new Lights();
        lights.init(hardwareMap);

        waitForStart();

        lights.green();
        sleep(500);
        lights.off();
        sleep(500);
        lights.green();
    }
    public void print(String a, Servo b, Telemetry t) {
        t.addLine(a + " / Current: " + b.getPosition());
        t.update();
    }
}

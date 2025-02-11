package org.firstinspires.ftc.teamcode.opmode.telemetryutil.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.opmode.telemetryutil.MenuItem;
import org.firstinspires.ftc.teamcode.opmode.telemetryutil.TelemetryMenu;

@Autonomous(name = "Telemenu Test")
public class MenuTest extends OpMode {
    TelemetryMenu menu;

    int picked = 0;

    @Override
    public void init() {
        menu = new TelemetryMenu(telemetry, "Number Selector");
        MenuItem pickOne = new MenuItem("Press me to change the number to 1");
        MenuItem pickTwo = new MenuItem("Press me to change the number to 2");

        pickOne.setOnClick(() -> setNumber(1));
        pickTwo.setOnClick(() -> setNumber(2));

        menu.addMenuItem(pickOne);
        menu.addMenuItem(pickTwo);
    }

    public void setNumber(int i) {picked = i;}

    @Override
    public void loop() {
        menu.update(gamepad1);
        telemetry.update();
    }
}

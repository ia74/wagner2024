package org.firstinspires.ftc.teamcode.opmode.telemetryutil;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

public class TelemetryMenu {
    private String title;
    private final Telemetry t;
    private final List<MenuItem> menuItems;
    private int selectedIndex = 0;
    private Gamepad lastGamepad;
    public TelemetryMenu(Telemetry t, String title) {
        this.t = t;
        this.title = title;
        menuItems = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    private void checkIndexOutsideOfBounds() {
        if(selectedIndex > menuItems.size()) selectedIndex = menuItems.size();
        else if(selectedIndex < 0) selectedIndex = 0;
    }


    public void update(Gamepad gamepad) {
        if(gamepad.dpad_up && !lastGamepad.dpad_up) selectedIndex++;
        if(gamepad.dpad_down && !lastGamepad.dpad_down) selectedIndex--;

        checkIndexOutsideOfBounds();

        t.addLine(title);

        for(int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            item.runPeriodic();
            String finalOutput = "";
            if(i == selectedIndex) {
                finalOutput = ">";
                item.runOnSelected();
                if(gamepad.a && !lastGamepad.a) {
                    finalOutput = "#";
                } else if(!gamepad.a && lastGamepad.a) {
                    finalOutput = "*";
                    item.runOnClick();
                }
                finalOutput += " ";
            }
            finalOutput += menuItems.toString();

            t.addLine(finalOutput);
        }
        t.addLine("DPad UP/DOWN - Change Selection");
        t.addLine("A - Select");

        lastGamepad = gamepad;
    }
}

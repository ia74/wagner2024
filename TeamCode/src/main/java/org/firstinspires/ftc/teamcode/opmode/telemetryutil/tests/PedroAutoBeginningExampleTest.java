package org.firstinspires.ftc.teamcode.opmode.telemetryutil.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.opmode.telemetryutil.MenuItem;
import org.firstinspires.ftc.teamcode.opmode.telemetryutil.MenuSpacer;
import org.firstinspires.ftc.teamcode.opmode.telemetryutil.TelemetryMenu;

@Autonomous(name = "Telemenu Auto Example")
public class PedroAutoBeginningExampleTest extends OpMode {
    TelemetryMenu currentMenu;

    public enum AutoState {
        BASKET_BLUE,
        BASKET_RED,
        AUDIENCE_BLUE,
        AUDIENCE_RED,
    }

    public AutoState state = AutoState.BASKET_RED;
    public void setState(AutoState state) {this.state=state;}

    @Override
    public void init() {
        TelemetryMenu whereAreYou = new TelemetryMenu(telemetry, "Where are you?");
        MenuItem pickOne = new MenuItem("Basket Blue");
        MenuItem pickTwo = new MenuItem("Basket Red");
        MenuItem pickThree = new MenuItem("Audience Blue");
        MenuItem pickFour = new MenuItem("Audience Red");
        MenuItem currentlySelected = new MenuItem("Current: BASKET_RED");

        pickOne.setOnClick(() -> setState(AutoState.BASKET_BLUE));
        pickTwo.setOnClick(() -> setState(AutoState.BASKET_RED));
        pickThree.setOnClick(() -> setState(AutoState.AUDIENCE_BLUE));
        pickFour.setOnClick(() -> setState(AutoState.AUDIENCE_RED));

        currentlySelected.setPeriodicUpdate(() -> {
            currentlySelected.setTitle("Current: " + state);
        });

        whereAreYou.addMenuItem(pickOne);
        whereAreYou.addMenuItem(pickTwo);
        whereAreYou.addMenuItem(pickThree);
        whereAreYou.addMenuItem(pickFour);
        whereAreYou.addMenuItem(new MenuSpacer());
        whereAreYou.addMenuItem(currentlySelected);

        currentMenu = whereAreYou;
    }

    @Override
    public void init_loop() {
        currentMenu.update(gamepad1);
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addLine("Final picked: " + state);
        telemetry.update();
    }
}

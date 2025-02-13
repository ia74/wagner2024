package org.firstinspires.ftc.teamcode.opmode.telemetryutil.tests;

import com.pedropathing.util.Timer;
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
        Timer timer = new Timer();

        pickOne.setOnClick(() -> {
            setNumber(1);
            telemetry.speak("Hello");
        });
        final boolean[] speak = {false};
//        pickTwo.setPeriodicUpdate(() -> {
//            if(!speak[0]) {
//                telemetry.speak("hi");
//                speak[0] = true;
//            } else {
//                if(timer.getElapsedTime() > 1000) {
//                    timer.resetTimer();
//                    speak[0] = false;
//                }
//            }
//        });
        telemetry.speak("" +
                        "Not leaving that one in\n" +
                        "That feeling when the knee surgery is tomorrow\n" +
                        "What's up Instagram fans (Uh!)\n" +
                        "Aye I just had my knee surgery done;\n" +
                        "It was not a lot of fun\n" +
                        "I just lied but wait, I'm getting knee surgery!\n" +
                        "\n" +
                        "Alright man, stop moving your knee now, we need to do surgery\n" +
                        "\n" +
                        "Nobody told me Bryce was gonna be my knee surgeon\n" +
                        "Went into ocean and I caught a sturgeon (Uh, uh!)\n" +
                        "\n" +
                        "Received a letter, LiL WiLY says \"rizz\"\n" +
                        "Opened my root beer, do you hear that fizz?\n" +
                        "Oh crap that was the wrong sound effect, but anyways\n" +
                        "\n" +
                        "Why'd you put it on the same beat, bro?\n" +
                        "Give me something better than this\n" +
                        "Ohh, okay, I got you, don't worry\n" +
                        "\n" +
                        "Aye Mr. Tinoco, make that sound again! (I'm not leaving that one in)\n" +
                        "Walked down to the store, that was inconveniencing\n" +
                        "Bryce (Huh? What?)\n" +
                        "Bryce (Huh? What? Uh!)\n" +
                        "Bryce (UH! What? I know!)\n" +
                        "Bryce \n" +
                        "\n" +
                        "They don't know me; salami\n" +
                        "Must be a bumblebee in the industry\n" +
                        "Call me Bryce, the way that I'm pickle, ayy, and\n" +
                        "Call me Bryce, the way that I'm plentiful\n" +
                        "\n" +
                        "Drinkin' water out of a Kirkland water bottle!\n" +
                        "Call me an engine the way that I'm on full throttle!\n" +
                        "And call me rain, the way that I'm oxidizing\n" +
                        "and.. making rust.. (I'm not leaving that one in)\n" +
                        "\n" +
                        "Talk so loud, call me Bryce when I'm yapping\n" +
                        "And y'all already know I suck at rapping\n" +
                        "But atleast I'm never capping (I'm not leaving that one in) \n" +
                        "\n" +
                        "Bryce, bryce, bryce, bryce, bryce, bryce\n" +
                        "Bro, I'm just gonna end the song here bro, I can't come up with any lyrics\n" +
                        "Bye Instagram fans! (Uh!)");

        menu.addMenuItem(pickOne);
        menu.addMenuItem(pickTwo);
    }

    public void setNumber(int i) {picked = i;}

    @Override
    public void init_loop() {
        menu.update(gamepad1);
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("picked", picked);
        if(gamepad1.x) telemetry.speak("hi");
        telemetry.update();
    }
}

package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.util.HashMap;
import java.util.Map;

public class SelectorRegistrar {
    public static String prefix = "Knee Surgery "; // In case the Knee Surgery meme dies, we can change how the OpModes are prefixed here

    /**
     * This is a map of the title (example: "Knee Surgery WHATEVER")
     * to the Blinkin light pattern.
     */
    HashMap<String, RevBlinkinLedDriver.BlinkinPattern> patternHashMap = new HashMap<String, RevBlinkinLedDriver.BlinkinPattern>() {{
        put("Red", RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
        put("Blue", RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
        put("beats_per_minute_ocean_palette", RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_OCEAN_PALETTE);
    }};

    @OpModeRegistrar
    public void register(OpModeManager manager) {
        patternHashMap.forEach((name, pattern) -> {
            // OpModeMeta is a class that is used to tell the Driver Station how to display the OpMode.
            OpModeMeta meta =
                    new OpModeMeta.Builder()
                            .setName(prefix + name)
                            .setSource(OpModeMeta.Source.BLOCKLY) // This isn't important, but I just set the OpModes to look like they're made with block coding because it's funny.
                            .setFlavor(OpModeMeta.Flavor.TELEOP)
                            .build();

            KneeSurgery opmode = new KneeSurgery(); // This will *create* (not initialize like the driver station does) the OpMode & get it set-up.
            opmode.patternToUse = pattern; // Here we will set our pattern.

            manager.register(meta, opmode); // Normally, we'd just do "manager.register(meta, new KneeSurgery())" but we have to declare it as we wanna set a variable (the pattern)
        });
    }
}

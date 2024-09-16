package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.reflection.ReflectionConfig;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.util.HashMap;
import java.util.Map;

public final class TestRegistrar {
    public static boolean enabled = true;

    public static HashMap<String, LinearOpMode> tests = new HashMap<>();
    public TestRegistrar() {}

    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        if(!enabled) return;
        tests.put("Claw", new TestClaw());

        for(Map.Entry<String, LinearOpMode> set : tests.entrySet()) {
            OpModeMeta meta = new OpModeMeta.Builder()
                    .setName(set.getKey())
                    .setFlavor(OpModeMeta.Flavor.TELEOP)
                    .setGroup("Mechanism Tests")
                    .setSource(OpModeMeta.Source.BLOCKLY)
                    .build();
            manager.register(meta, set.getValue());
        }
        FtcDashboard.getInstance().withConfigRoot(configRoot -> {
            for(Map.Entry<String, LinearOpMode> set : tests.entrySet()) {
                configRoot.putVariable(set.getKey(), ReflectionConfig.createVariableFromClass(set.getValue().getClass()));
            }
        });
    }
}

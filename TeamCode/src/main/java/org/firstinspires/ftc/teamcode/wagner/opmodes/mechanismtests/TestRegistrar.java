package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.reflection.ReflectionConfig;
import com.acmerobotics.dashboard.config.variable.CustomVariable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.opmodes.NGTeleOp;

import java.util.HashMap;
import java.util.Map;

public final class TestRegistrar {
    public static final boolean enabled = GlobalStorage.testsEnabled;

    public static HashMap<String, LinearOpMode> tests = new HashMap<>();
    public TestRegistrar() {}

    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        if(!enabled) return;
        tests.put("TUNER: ARM", new ArmTuner());
        tests.put("Claw", new TestClaw());
        tests.put("Arm", new TestArm());
        tests.put("Hanger", new TestHanger());
        tests.put("RoadRunner", new TestRoadRunner());
        tests.put("Motor Direction", new TestMotors());


        for(Map.Entry<String, LinearOpMode> set : tests.entrySet()) {
            OpModeMeta meta = new OpModeMeta.Builder()
                    .setName("Test " + set.getKey())
                    .setFlavor(GlobalStorage.testOpModeFlavor)
                    .setGroup(GlobalStorage.testOpModeGroup)
                    .setSource(GlobalStorage.testOpModeSource)
                    .build();
            manager.register(meta, set.getValue());
        }
        FtcDashboard.getInstance().withConfigRoot(configRoot -> {
            for(Map.Entry<String, LinearOpMode> set : tests.entrySet()) {
                CustomVariable cv = ReflectionConfig.createVariableFromClass(set.getValue().getClass());
                if(cv.size() == 0) return;
                configRoot.putVariable(
                        set.getValue().getClass().getSimpleName(),
                        cv
                );
            }
        });
    }
}

package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.reflection.ReflectionConfig;
import com.acmerobotics.dashboard.config.variable.CustomVariable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

public final class SillyTeleOpRegistrar {
    public static String[] names = {
        "808 Essentially Means Crashout",
        "Estoy Gay: The Return",
        "New Gen TeleOp",
        "Spicy Bucket lives on"
    };
    public SillyTeleOpRegistrar() {}

    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        String rando = names[(int) Math.floor(Math.random() * names.length)];

        OpModeMeta meta = new OpModeMeta.Builder()
                    .setName(rando)
                    .setFlavor(OpModeMeta.Flavor.TELEOP)
                    .setGroup("TeleOp")
                    .setSource(OpModeMeta.Source.BLOCKLY)
                    .build();
        manager.register(meta, new NGTeleOp());
        CustomVariable cv = ReflectionConfig.createVariableFromClass(NGTeleOp.class);
        if(cv.size() == 0) return;
        FtcDashboard.getInstance().withConfigRoot(configRoot -> {
            configRoot.putVariable(meta.name, cv);
        });
    }
}

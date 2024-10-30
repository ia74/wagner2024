package org.firstinspires.ftc.teamcode.wagner;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

public class GlobalStorage {
    public static boolean testsEnabled = true;
    public static String testOpModeGroup = "tuners";
    public static OpModeMeta.Flavor testOpModeFlavor = OpModeMeta.Flavor.AUTONOMOUS;
    public static OpModeMeta.Source testOpModeSource = OpModeMeta.Source.BLOCKLY;
    public static Pose2d pose = new Pose2d(0,0,0);
}

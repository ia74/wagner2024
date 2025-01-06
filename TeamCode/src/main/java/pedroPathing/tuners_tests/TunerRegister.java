package pedroPathing.tuners_tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

public final class TunerRegister {
    boolean areTunersEnabled = true; // TODO: Turn this off @ competition, usually
    private TunerRegister() {}

    static OpModeMeta metaFor(Class<OpMode> opMode) {
        return new OpModeMeta.Builder()
                .setName(opMode.getSimpleName())
                .setGroup("Automatic Tuners")
                .setFlavor(OpModeMeta.Flavor.AUTONOMOUS)
                .build();
    }
}

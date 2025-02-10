package org.firstinspires.ftc.teamcode.trilobytes.impl;

import org.firstinspires.ftc.teamcode.trilobytes.Trilobyte;
import org.firstinspires.ftc.teamcode.trilobytes.TrilobyteState;

public class LambdaTrilobyte extends Trilobyte {
    public enum Type {
        ONCE,
        INSTANT_ON_ONCE,
        PERIODIC,
    };
    private final Runnable function;
    private final Type type;
    public LambdaTrilobyte(Runnable function, Type type) {
        super(TrilobyteState.OFF, "Lambda");
        this.function = function;
        this.type = type;

    }

    @Override
    public TrilobyteState runOnce() {
        if(type == Type.INSTANT_ON_ONCE || type == Type.ONCE) {
            function.run();
            return type == Type.INSTANT_ON_ONCE ? TrilobyteState.ON : TrilobyteState.NOOP;
        }
        return TrilobyteState.NOOP;
    }

    @Override
    public TrilobyteState runPeriodic() {
        if(type == Type.PERIODIC) {
            function.run();
            return TrilobyteState.ON;
        }
        return TrilobyteState.NOOP;
    }
}

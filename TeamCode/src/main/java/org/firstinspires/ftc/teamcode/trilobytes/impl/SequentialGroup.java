package org.firstinspires.ftc.teamcode.trilobytes.impl;

import org.firstinspires.ftc.teamcode.trilobytes.Trilobyte;
import org.firstinspires.ftc.teamcode.trilobytes.TrilobyteState;

public class SequentialGroup extends Trilobyte {
    public SequentialGroup(Trilobyte... commands) {
        super(TrilobyteState.NOOP, "Sequential Grouping");
    }
}

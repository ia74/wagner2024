package org.firstinspires.ftc.teamcode.trilobytes;

public enum TrilobyteState {
    ON(1),
    FINISHED(1),
    NOOP(0),
    OFF(0),
    UNKNOWN(-1),
    DISABLED(-100),
    IN_PROGRESS(2),
    INITIALIZED(3),


    ;
    public final int truthfulState;

    TrilobyteState(int truthfulState) {
        this.truthfulState = truthfulState;
    }
}

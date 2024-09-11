package org.firstinspires.ftc.teamcode.wagner.mechanisms;

public enum MechanismState {
    CLOSED(0),
    OPEN(1),
    UNKNOWN(-1),
    UNINITIALIZED(-2),
    IN_USE(-3);

    public final int state;
    private MechanismState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}

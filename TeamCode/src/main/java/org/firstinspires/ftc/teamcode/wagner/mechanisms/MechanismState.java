package org.firstinspires.ftc.teamcode.wagner.mechanisms;

public enum MechanismState {
    CLOSED(0),
    UNEXTENDED(0),
    OPEN(1),
    EXTENDED(1),
    UNKNOWN(-1),
    UNINITIALIZED(-2),
    IN_USE(-3),
    REQUESTED(-4);

    public final int state;
    private MechanismState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public boolean only(MechanismState state) {
        return this.state == state.getState();
    }

    public boolean not(MechanismState state) {
        return this.state != state.getState();
    }
}

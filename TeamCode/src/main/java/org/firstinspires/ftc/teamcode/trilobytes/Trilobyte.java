package org.firstinspires.ftc.teamcode.trilobytes;

public class Trilobyte {
    private TrilobyteState state;
    private boolean postRunRemoval = false;
    private final String name;

    public Trilobyte(TrilobyteState state, String name) {
        this.state = state;
        this.name = name;
    }

    public TrilobyteState runOnce() {
        return TrilobyteState.NOOP;
    }

    public TrilobyteState runPeriodic() {
        return TrilobyteState.NOOP;
    }

    public String getName() {
        return name;
    }

    public boolean getPostRunRemoval() {
        return postRunRemoval;
    }

    public void setPostRunRemoval(boolean postRunRemoval) {
        this.postRunRemoval = postRunRemoval;
    }

    public boolean setState(TrilobyteState state) {
        if(state == TrilobyteState.IN_PROGRESS) return false;
        this.state = state;
        return true;
    }

    public TrilobyteState getState() {
        return state;
    }
}

package org.firstinspires.ftc.teamcode.trilobytes.impl.usages;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.PathChain;

import org.firstinspires.ftc.teamcode.trilobytes.Trilobyte;
import org.firstinspires.ftc.teamcode.trilobytes.TrilobyteState;

public class PathFollower extends Trilobyte {
    private final Follower follower;
    private PathChain pathChain;

    public PathFollower(Follower follower) {
        super(TrilobyteState.INITIALIZED, "Pedro Path Follower");
        this.follower = follower;
    }

    public void follow(PathChain path) {
        this.pathChain = path;
    }

    @Override
    public TrilobyteState runOnce() {
        follower.followPath(pathChain, true);
        return TrilobyteState.IN_PROGRESS;
    }

    @Override
    public TrilobyteState runPeriodic() {
        follower.update();
        if(!follower.isBusy()) {
            setPostRunRemoval(true);
            return TrilobyteState.FINISHED;
        }
        return TrilobyteState.IN_PROGRESS;
    }
}

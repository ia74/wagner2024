package org.firstinspires.ftc.teamcode.opmode.autonomous.util;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class PathChainHelper {
    Follower follower;
    public PathChainHelper(Follower follower) {
        this.follower = follower;
    }

    public PathChain fromTwoPoints(Pose point1, Pose point2, PathChainHelperType type) {
        PathBuilder pathBuilder = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        new Point(point1), new Point(point2)
                )));
        if(type == PathChainHelperType.LINEAR) {
            pathBuilder = pathBuilder.setLinearHeadingInterpolation(point1.getHeading(), point2.getHeading());
        } else if(type == PathChainHelperType.CONSTANT) {
            pathBuilder = pathBuilder.setConstantHeadingInterpolation(point2.getHeading());
        }
        return pathBuilder.build();
    }
}

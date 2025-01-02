package org.firstinspires.ftc.teamcode.opmode.autonomous.util;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Returns a PedroPathing Follower, but with the added isInRangeOf method.
 */
public class FollowerEx extends Follower {
    public FollowerEx(HardwareMap hardwareMap) {
        super(hardwareMap);
    }
    public boolean isInRangeOf(Pose pose) {
        return this.getPose().getX() > (pose.getX() - 3) &&
                this.getPose().getY() > (pose.getY() - 3);
    }
}

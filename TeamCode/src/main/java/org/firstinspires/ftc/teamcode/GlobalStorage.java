package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

@Config
public class GlobalStorage {
    public static Pose currentPose = new Pose(0,0, Math.toRadians(0));
}

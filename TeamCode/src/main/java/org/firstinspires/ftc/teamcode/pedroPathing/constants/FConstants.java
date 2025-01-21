package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizers;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;

import org.firstinspires.ftc.teamcode.PartsMap;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.THREE_WHEEL;
        FollowerConstants.leftFrontMotorName = PartsMap.DRIVE_FL.toString();
        FollowerConstants.leftRearMotorName = PartsMap.DRIVE_BL.toString();
        FollowerConstants.rightFrontMotorName = PartsMap.DRIVE_FR.toString();
        FollowerConstants.rightRearMotorName = PartsMap.DRIVE_BR.toString();

        FollowerConstants.xMovement = 51.4966;
        FollowerConstants.yMovement = 42.142;

        FollowerConstants.forwardZeroPowerAcceleration = -41.94002244243403;
        FollowerConstants.lateralZeroPowerAcceleration = -82.94;
        FollowerConstants.zeroPowerAccelerationMultiplier = 5;

        FollowerConstants.mass = 13.608; // In kg
        FollowerConstants.centripetalScaling = 0.00059;

        FollowerConstants.translationalPIDFCoefficients = new CustomPIDFCoefficients(
                0.17,
                0,
                0,
                0);
        FollowerConstants.headingPIDFCoefficients = new CustomPIDFCoefficients(
                2.5,
                0,
                0,
                0);

        FollowerConstants.drivePIDFCoefficients = new CustomFilteredPIDFCoefficients(
                0.015,
                0,
                0,
                0.6,
                0);

        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndTimeoutConstraint = 500;
    }
}

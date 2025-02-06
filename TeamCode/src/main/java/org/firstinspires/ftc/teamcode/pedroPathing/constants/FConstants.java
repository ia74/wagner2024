package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.THREE_WHEEL;

        FollowerConstants.leftFrontMotorName = PartsMap.DRIVE_FL.toString();
        FollowerConstants.leftRearMotorName = PartsMap.DRIVE_BL.toString();
        FollowerConstants.rightFrontMotorName = PartsMap.DRIVE_FR.toString();
        FollowerConstants.rightRearMotorName = PartsMap.DRIVE_BR.toString();

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 16.7;

        FollowerConstants.xMovement = 55.678203239043;
        FollowerConstants.yMovement = 40.608821307914;

        FollowerConstants.forwardZeroPowerAcceleration = -40.770043606209;
        FollowerConstants.lateralZeroPowerAcceleration = -99.4558788;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(
                0.2,
                0,
                0,
                0);
        FollowerConstants.headingPIDFCoefficients.setCoefficients(
                2.5,
                0,
                0,
                0);

        FollowerConstants.drivePIDFCoefficients.setCoefficients(
                0.015,
                0,
                0,
                0.6,
                0);


        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(2,0,0.1,0); // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
        FollowerConstants.centripetalScaling = 0.00059;

        FollowerConstants.pathEndTimeoutConstraint = 50;
        FollowerConstants.pathEndTValueConstraint = 0.985;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;

        FollowerConstants.useBrakeModeInTeleOp = true;
        FollowerConstants.useVoltageCompensationInAuto = true;
        FollowerConstants.nominalVoltage = 12.0;
    }
}

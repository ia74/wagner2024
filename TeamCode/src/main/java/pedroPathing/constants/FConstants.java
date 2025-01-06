package pedroPathing.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class FConstants {
    public static Pose currentPose = new Pose(0,0, Math.toRadians(0));
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

        FollowerConstants.mass = 14.0614;

        FollowerConstants.xMovement = 50.73060552762334;
        FollowerConstants.yMovement = 32.19254896295486;

        FollowerConstants.forwardZeroPowerAcceleration = -43.307074641927734;
        FollowerConstants.lateralZeroPowerAcceleration = -103.85635630850656;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(
                0.123,
                0,
                0,
                0);

        FollowerConstants.headingPIDFCoefficients.setCoefficients(
                2,
                0,
                0,
                0);

        FollowerConstants.drivePIDFCoefficients.setCoefficients(
                0.014,
                0,
                0.00003,
                0.6,
                0);

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
        FollowerConstants.centripetalScaling = 0.00043;

        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.useSecondaryDrivePID = false;

        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
        FollowerConstants.useBrakeModeInTeleOp = true;
    }
}

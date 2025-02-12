package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.PartsMap;
import org.firstinspires.ftc.teamcode.opmode.subsystem.pid.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.opmode.subsystem.pid.PIDFController;

@Config
public class Arm extends Subsystem {
    public static PIDFCoefficients slidesCoefficients = new PIDFCoefficients(
            0.006,
            0,
            0.00001,
            0.000005
    );
    public static double slidesMaximumPositionLimit = 3800;
    public static double slidesTargetPosition = 0;

    public static PIDFCoefficients shoulderCoefficients = new PIDFCoefficients(
            0.005,
            0,
            0,
            0
    );
    public static double shoulderMaximumPositionLimit = 2500;
    public static double shoulderTargetPosition = 0;

    public DcMotor left;
    public DcMotor right;
    public DcMotor shoulder;
    public PIDFController slidesPid;
    public PIDFController shoulderPid;

    public Arm(HardwareMap hardwareMap) {
        super(hardwareMap);
        left = hardwareMap.get(DcMotor.class, PartsMap.ARM_LEFT.toString());
        right = hardwareMap.get(DcMotor.class, PartsMap.ARM_RIGHT.toString());
        shoulder = hardwareMap.get(DcMotor.class, PartsMap.ARM_SHOULDER.toString());

        shoulder.setDirection(DcMotorSimple.Direction.FORWARD);
        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        Subsystem.initializeMotors(left, right, shoulder); // Reset, run with encoder, and set ZPB to FLOAT

        slidesPid = new PIDFController(slidesCoefficients, slidesMaximumPositionLimit);
        slidesPid.setMaxPosition(slidesMaximumPositionLimit);
        slidesPid.setTargetPosition(getArmPosition());

        shoulderPid = new PIDFController(shoulderCoefficients, shoulderMaximumPositionLimit);
        shoulderPid.setTargetPosition(getShoulderPosition());
    }

    public void setShoulderTargetPosition(double spos) {
        Arm.shoulderTargetPosition = spos;
    }

    public void setSlidesTargetPosition(double tpos) {
        Arm.slidesTargetPosition = tpos;
    }

    public double getArmPosition() {
        return (left.getCurrentPosition() + right.getCurrentPosition()) / 2.0;
    }

    public void setSlidePower(double pwr) {
        left.setPower(pwr);
        right.setPower(pwr);
    }

    public void setShoulderPower(double power) {this.shoulder.setPower(power);}
    public double getShoulderPosition() {
        return shoulder.getCurrentPosition();
    }

    public void individuallyUpdateSlides() {
        slidesPid.setTargetPosition(slidesTargetPosition);
        double power = slidesPid.calculate(getArmPosition());
        setSlidePower(power);
    }
    public void individuallyUpdateShoulder() {
        shoulderPid.setTargetPosition(shoulderTargetPosition);
        double power2 = shoulderPid.calculate(getShoulderPosition());
        setShoulderPower(power2);
    }

    public boolean isPositionWithinTolerance(double currentPosition, double tolerance, double pos) {
        return Math.abs(pos - currentPosition) <= tolerance;
    }

    public void update() {
        individuallyUpdateSlides();
        individuallyUpdateShoulder();
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Arm] --\n" +
                Subsystem.pidControllerIfo(slidesPid, "Slides", fmt(left.getPower(), right.getPower())) + "\n" +
                Subsystem.pidControllerIfo(shoulderPid, "Shoulder", String.valueOf(shoulder.getPower())) + "\n" +
                Subsystem.motorIfo(left, "Motor Left") + "\n" +
                Subsystem.motorIfo(right, "Motor Right") + "\n" +
                Subsystem.motorIfo(shoulder, "Shoulder") + "\n";
    }

    public String fmt(double... parts) {
        StringBuilder out = new StringBuilder();
        for (double part: parts) {
            out.append(part).append("/");
        }
        return out.toString();
    }
}

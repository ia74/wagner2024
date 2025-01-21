package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class Arm extends Subsystem {
    public static double skP = 0.005;
    public static double skI = 0;
    public static double skD = 0.00001;
    public static double skF = 0.000005;
    public static double smaxPosition = 3725;
    public static double stargetPosition = 0;

    public static double shoulderkP = 0.005;
    public static double shoulderkI = 0;
    public static double shoulderkD = 0;
    public static double shoulderkF = 0;
    public static double shoulderkmaxPosition = 2500;
    public static double shoulderktargetPosition = 0;


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
        shoulder.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        Subsystem.resetMotor(left);
        Subsystem.resetMotor(right);
        Subsystem.resetMotor(shoulder);

        slidesPid = new PIDFController(skP, skI, skD, skF);
        slidesPid.setMaxPosition(smaxPosition);
        slidesPid.setkP(skP);
        slidesPid.setkI(skI);
        slidesPid.setkD(skD);
        slidesPid.setkF(skF);
        stargetPosition = getArmPosition();

        shoulderPid = new PIDFController(shoulderkP, shoulderkI, shoulderkD, shoulderkF);
        shoulderPid.setMaxPosition(shoulderkmaxPosition);
        shoulderPid.setkP(shoulderkP);
        shoulderPid.setkI(shoulderkI);
        shoulderPid.setkD(shoulderkD);
        shoulderPid.setkF(shoulderkF);
        shoulderktargetPosition = getShoulderPosition();
    }

    public void setShoulderTargetPosition(double spos) {
        Arm.shoulderktargetPosition = spos;
        this.shoulderPid.setSetpoint(spos);
    }

    public void setSlidesTargetPosition(double tpos) {
        Arm.stargetPosition = tpos;
        this.slidesPid.setSetpoint(tpos);
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

//    public void teleopControl(double gamepadPower, boolean isSlides) {
//        double currentPosition = isSlides ? getArmPosition() : getShoulderPosition();
//        double maximumPosition = isSlides ? Arm.smaxPosition : shoulderkmaxPosition;
//        if(isSlides)
//        if(Math.abs(gamepadPower) > 0.1) {
//            if(currentPosition > maximumPosition - 30) {
//                if (!(shoulderPower > 0.1)) {
//                    arm.setShoulderPower(shoulderPower);
//                } else {
//                    arm.individuallyUpdateShoulder();
//                }
//            } else {
//                arm.setShoulderPower(shoulderPower);
//            }
//        } else {
//            arm.individuallyUpdateShoulder();
//        }
//    }

    public void individuallyUpdateSlides() {
        slidesPid.setSetpoint(stargetPosition);
        double power = slidesPid.calculate(getArmPosition());
        setSlidePower(power);
    }
    public void individuallyUpdateShoulder() {
        shoulderPid.setSetpoint(shoulderktargetPosition);
        double power2 = shoulderPid.calculate(getShoulderPosition());
        setShoulderPower(power2);
    }

    public void update() {
        individuallyUpdateSlides();
        individuallyUpdateShoulder();
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Arm] --\n" +
                "SPosition: " + getArmPosition() + "\n" +
                "STargetPosition: " + stargetPosition + "\n\n" +
                "KPosition: " + getShoulderPosition() + "\n" +
                "kTargetPosition: " + shoulderktargetPosition + "\n" +

                Subsystem.motorIfo(left, "Motor Left") + "\n" +
                Subsystem.motorIfo(right, "Motor Right") + "\n" +
                Subsystem.motorIfo(shoulder, "Shoulder") + "\n";
    }
}

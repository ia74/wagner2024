package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Arm implements Mechanism {
    public DcMotor left;
    public DcMotor right;
//    public CRServo elbow;
    public DcMotor shoulder;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, PartsMap.ARM_LEFT.toString());
        right = hardwareMap.get(DcMotor.class, PartsMap.ARM_RIGHT.toString());

//        elbow = hardwareMap.get(CRServo.class, PartsMap.ARM_ELBOW.toString());
        shoulder = hardwareMap.get(DcMotor.class, PartsMap.ARM_SHOULDER.toString());
        shoulder.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public double getArmPosition() {
        return (left.getCurrentPosition() + right.getCurrentPosition()) / 2.0;
    }
    public double getShoulderPosition() {
        return shoulder.getCurrentPosition();
    }
    public void slidePower(double pwr) {
        left.setPower(pwr);
        right.setPower(pwr);
    }
    // ARM: Shoulder Controls
    public void setShoulder(double power) {this.shoulder.setPower(power);}
    // ARM: Elbow Controls
//    public void elbowPower(double power) {
//        this.elbow.setPower(power);
//    }
    @NonNull
    public String toString() {
        return "-- [Mechanism: Arm] --\n" +
                Mechanism.motorIfo(left, "Motor Left") + "\n" +
                Mechanism.motorIfo(right, "Motor Right") + "\n" +
                Mechanism.motorIfo(shoulder, "Shoulder") + "\n";
//                Mechanism.servoIfo(elbow, "Elbow") + "\n";
    }
}

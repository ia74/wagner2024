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
    public CRServo elbow;
    public CRServo shoulder;
    public MechanismPID pidController;
    public static double targetPosition = 0.0;
    public static double kP = 0.1;
    public static double kI = 0.01;
    public static double kD = 0.01;
    public static double gravityCompensation = 0.2;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, PartsMap.ARM_LEFT.toString());
        right = hardwareMap.get(DcMotor.class, PartsMap.ARM_RIGHT.toString());

        elbow = hardwareMap.get(CRServo.class, PartsMap.ARM_ELBOW.toString());
        shoulder = hardwareMap.get(CRServo.class, PartsMap.ARM_SHOULDER.toString());

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

        pidController = new MechanismPID(kP, kI, kD, gravityCompensation);
    }

    // ARM: Arm Controls

    public void setTargetPosition(double position) {
        targetPosition = position;
        pidController.reset();
    }

    public void holdPosition() {
        double currentPosition = getArmPosition();
        double power = pidController.calculate(targetPosition, currentPosition);
        slidePower(power);
    }

    public void holdPositionWithJoystick(double joystickInput) {
        double currentPosition = getArmPosition();

        double gravityCompensationPower = pidController.calculate(targetPosition, currentPosition);

        double power = joystickInput + gravityCompensationPower;

        slidePower(power);
    }

    public double getArmPosition() {
        return (left.getCurrentPosition() + right.getCurrentPosition()) / 2.0;
    }

    public void slidePower(double pwr, double threshold) {
        if(Math.abs(pwr) > threshold) slidePower(pwr);
    }
    public void slidePower(double pwr) {
        left.setPower(pwr);
        right.setPower(pwr);
    }

    // ARM: Shoulder Controls
    public void setShoulder(double power) {this.shoulder.setPower(power);}
    public void setShoulder(double power, double threshold) {
        if(Math.abs(power) > threshold) setShoulder(power);
    }

    // ARM: Elbow Controls
    public void elbowPower(double power) {
        this.elbow.setPower(power);
    }
    public void elbowPower(double power, double threshold) {
        if(Math.abs(power) > threshold) elbowPower(power);
    }

    @NonNull
    public String toString() {
        return "-- [Mechanism: Arm] --\n" +
                Mechanism.motorIfo(left, "Motor Left") + "\n" +
                Mechanism.motorIfo(right, "Motor Right") + "\n" +
                Mechanism.servoIfo(elbow, "Elbow") + "\n" +
                Mechanism.servoIfo(shoulder, "Shoulder") + "\n";
    }
}

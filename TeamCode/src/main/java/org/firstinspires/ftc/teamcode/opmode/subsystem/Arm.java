package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class Arm extends Subsystem {
    public static int num = 0;
    public DcMotor left;
    public DcMotor right;
    public DcMotor shoulder;

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

    @NonNull
    public String toString() {
        return "-- [Mechanism: Arm] --\n" +
                "Position: " + getArmPosition() + "\n" +
                Subsystem.motorIfo(left, "Motor Left") + "\n" +
                Subsystem.motorIfo(right, "Motor Right") + "\n" +
                Subsystem.motorIfo(shoulder, "Shoulder") + "\n";
    }
}

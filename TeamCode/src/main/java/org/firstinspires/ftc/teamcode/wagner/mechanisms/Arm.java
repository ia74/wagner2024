package org.firstinspires.ftc.teamcode.wagner.mechanisms;

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
    public ElapsedTime _timer;
    public DcMotor left;
    public DcMotor right;
    public CRServo elbow;
    public CRServo shoulder;

    public static double shoulderPos = 0;

    @Override
    public void init(HardwareMap hardwareMap) {
        _timer = new ElapsedTime();
        left = hardwareMap.get(DcMotor.class, PartsMap.ARM_LEFT.toString());
        right = hardwareMap.get(DcMotor.class, PartsMap.ARM_RIGHT.toString());

        elbow = hardwareMap.get(CRServo.class, PartsMap.ARM_ELBOW.toString());
        shoulder = hardwareMap.get(CRServo.class, PartsMap.ARM_SHOULDER.toString());

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public void slidePowerThreshold(double pwr, double threshold) {
        if(pwr > threshold) slidePower(pwr);
    }

    public void slidePower(double pwr) {
        left.setPower(pwr);
        right.setPower(pwr);
    }

    public void setShoulder(double pos) {
        this.shoulder.setPower(pos);
        Arm.shoulderPos=pos;
    }
    public void rawElbowPower(double power) {
        this.elbow.setPower(power);
    }

    public void setElbowPowerFor(double power, double ms) {
        _timer.reset();
        while(_timer.milliseconds() < ms) rawElbowPower(power);
        rawElbowPower(0);
    }

}

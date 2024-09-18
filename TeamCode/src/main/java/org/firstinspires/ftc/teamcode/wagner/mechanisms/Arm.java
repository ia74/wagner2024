package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Arm implements Mechanism {
    public DcMotor left;
    public DcMotor right;
    public Servo elbow;
    public Servo shoulder;
    public Servo wrist;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, PartsMap.ARM_LEFT.toString());
        right = hardwareMap.get(DcMotor.class, PartsMap.ARM_RIGHT.toString());

        elbow = hardwareMap.get(Servo.class, PartsMap.ARM_ELBOW.toString());
        shoulder = hardwareMap.get(Servo.class, PartsMap.ARM_SHOULDER.toString());
        wrist = hardwareMap.get(Servo.class, PartsMap.ARM_WRIST.toString());

        //TODO: REVERSE
        right.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void slidePower(double pwr) {
        left.setPower(pwr);
        right.setPower(pwr);
    }

    public void setShoulder(double pos) {this.shoulder.setPosition(pos);}
    public void setElbow(double pos) {this.elbow.setPosition(pos);}
    public void setWrist(double pos) {this.wrist.setPosition(pos);}

}

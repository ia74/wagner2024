package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Arm implements Mechanism {
    public DcMotor left;
    public DcMotor right;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, PartsMap.ARM_LEFT.toString());
        right = hardwareMap.get(DcMotor.class, PartsMap.ARM_RIGHT.toString());
        //TODO: REVERSE
        right.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double pwr) {
        left.setPower(pwr);
        right.setPower(pwr);
    }

}

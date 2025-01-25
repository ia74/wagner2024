package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class DeployHooks extends Subsystem{
    Servo deployerLeft;
    Servo deployerRight;

    public static double currentPosition = 0.0;
    public static double startPosition = 0.0;
    public static double deployPosition = 1.0;

    public DeployHooks(HardwareMap hardwareMap) {
        super(hardwareMap);
        deployerLeft = hardwareMap.get(Servo.class, PartsMap.HANG_DEPLOYER_LEFT.toString());
        deployerRight = hardwareMap.get(Servo.class, PartsMap.HANG_DEPLOYER_RIGHT.toString());
        deployerLeft.setDirection(Servo.Direction.REVERSE);
    }

    public void update(double position) {
        currentPosition = position;
        deployerLeft.setPosition(position);
        deployerRight.setPosition(position);
    }

    public void start() {
        update(startPosition);
    }

    public void deploy() {
        update(deployPosition);
    }

    @NonNull
    @Override
    public String toString() {
        return "Deployer L/R Current: " + deployerLeft.getPosition() +"/" + deployerRight.getPosition() +"\n" +
                "Preset/currentPosition = " + currentPosition + "\n" +
                "Preset/startPosition = " + startPosition + "\n" +
                "Preset/deployPosition = " + deployPosition+ "\n";
    }
}

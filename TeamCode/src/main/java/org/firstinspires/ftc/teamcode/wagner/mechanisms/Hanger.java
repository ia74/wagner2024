package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.PartsMap;

@Config
public class Hanger implements Mechanism {
    public DcMotor motor;
    public static int OPEN_POSITION = 1000;
    public static int CLOSED_POSITION = 0;
    public static double power = 1.0;
    public static boolean activation = false;
    public static MechanismState state = MechanismState.UNEXTENDED;

    @Override
    public void init(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, PartsMap.HANGER.toString());
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void internalGoTo(int position) {
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
    }

    public MechanismState unextend() {
        if(state == MechanismState.IN_USE) return state;
        activation = false;
        internalGoTo(CLOSED_POSITION);
        while(motor.isBusy()) {
            state = MechanismState.IN_USE;
        }
        motor.setPower(0);
        state = MechanismState.UNEXTENDED;
        return state;
    }

    public MechanismState extend() {
        if(state == MechanismState.IN_USE) return state;
        activation = true;
        internalGoTo(OPEN_POSITION);
        while(motor.isBusy()) {
            state = MechanismState.IN_USE;
        }
        motor.setPower(0);
        state = MechanismState.EXTENDED;
        return state;
    }
}

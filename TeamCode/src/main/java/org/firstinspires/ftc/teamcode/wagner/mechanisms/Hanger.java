package org.firstinspires.ftc.teamcode.wagner.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
public class Hanger implements Mechanism {
    DcMotor motor;
    public static int OPEN_POSITION = 1000;
    public static int CLOSED_POSITION = 0;
    public static boolean activation = false;
    public static MechanismState state = MechanismState.UNINITIALIZED;

    @Override
    public void init(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "hanger");
    }

    public MechanismState close() {
        if(state == MechanismState.IN_USE) return state;
        activation = true;
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setTargetPosition(CLOSED_POSITION);
        while(motor.isBusy()) {
            state = MechanismState.IN_USE;
        }
        state = MechanismState.CLOSED;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        return state;
    }

    public MechanismState open() {
        if(state == MechanismState.IN_USE) return state;
        activation = true;
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setTargetPosition(OPEN_POSITION);
        while(motor.isBusy()) {
            state = MechanismState.IN_USE;
        }
        state = MechanismState.OPEN;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        return state;
    }

    @Override
    public void run(Gamepad gamepad1, Gamepad gamepad2, MecanumDrive drive, Telemetry telemetry) {
        if(gamepad1.b && state != MechanismState.IN_USE) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition(activation ? OPEN_POSITION : CLOSED_POSITION);
            while(motor.isBusy()) {
                state = MechanismState.IN_USE;
            }
            state = activation ? MechanismState.OPEN : MechanismState.CLOSED;
        } else {
            telemetry.addData("Hanger", "Please wait, the motor is currently running.");
        }
    }
}

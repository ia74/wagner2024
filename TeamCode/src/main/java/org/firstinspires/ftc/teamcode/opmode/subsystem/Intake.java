package org.firstinspires.ftc.teamcode.opmode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class Intake extends Subsystem{
    public enum IntakeState {
        OFF,
        INTAKE,
        OUTTAKE
    };
    private IntakeState intakeState = IntakeState.OFF;
    CRServo intake;

    public Intake(HardwareMap hardwareMap) {
        super(hardwareMap);
        intake = hardwareMap.get(CRServo.class, PartsMap.INTAKE.toString());
    }

    public void setIntakeState(IntakeState state) {
        if(intakeState != state) {
            switch(state) {
                case OFF: intake.setPower(0); break;
                case INTAKE: intake.setPower(1); break;
                case OUTTAKE: intake.setPower(-1); break;
            }
            intakeState = state;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "-- [Mechanism: Intake]--\n" +
                Subsystem.servoIfo(intake, "Intake") + "\n";
    }
}

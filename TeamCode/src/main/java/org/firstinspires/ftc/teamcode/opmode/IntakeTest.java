package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Intake;

@TeleOp(name = "Intake Test (uses teleop)")
public class IntakeTest extends KneeSurgery{
    Intake intake;
    public void externalFunctionInit() {
        intake = new Intake(hardwareMap);
    }

    public void externalFunctionLoop() {
        if(gamepad1.dpad_up) {
            intake.setIntakeState(Intake.IntakeState.INTAKE);
        } else if(gamepad1.dpad_down) {
            intake.setIntakeState(Intake.IntakeState.OUTTAKE);
        } else {
            intake.setIntakeState(Intake.IntakeState.OFF);
        }

        telemetry.addLine("Gamepad 1 DPAD UP: Intake/INTAKE");
        telemetry.addLine("Gamepad 1 DPAD DOWN: Intake/OUTTAKE");
        telemetry.addLine();

        telemetry.addLine(intake.toString());
    }
}

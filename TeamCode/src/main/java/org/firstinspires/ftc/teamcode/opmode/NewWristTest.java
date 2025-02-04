package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.opmode.subsystem.NewClaw;

@TeleOp(name = "New Wrist Test (ONLY)")
public class NewWristTest extends OpMode {
    NewClaw claw;
    public void init() {
        claw = new NewClaw(hardwareMap);
    }
    public void loop() {
        if(gamepad1.triangle) claw.setWristTargetPosition(0);
        if(gamepad1.circle) claw.setWristTargetPosition((int) (NewClaw.wristMaximumPositionLimit / 2));
        if(gamepad1.cross) claw.setWristTargetPosition((int) (NewClaw.wristMaximumPositionLimit));
        telemetry.addLine("GAMEPAD 1 TRIANGLE: Position 0 (START)");
        telemetry.addLine("GAMEPAD 1 CIRCLE: Half of max limit (MIDDLE ISH?)");
        telemetry.addLine("GAMEPAD 1 CROSS: Max limit (END)");
        telemetry.addLine(claw.toString());
        claw.updateWrist();
    }
}

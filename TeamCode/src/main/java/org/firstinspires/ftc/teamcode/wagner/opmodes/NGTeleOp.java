package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepad;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepadPressables.Trigger;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepadPressables.Button;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.MechanismState;

@Config
@TeleOp(name = "New Gen TeleOp", group = "estoy")
public class NGTeleOp extends LinearOpMode {
    public static double clawOpenPosition = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        Claw claw = new Claw();
        Hanger hanger = new Hanger();
        Arm arm = new Arm();

        arm.init(hardwareMap);
        claw.init(hardwareMap);
        hanger.init(hardwareMap);

        NGGamepad ng_gamepad1 = new NGGamepad(gamepad1);
        NGGamepad ng_gamepad2 = new NGGamepad(gamepad2);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            PoseVelocity2d input = new PoseVelocity2d(
                    new Vector2d(
                            ng_gamepad1.left_stick_x(),
                            ng_gamepad1.left_stick_y()
                    ),
                    ng_gamepad1.right_stick_x()
            );

            arm.slidePowerThreshold(ng_gamepad2.left_stick_y(), 0.2);
            arm.setElbow(ng_gamepad2.right_stick_y());

            if(ng_gamepad1.is(Trigger.RIGHT_TRIGGER))
                claw.openClaw(clawOpenPosition);
            else
                claw.openClaw(0);

            if(ng_gamepad1.is(Button.B) &&
                    Hanger.state.not(MechanismState.IN_USE) ||
                    Hanger.state.not(MechanismState.REQUESTED)) {
                Hanger.state = MechanismState.REQUESTED;
            } else {
                telemetry.addData("Hanger", "Please wait, the motor is currently running.");
            }
            if(Hanger.state == MechanismState.REQUESTED) {
                hanger.internalGoTo(Hanger.activation ? Hanger.OPEN_POSITION : Hanger.CLOSED_POSITION);
                while(hanger.motor.isBusy()) {
                    Hanger.state = MechanismState.IN_USE;
                }
                Hanger.state = Hanger.activation ? MechanismState.EXTENDED : MechanismState.UNEXTENDED;
                Hanger.activation = !Hanger.activation;
            }

            drive.setDrivePowers(input);
        }
    }
}

package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.MechanismState;

@Config
public class NGTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        Claw claw = new Claw();
        Hanger hanger = new Hanger();
        Arm arm = new Arm();

        arm.init(hardwareMap);
        claw.init(hardwareMap);
        hanger.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            /* SECTION: Drive */
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x
            ));
            drive.updatePoseEstimate();

            /* SECTION: Arm */
            arm.slidePower(-gamepad2.right_stick_y);

            arm.rawElbowPower(gamepad2.left_stick_x);
            arm.setShoulder(gamepad2.left_stick_y);

            /* SECTION: Wrist */
            if (gamepad2.right_trigger > 0.5)
                claw.openClaw(Claw.clawClosedPosition);
            else
                claw.openClaw(Claw.clawOpenPosition);

            if (gamepad2.dpad_up)
                claw.up();
            else if (gamepad2.dpad_down)
                claw.down();
            else if (gamepad2.dpad_right)
                claw.clawZero();

            if (gamepad2.y) hanger.extend();
            if (gamepad2.x) hanger.unextend();
            if (gamepad2.right_stick_button) hanger.forceStop();

            telemetry.addData("Hanger STATE", Hanger.state);
            telemetry.addData("Hanger ACTIVATED", Hanger.activation);
            telemetry.addData("Hanger MOTOR/Position", hanger.motor.getCurrentPosition());
            telemetry.addData("Hanger MOTOR/Is Busy", hanger.motor.isBusy());
            telemetry.addData("Hanger MOTOR/Power", hanger.motor.getPower());
            telemetry.addData("Hanger MOTOR/Target Pos", hanger.motor.getTargetPosition());
            telemetry.addData("Claw CLAW/Position", claw.claw.getPosition());
            telemetry.addData("Claw WRIST/Position", claw.wrist.getPosition());
            telemetry.addData("Arm ELBOW/Power", arm.elbow.getPower());
            telemetry.addData("Arm SHOULDER/Power", arm.shoulder.getPower());
            telemetry.addData("Arm SlideArm/LEFT Position", arm.left.getCurrentPosition());
            telemetry.addData("Arm ArmArm/RIGHT Position", arm.right.getCurrentPosition());
            telemetry.addLine("Gamepad 1: Drive");
            telemetry.addLine("Gamepad 1 Right Trigger DOWN: Claw Open");
            telemetry.addLine("Gamepad 1 Right Trigger Up: Claw Close");
            telemetry.addLine("Gamepad 2 Right Stick Y: Arm Slides");
            telemetry.addLine("Gamepad 2 Left Stick X: Elbow");
            telemetry.addLine("Gamepad 2 Y: hanger.extend");
            telemetry.addLine("Gamepad 2 X: hanger.unextend");
            telemetry.addLine("Gamepad 2 Right Stick Press: hanger.forceStop");
            telemetry.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }
    }
}

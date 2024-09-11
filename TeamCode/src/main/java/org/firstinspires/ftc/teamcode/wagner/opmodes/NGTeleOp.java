package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;

@TeleOp(name = "New Gen TeleOp", group = "estoy")
public class NGTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        Claw claw = new Claw();
        Hanger hanger = new Hanger();
        claw.init(hardwareMap);
        hanger.init(hardwareMap);
        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            PoseVelocity2d input = new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_x,
                            -gamepad1.left_stick_y
                    ),
                    gamepad1.right_stick_x
            );

            claw.run(gamepad1, gamepad2, drive, telemetry);
            hanger.run(gamepad1, gamepad2, drive, telemetry);
            drive.setDrivePowers(input);
        }
    }
}

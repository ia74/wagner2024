package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;

@Autonomous(name = "Motor Limit Fixer")
public class TestOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);
        Arm arm = new Arm();
        arm.init(hardwareMap);
        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            if(gamepad1.a) {
                arm.shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            };

            telemetry.addLine("Gamepad 1 A : Reset Shoulder Position");
            telemetry.addLine(arm.toString());
            telemetry.update();
        }

    }
}

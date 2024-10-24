package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepad;

public class TestClaw extends LinearOpMode {
    public int sleepTime = 1000;
    @Override
    public void runOpMode() {
        Claw claw = new Claw();
        claw.init(hardwareMap);

        waitForStart();

        claw.clawZero();
        print("Claw was set to 0", claw.claw, telemetry);
        sleep(sleepTime);

        claw.fullOpen();
        print("Claw was set 1", claw.claw, telemetry);
        sleep(sleepTime);

        claw.openClaw(0.5);
        print("Claw was set to 0.5", claw.claw, telemetry);
        sleep(sleepTime);

        claw.fullClose();
        print("Claw was full closed to -1", claw.claw, telemetry);
        sleep(sleepTime);

        claw.close();
        print("Claw was closed to " + Claw.clawClosedPosition, claw.claw, telemetry);
        sleep(sleepTime);

        claw.open();
        print("Claw was opened to " + Claw.clawOpenPosition, claw.claw, telemetry);
        sleep(sleepTime);

        claw.middle();
        print("Wrist was middled to 0", claw.wrist, telemetry);
        sleep(sleepTime);

        claw.setWrist(0.5);
        print("Wrist was set to 0.5", claw.wrist, telemetry);
        sleep(sleepTime);

        claw.up();
        print("Wrist was upped to " + Claw.wristUpPosition, claw.wrist, telemetry);
        sleep(sleepTime);

        claw.down();
        print("Wrist was downed to " + Claw.wristDownPosition, claw.wrist, telemetry);
        sleep(sleepTime);

        claw.fullDown();
        print("Wrist was full downed to -1", claw.wrist, telemetry);
        sleep(sleepTime);

        claw.fullUp();
        print("Wrist was full upped to 1", claw.wrist, telemetry);
        sleep(sleepTime);

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Wrist", claw.wrist.getPosition());
            telemetry.addData("Claw", claw.claw.getPosition());
            telemetry.addLine("Gamepad1 Right stick Y: Wrist control");
            telemetry.addLine("Gamepad1 A: Fully Open Claw (1)");
            telemetry.addLine("Gamepad1 B: Fully Close Claw (-1)");
            telemetry.addLine("Gamepad1 X: Close Claw (" + Claw.clawClosedPosition + ")");
            telemetry.addLine("Gamepad1 Y: Open Claw (" + Claw.clawOpenPosition + ")");
            telemetry.addLine("Not holding: Set position to 0");
            if(gamepad1.a) claw.fullOpen();
            else if(gamepad1.b) claw.fullClose();
            else if(gamepad1.x) claw.open();
            else if(gamepad1.y) claw.close();
            else claw.openClaw(0);

            claw.setWrist(gamepad1.right_stick_y);
            telemetry.update();
        }
    }
    public void print(String a, Servo b, Telemetry t) {
        t.addLine(a + " / Current: " + b.getPosition());
        t.update();
    }
}

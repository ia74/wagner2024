package org.firstinspires.ftc.teamcode.wagner.opmodes.mechanismtests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.nggamepad.NGGamepad;

public class TestArm extends LinearOpMode {
    @Override
    public void runOpMode() {
        ElapsedTime time = new ElapsedTime();
        Arm arm = new Arm();
        arm.init(hardwareMap);

        waitForStart();

        time = new ElapsedTime();
        while(time.milliseconds() < 1000)
            arm.slidePower(1);
        arm.slidePower(0);
        print("Ran motor at 1 power for " + time.milliseconds() + "ms", telemetry);
        sleep(1000);

        time = new ElapsedTime();
        while(time.milliseconds() < 1000)
            arm.slidePower(-1);
        arm.slidePower(0);
        print("Ran motor at -1 power for " + time.milliseconds() + "ms", telemetry);
        sleep(1000);

        time = new ElapsedTime();
        while(time.milliseconds() < 500)
            arm.rawElbowPower(1);
        print("Ran elbow at 1 power for 500ms, real: " + arm._timer.milliseconds(), telemetry);
        sleep(1000);

        time = new ElapsedTime();
        while(time.milliseconds() < 500)
            arm.rawElbowPower(-1);
        print("Ran elbow at 1 power for 500ms, real: " + arm._timer.milliseconds(), telemetry);
        sleep(1000);

        print("Main test complete, longer elbow will start", telemetry);
        sleep(2000);

        time = new ElapsedTime();
        while(time.milliseconds() < 1500)
            arm.rawElbowPower(1);
        print("Ran elbow at 1 power for 1500ms, real: " + arm._timer.milliseconds(), telemetry);
        sleep(1000);

        time = new ElapsedTime();
        while(time.milliseconds() < 1500)
            arm.rawElbowPower(-1);
        print("Ran elbow at 1 power for 1500ms, real: " + arm._timer.milliseconds(), telemetry);
        sleep(1000);

        print("shoulder time", telemetry);
        sleep(2000);

        arm.setShoulder(1);
        print("Set shoulder to 1", telemetry);
        sleep(1000);

        arm.setShoulder(-1);
        print("Set shoulder to -1", telemetry);
        sleep(1000);

        arm.setShoulder(0.5);
        print("Set shoulder to 0.5", telemetry);
        sleep(1000);

        while(opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Shoulder", arm.shoulder.getPower());
            telemetry.addData("Elbow", arm.elbow.getPower());
            telemetry.addLine("Elbow: Gamepad1 Left Stick Y");
            telemetry.addLine("Elbow: Gamepad1 Right Stick Y");

            arm.rawElbowPower(gamepad1.left_stick_x);
            arm.setShoulder(gamepad1.right_stick_y);
            telemetry.update();
        }
    }
    public void print(String a, Telemetry t) {
        t.addLine(a);
        t.update();
    }
    public void print(String a, Servo b, Telemetry t) {
        t.addLine(a + " / Current: " + b.getPosition());
        t.update();
    }
}

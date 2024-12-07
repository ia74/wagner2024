package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.Toggleable;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Lights;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Mechanism;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
public static String[] names = {
        "808 Essentially Means Crashout",
        "Estoy Gay: The Return",
        "New Gen TeleOp",
        "Spicy Bucket lives on",
        "When the knee surgery is tomorrow"
};
 */
@TeleOp(name = "Fabian needs knee surgery Record")
@Config
@Disabled
public class FabianGay extends LinearOpMode {
    ArrayList<Frame> frames = new ArrayList<>();
    public static double slowModeMultiplier = 0.7;
    public static double extraSlowModeMultiplier = 0.4;
    public static double shoulderLimitMin = 100;
    public static double shoulderLimitMax = 2442;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        double power = 1;
        Toggleable pullDrive = new Toggleable();
        Toggleable clawClosed = new Toggleable();

        Claw claw = new Claw();
        Arm arm = new Arm();
        Lights lights = new Lights();

        arm.init(hardwareMap);
        claw.init(hardwareMap);
        lights.init(hardwareMap);

        lights.breathRed();

        boolean isDoingAction = false;
        boolean shouldRecord = true;

        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (shouldRecord) {
            /* SECTION: Drive */
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * power,
                            -gamepad1.left_stick_x * power
                    ),
                    -gamepad1.right_stick_x * power
            ));
            drive.updatePoseEstimate();

            if(gamepad1.left_bumper) power = slowModeMultiplier;
            else if(gamepad1.right_bumper) power = extraSlowModeMultiplier;
            else power = 1;

            double shoulderPos = arm.getShoulderPosition();
            /* SECTION: Arm */
            arm.slidePower(-gamepad2.right_stick_y);
            double shoulderPower = -gamepad2.left_stick_y;

            if(shoulderPos < shoulderLimitMax) arm.setShoulder(shoulderPower);
            else if(shoulderPower < 0.2) arm.setShoulder(shoulderPower);

            if(shoulderPos >= shoulderLimitMax) arm.setShoulder(0);

            /* SECTION: Wrist */
            if (gamepad2.right_trigger > 0.2) {
                claw.openClaw(Claw.clawClosedPosition);
                if(!isDoingAction) lights.setPatternIfNot(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_LAVA_PALETTE);
                isDoingAction = true;
            } else {
                claw.openClaw(Claw.clawOpenPosition);
                if(!isDoingAction) lights.setPatternIfNot(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
                isDoingAction = false;
            }

            if (gamepad2.dpad_up)
                claw.up();
            else if (gamepad2.dpad_down)
                claw.down();
            else if (gamepad2.dpad_right)
                claw.middle();

            clawClosed.update(gamepad2.right_trigger > 0.2);
            pullDrive.update(gamepad2.share);

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            if(gamepad2.options) shouldRecord = false;

            frames.add(new Frame(
                new double[] {
                        drive.leftFront.getPower(), drive.rightFront.getPower(),
                        drive.leftBack.getPower(), drive.rightBack.getPower()
                },
                    arm.left.getPower(),
                    claw.claw.getPosition(), arm.getShoulderPosition(), timer.nanoseconds()
            ));

            telemetry.addLine("I think im recording");
            telemetry.update();
        }

        try {
        FileWriter fileWriter = new FileWriter("Test-Frame.txt");
            String output = "";
            for(Frame frame : frames) {
                output += frame.toString() + "\n";
            }
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

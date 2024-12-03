package org.firstinspires.ftc.teamcode.wagner.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;
import org.firstinspires.ftc.teamcode.wagner.Toggleable;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Hanger;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Lights;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Mechanism;

/*
public static String[] names = {
        "808 Essentially Means Crashout",
        "Estoy Gay: The Return",
        "New Gen TeleOp",
        "Spicy Bucket lives on",
        "When the knee surgery is tomorrow"
};
 */
@TeleOp(name = "Knee Surgery")
@Config
public class NGTeleOp extends LinearOpMode {
    public static double slowModeMultiplier = 0.7;
    public static double extraSlowModeMultiplier = 0.4;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        double power = 1;
        Toggleable pullDrive = new Toggleable();
        Toggleable clawClosed = new Toggleable();
        Toggleable hookOut = new Toggleable();

        Claw claw = new Claw();
        Hanger hanger = new Hanger();
        Arm arm = new Arm();
        Lights lights = new Lights();

        arm.init(hardwareMap);
        claw.init(hardwareMap);
        hanger.init(hardwareMap);
        lights.init(hardwareMap);

        lights.breathRed();

        boolean isDoingAction = false;

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
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

            /* SECTION: Arm */
            arm.slidePower(-gamepad2.right_stick_y);

            float lsx = gamepad2.left_stick_x;
            if(lsx > 0.1) {
                arm.elbowPower(1);
                if(!isDoingAction) lights.setPatternIfNot(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                isDoingAction = true;
            } else if(lsx < -0.1) {
                arm.elbowPower(-1);
                if(!isDoingAction) lights.setPatternIfNot(RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET);
                isDoingAction = true;
            }
            else {
                arm.elbowPower(0);
                isDoingAction = false;
            }

            arm.setShoulder(gamepad2.left_stick_y);

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

            if(hookOut.state)  hanger.extend();
            else hanger.unextend();

            clawClosed.update(gamepad2.right_trigger > 0.2);
            hookOut.update(gamepad1.y);
            pullDrive.update(gamepad2.options);

            if(pullDrive.state) {
                telemetry.addLine();
                telemetry.addLine(Mechanism.motorIfo(drive.leftFront, "FrontLeftMotor"));
                telemetry.addLine(Mechanism.motorIfo(drive.rightFront, "FrontRightMotor"));
                telemetry.addLine(Mechanism.motorIfo(drive.leftBack, "BackLeftMotor"));
                telemetry.addLine(Mechanism.motorIfo(drive.rightBack, "BackRightMotor"));
                telemetry.addLine();
            }

            telemetry.addLine("Gamepad 1");
            telemetry.addLine("\tLeft & Right Stick: Drive");
            telemetry.addLine("\tY: Hanger Hook Extend (TOGGLE)");
            telemetry.addLine();
            telemetry.addLine("Gamepad 2");
            telemetry.addLine("\tOptions: Pull extra info (TOGGLE)");
            telemetry.addLine("\tLeft Stick X: Elbow");
            telemetry.addLine("\tLeft Stick Y: Shoulder");
            telemetry.addLine("\tRight Stick Y: Arm Slides");
            telemetry.addLine("\tRight Trigger: Claw Close (TOGGLE)");
            telemetry.addLine("\tD-Pad:");
            telemetry.addLine("\t\tUp: Claw UP");
            telemetry.addLine("\t\tDown: Claw DOWN");
            telemetry.addLine("\t\tRight: Claw Middle");
            telemetry.addLine();
            if(pullDrive.state) {
                telemetry.addLine(arm.toString());
                telemetry.addLine(claw.toString());
                telemetry.addLine(hanger.toString());
            }
            telemetry.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }
    }
}

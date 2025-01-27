package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.GlobalStorage;
import org.firstinspires.ftc.teamcode.Toggleable;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Subsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Drawing;

public class KneeSurgery extends OpMode {
    Follower follower;
    Arm arm;
    Lights lights;
    Claw claw;

    Toggleable debugMode = new Toggleable();

    public void setLightColor(Lights e){};

    @Override
    public void init() {
        follower = new Follower(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        lights = new Lights(hardwareMap);

        follower.getLeftFront().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        follower.getRightFront().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        follower.getLeftRear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        follower.getRightRear().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        setLightColor(lights);

        follower.setPose(
                GlobalStorage.currentPose != null ? GlobalStorage.currentPose : new Pose(10.220338983050848, 60.40677966101695, Math.toRadians(0))
        );
        follower.startTeleopDrive();
    }

    double currentDrivePower = 1.0;

    @Override
    public void loop() {
        double newDrivePower;
        double currentArmPosition = arm.getArmPosition();
        double currentShoulderPosition = arm.getShoulderPosition();

        Arm.slidesTargetPosition = currentArmPosition;
        Arm.shoulderTargetPosition = currentShoulderPosition;

        double armPower = -gamepad2.right_stick_y;
        double shoulderPower = -gamepad2.left_stick_y;

        if(Math.abs(armPower) > 0.1) {
            if(currentArmPosition > Arm.slidesMaximumPositionLimit - 20) {
                if (!(armPower > 0.1)) {
                    arm.setSlidePower(armPower);
                } else {
                    arm.individuallyUpdateSlides();
                }
            } else {
                arm.setSlidePower(armPower);
            }
        } else {
            arm.individuallyUpdateSlides();
        }

        if(Math.abs(shoulderPower) > 0.1) {
            if(currentShoulderPosition > Arm.shoulderMaximumPositionLimit - 30) {
                if (!(shoulderPower > 0.1)) {
                    arm.setShoulderPower(shoulderPower);
                } else {
                    arm.individuallyUpdateShoulder();
                }
            } else {
                arm.setShoulderPower(shoulderPower);
            }
        } else {
            arm.individuallyUpdateShoulder();
        }

        if (gamepad2.dpad_up)
            claw.setWristState(Claw.WristState.UP);
        else if (gamepad2.dpad_down)
            claw.setWristState(Claw.WristState.DOWN);
        else if (gamepad2.dpad_right)
            claw.setWristState(Claw.WristState.MIDDLE);

        if (gamepad2.right_trigger > 0.2) claw.setClawState(Claw.ClawState.CLOSED);
        else claw.setClawState(Claw.ClawState.OPEN);


        if(gamepad1.left_bumper) {
            newDrivePower = 0.6;
        } else if(gamepad1.right_bumper) {
            newDrivePower = 0.3;
        } else {
            newDrivePower = 1.0;
        }

        if(newDrivePower != currentDrivePower) {
            follower.setMaxPower(newDrivePower);
            currentDrivePower = newDrivePower;
        }

        debugMode.update(gamepad2.share);

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
        follower.update();

        if(debugMode.state) {
            telemetry.addLine(arm.toString());
            telemetry.addLine(claw.toString());
            telemetry.addLine(lights.toString());
            telemetry.addLine(follower.getPose().toString());
            Drawing.drawDebug(follower);
        } else {
            telemetry.addLine("Gamepad 2 - Options : Debug Mode");
        }
        telemetry.update();
    }
}

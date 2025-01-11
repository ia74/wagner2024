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
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

public class KneeSurgery extends OpMode {
    private Follower follower;
    Arm arm;
    Lights lights;
    Claw claw;

    Toggleable debugMode = new Toggleable();

    public void setLightColor(Lights e){};

    public static double shoulderLimitMax = 2442;
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

        lights.off();
        lights.green();
        setLightColor(lights);

        if(GlobalStorage.currentPose != null) follower.setPose(GlobalStorage.currentPose);
        follower.startTeleopDrive();
    }
    @Override
    public void loop() {
        setLightColor(lights);
        Arm.stargetPosition = arm.getArmPosition();
        Arm.shoulderktargetPosition = arm.getShoulderPosition();

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
        follower.update();

        /* SECTION: Arm */
        double armPower = -gamepad2.right_stick_y;
        double shoulderPower = -gamepad2.left_stick_y;

        if(Math.abs(armPower) > 0.1) {
            if(arm.getArmPosition() > Arm.smaxPosition - 20) {
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
            if(arm.getShoulderPosition() > Arm.shoulderkmaxPosition - 30) {
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
            claw.up();
        else if (gamepad2.dpad_down)
            claw.down();
        else if (gamepad2.dpad_right)
            claw.middle();

        if(gamepad1.left_bumper) {
            follower.setMaxPower(0.6);
        } else if(gamepad1.right_bumper) {
            follower.setMaxPower(0.3);
        } else {
            follower.setMaxPower(1);
        }

        if (gamepad2.right_trigger > 0.2) claw.close();
        else claw.open();

        debugMode.update(gamepad2.share);

        if(debugMode.state) {
            telemetry.addLine(arm.toString());
            telemetry.addLine(claw.toString());
            telemetry.addLine(lights.toString());
        } else {
            telemetry.addLine("Gamepad 1 - Options : Debug Mode");
        }
        telemetry.update();
    }
}

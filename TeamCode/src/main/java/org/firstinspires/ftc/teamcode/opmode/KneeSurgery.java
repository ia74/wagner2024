package org.firstinspires.ftc.teamcode.opmode;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.subsystem.Lights;

/**
 * This is the real TeleOp code. This specific class is not registered as an OpMode as it's meant to have a custom light pattern, THEN registered.
 */
public class KneeSurgery extends OpMode {
    private Follower follower;
    public Lights lights;
    public RevBlinkinLedDriver.BlinkinPattern patternToUse = RevBlinkinLedDriver.BlinkinPattern.FIRE_LARGE;
    // The idea is, we shouldn't see the FIRE_LARGE pattern, because it will be overridden by the selector. (blue, red)
    Arm arm;
    Claw claw;

    public static double shoulderLimitMax = 2442;
    @Override
    public void init() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        lights = new Lights(hardwareMap);

        lights.setPattern(patternToUse);

        if(FConstants.currentPose != null) follower.setPose(FConstants.currentPose); // This is used to carry over from Autonomous
        follower.startTeleopDrive();
    }
    @Override
    public void loop() {
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
        follower.update();

        double shoulderPos = arm.getShoulderPosition();
        /* SECTION: Arm */
        arm.setSlidePower(-gamepad2.right_stick_y);
        double shoulderPower = -gamepad2.left_stick_y;

        if(shoulderPos < shoulderLimitMax) arm.setShoulderPower(shoulderPower);
        else if(shoulderPower < 0.2) arm.setShoulderPower(shoulderPower);

        if(shoulderPos >= shoulderLimitMax) arm.setShoulderPower(0);

        if (gamepad2.dpad_up)
            claw.up();
        else if (gamepad2.dpad_down)
            claw.down();
        else if (gamepad2.dpad_right)
            claw.middle();

        if (gamepad2.right_trigger > 0.2) claw.close();
        else claw.open();

        telemetry.addLine(arm.toString());
        telemetry.addLine(claw.toString());
        telemetry.addLine(lights.toString());
        telemetry.update();
    }
}

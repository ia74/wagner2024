package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Lights;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

/**
 * This is the TeleOpEnhancements OpMode. It is an example usage of the TeleOp enhancements that
 * Pedro Pathing is capable of.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/21/2024
 */
@TeleOp(name = "Knee Surgery Blue")
public class KneeSurgeryBlue extends OpMode {
    private Follower follower;
    Arm arm;
    Lights lights;
    Claw claw;

    public static double shoulderLimitMax = 2442;
    @Override
    public void init() {
        follower = new Follower(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        lights = new Lights(hardwareMap);

        lights.breathBlue();

        follower.startTeleopDrive();
    }
    @Override
    public void loop() {
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
        follower.update();

        double shoulderPos = arm.getShoulderPosition();
        /* SECTION: Arm */
        arm.slidePower(-gamepad2.right_stick_y);
        double shoulderPower = -gamepad2.left_stick_y;

        if(shoulderPos < shoulderLimitMax) arm.setShoulder(shoulderPower);
        else if(shoulderPower < 0.2) arm.setShoulder(shoulderPower);

        if(shoulderPos >= shoulderLimitMax) arm.setShoulder(0);

        if (gamepad2.dpad_up)
            claw.up();
        else if (gamepad2.dpad_down)
            claw.down();
        else if (gamepad2.dpad_right)
            claw.middle();

    }
}

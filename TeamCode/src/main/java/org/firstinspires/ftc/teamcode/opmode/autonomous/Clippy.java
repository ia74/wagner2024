package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

public class Clippy extends LinearOpMode {
    public static int scoringBasketRaisePos = 1020;
    public static int s = 0;
    Follower follower;
    Arm arm;
    Claw claw;

    @Override
    public void runOpMode() throws InterruptedException {
        follower = new Follower(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
        }
    }
}

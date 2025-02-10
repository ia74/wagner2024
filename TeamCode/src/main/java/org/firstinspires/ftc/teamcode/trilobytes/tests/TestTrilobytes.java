package org.firstinspires.ftc.teamcode.trilobytes.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.trilobytes.Scheduler;
import org.firstinspires.ftc.teamcode.trilobytes.impl.usages.PIDControllerUser;
import org.firstinspires.ftc.teamcode.trilobytes.impl.usages.PathFollower;
import org.firstinspires.ftc.teamcode.trilobytes.impl.usages.RunPIDControllerToPosition;

@Autonomous(name="Trilobyte Command System Test")
public class TestTrilobytes extends OpMode {
    Scheduler scheduler;
    Arm arm;
    Follower follower;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        arm = new Arm(hardwareMap);
        follower = new Follower(hardwareMap);

        PathFollower pathFollower = new PathFollower(follower);
        PIDControllerUser user = new PIDControllerUser(arm.slidesPid, arm.left, arm.right);
        RunPIDControllerToPosition controllerToPosition = new RunPIDControllerToPosition(
                arm.left,
                arm.slidesPid,
                100,
                10
        );

        scheduler = new Scheduler(telemetry);
        scheduler.queue(pathFollower);
        scheduler.queue(user);
        scheduler.queue(controllerToPosition);
    }

    @Override
    public void loop() {
        scheduler.update();
    }
}

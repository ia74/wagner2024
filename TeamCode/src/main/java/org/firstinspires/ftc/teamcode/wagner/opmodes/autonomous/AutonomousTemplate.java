package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.wagner.GlobalStorage;

@Disabled // TODO: REMOVE THIS FROM YOUR REAL AUTO
@Autonomous(name = "Autonomous TEMPLATE", group="Autonomous")
public class AutonomousTemplate extends LinearOpMode {
    enum State {
        STEP_ONE,
        STEP_TWO
    };
    State state = State.STEP_ONE;
    @Override
    public void runOpMode() throws InterruptedException {
        GlobalStorage.pose = new Pose2d(0,0,0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        waitForStart();

        while(opModeIsActive()) {
            switch(state) {
                case STEP_ONE:
                    Actions.runBlocking(drive.actionBuilder(drive.pose)
                                    .turnTo(Math.toRadians(90))
                            .build());
                    state = State.STEP_TWO;
                    break;
                case STEP_TWO:
                    Actions.runBlocking(drive.actionBuilder(drive.pose)
                                    .turn(Math.toRadians(360))
                            .build());
            }

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            telemetry.addData("Current State", state);
            telemetry.update();
        }

    }
}

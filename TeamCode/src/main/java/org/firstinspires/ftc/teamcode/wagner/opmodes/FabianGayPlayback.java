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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

/*
public static String[] names = {
        "808 Essentially Means Crashout",
        "Estoy Gay: The Return",
        "New Gen TeleOp",
        "Spicy Bucket lives on",
        "When the knee surgery is tomorrow"
};
 */
@TeleOp(name = "Fabian needs knee surgery Playback")
@Config
@Disabled
public class FabianGayPlayback extends LinearOpMode {
    Queue<Frame> frames = new LinkedList<>();
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, GlobalStorage.pose);

        Claw claw = new Claw();
        Arm arm = new Arm();
        Lights lights = new Lights();

        arm.init(hardwareMap);
        claw.init(hardwareMap);
        lights.init(hardwareMap);

        lights.breathRed();

        try {
            Scanner scan = new Scanner(new File("Test-Frame.txt"));
            while(scan.hasNextLine()) {
                frames.add(Frame.from(scan.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while(opModeIsActive()) {
            Frame top = frames.peek();
            if(top == null) break;
            if(top.current > timer.nanoseconds()) continue;
            if(top.current >= timer.nanoseconds() - 500 && top.current <= timer.nanoseconds() + 500) {
                Frame.execute(Objects.requireNonNull(frames.poll()), claw, arm, drive);
            }
        }
    }
}

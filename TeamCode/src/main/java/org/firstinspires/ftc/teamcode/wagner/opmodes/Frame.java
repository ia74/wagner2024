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
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Lights;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class Frame {
    public double[] driveMotPow;
    public double armPower;
    public double clawState;
    public double shoulder;
    public long current;
    public Frame(double[] driveMotPow, double armPower, double claw, double shoulder, long currentTime) {
        this.driveMotPow = driveMotPow;
        this.clawState = claw;
        this.shoulder = shoulder;
        this.armPower = armPower;
        this.current = currentTime;
    }
    public String toString() {
        StringBuilder motorPows = new StringBuilder();
        for(double i : driveMotPow) motorPows.append(i+",");
        motorPows.append(";");
        motorPows.append(armPower);
        motorPows.append(";");
        motorPows.append(clawState);
        motorPows.append(";");
        motorPows.append(shoulder);
        motorPows.append(";");
        motorPows.append(current);
        return motorPows.toString();
    }
    public static Frame from(String frame) {
        String[] a = frame.split(";");
        double[] mtp = Arrays.stream(a[0].split(",")).mapToDouble(Double::parseDouble).toArray();
        double arm = Double.parseDouble(a[1]);
        double claw = Double.parseDouble(a[2]);
        double shoulder = Double.parseDouble(a[3]);
        long curr = Long.parseLong(a[4]);
        return new Frame(mtp, arm, claw, shoulder, curr);
    }
    public static void execute(Frame frame, Claw claw, Arm arm, MecanumDrive drive) {
        drive.leftFront.setPower(frame.driveMotPow[0]);
        drive.rightFront.setPower(frame.driveMotPow[1]);
        drive.leftBack.setPower(frame.driveMotPow[2]);
        drive.rightBack.setPower(frame.driveMotPow[3]);
        arm.slidePower(frame.armPower);
        arm.setShoulder(frame.shoulder);
        claw.claw.setPosition(frame.clawState);
    }
}
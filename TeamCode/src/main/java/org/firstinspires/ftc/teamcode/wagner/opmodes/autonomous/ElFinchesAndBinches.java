package org.firstinspires.ftc.teamcode.wagner.opmodes.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.wagner.PartsMap;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.wagner.mechanisms.Lights;

@Autonomous(name = "Miguel Maneuvering Basket Vlue", group="!!Autonomous")
@Config
public class ElFinchesAndBinches extends LinearOpMode {
    public Arm arm = new Arm();
    public Claw claw = new Claw();
    public Lights lights = new Lights();
    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;
    public ElapsedTime timer;
    public DcMotor[] mts = {
            leftFront,
            leftBack,
            rightFront,
            rightBack
    };
    public static int upwardsExtendTime = 4500;
    public static double COUNTS_PER_MOTOR_REV = 384.5;
    public static double DRIVE_GEAR_REDUCTION = 1.0;
    public static double WHEEL_DIAMETER_INCHES = 3.75;
    public static double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    void setupMtr() {
        leftFront = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_FL.toString());
        rightFront = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_FR.toString());
        leftBack = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_BL.toString());
        rightBack = hardwareMap.get(DcMotor.class, PartsMap.DRIVE_BR.toString());

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        mts = new DcMotor[]{leftFront, leftBack, rightFront, rightBack};

        for (DcMotor mot : mts) {
            mot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            mot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    @Override
    public void runOpMode() {
        setupMtr();
        arm.init(hardwareMap);
        claw.init(hardwareMap);
        lights.init(hardwareMap);
        timer = new ElapsedTime();
        waitForStart();

        bucketFromStart();
        grabFromFloorRight();

    }

    void grabFromFloorRight() {

        finchesAndBinches(1,13.25,13.25,
                                  13.25,13.25, 7);

        grab();

        finchesAndBinches(1,-43,43,
                -43,43,7);

        finchesAndBinches(1,10,10,
                        10,10,7);

        finchesAndBinches(1,-6,6, 6,-6,7);

        score(1);

        finchesAndBinches(1,-3,3,
                3,-3,7);

        finchesAndBinches(1,8,8,
                8,8, 7);

    }

    void bucketFromStart() {

        finchesAndBinches(1,9,9,
                                  9,9,7);

        finchesAndBinches(1,-16,16,
                                  16,-16,7);

        finchesAndBinches(1,-40,40,
                                  -40,40,7);

        score(2);
    }


    public void finchesAndBinches(
            double speed,
            double flInches,
            double frInches,
            double blInches,
            double brInches,
            double timeoutSeconds
    ) {
        if(opModeIsActive()) {
            int flTarget = leftFront.getCurrentPosition() + (int) (flInches * COUNTS_PER_INCH);
            int frTarget = rightFront.getCurrentPosition() + (int) (frInches * COUNTS_PER_INCH);
            int blTarget = leftBack.getCurrentPosition() + (int) (blInches * COUNTS_PER_INCH);
            int brTarget = rightBack.getCurrentPosition() + (int) (brInches * COUNTS_PER_INCH);

            leftFront.setTargetPosition(flTarget);
            rightFront.setTargetPosition(frTarget);
            leftBack.setTargetPosition(blTarget);
            rightBack.setTargetPosition(brTarget);

            for(DcMotor mt : mts) mt.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            timer.reset();
            for(DcMotor mt : mts) mt.setPower(Math.abs(speed));

            while(opModeIsActive() &&
                    (timer.seconds() < timeoutSeconds) &&
                    (leftFront.isBusy() && rightFront.isBusy() && leftBack.isBusy() && rightBack.isBusy())) {
                telemetry.addData("Miguel Maneuvering", "Running to %7d :%7d", flTarget, frTarget, blTarget, brTarget);
                telemetry.addData("Miguel Maneuvering", "Running to %7d :%7d",
                        leftFront.getCurrentPosition(),
                        rightFront.getCurrentPosition(),
                        leftBack.getCurrentPosition(),
                        rightBack.getCurrentPosition()
                );

                telemetry.update();
            }
            for(DcMotor mt : mts) {
                mt.setPower(0);
                mt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }
    void score(int stage) {
        arm.slidePower(1);
        sleep(upwardsExtendTime);
        arm.slidePower(0);
        claw.down();
        sleep(625);
        claw.open();
        sleep(200);
        claw.up();
        sleep(250);
        finchesAndBinches(1, -4,-4,
                                    -4,-4, 7);
        claw.close();
        sleep(150);
        arm.slidePower(-1);
        if(stage == 2)  finchesAndBinches(1,41,-41,
                42,-42,7);
        sleep(upwardsExtendTime);
        arm.slidePower(0);
    }
    void grab() {
        claw.open();
        claw.down();
        sleep(700);
        claw.close();
        sleep(500);
        claw.up();
        sleep(500);
    }
}

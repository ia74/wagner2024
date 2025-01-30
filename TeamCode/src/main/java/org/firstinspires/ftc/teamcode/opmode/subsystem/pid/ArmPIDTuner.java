package org.firstinspires.ftc.teamcode.opmode.subsystem.pid;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MathUtil;
import org.firstinspires.ftc.teamcode.opmode.subsystem.Arm;

//@Disabled
@Config
@TeleOp(name = "PID / Arm Tuner")
public class ArmPIDTuner extends OpMode {
    public static MathUtil.Position slidesPositioning = new MathUtil.Position(300, 2000);
    public static MathUtil.Position shoulderPositioning = new MathUtil.Position(300, 2000);
    public static boolean tuningSlides = true;

    Arm arm;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        arm = new Arm(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.y) {
            if(tuningSlides) arm.setSlidesTargetPosition(slidesPositioning.high);
            else arm.setShoulderTargetPosition(shoulderPositioning.high);
        }
        else if(gamepad1.a) {
            if(tuningSlides) arm.setSlidesTargetPosition(slidesPositioning.low);
            else arm.setShoulderTargetPosition(shoulderPositioning.low);
        }

        if(tuningSlides) arm.slidesPid.setCoefficients(Arm.slidesCoefficients);
        else arm.shoulderPid.setCoefficients(Arm.shoulderCoefficients);

        arm.update();

        telemetry.addData("Tuning", tuningSlides ? "Slides" : "Shoulder");
        telemetry.addData("Gamepad 1 Y/Triangle", "Up");
        telemetry.addData("Gamepad 1 A/Cross", "Down");
        telemetry.addData("Target Position", tuningSlides ? Arm.slidesTargetPosition : Arm.shoulderCoefficients);
        telemetry.addData("Current Position", tuningSlides? arm.getArmPosition() : arm.getShoulderPosition());
        telemetry.addData("Current Power", tuningSlides? arm.left.getPower() : arm.shoulder.getPower());
        telemetry.addLine(tuningSlides ? arm.slidesPid.toString() : arm.shoulderPid.toString());
        telemetry.update();
    }
}

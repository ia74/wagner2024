package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

import org.firstinspires.ftc.teamcode.PartsMap;

@Config
public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.0029561460323358517;
        ThreeWheelConstants.strafeTicksToInches = -0.0030611992603021814;
        ThreeWheelConstants.turnTicksToInches = 0.003042425003170012;
        ThreeWheelConstants.leftY = 7.9;
        ThreeWheelConstants.rightY = -7.9;
        ThreeWheelConstants.strafeX = -6;
        ThreeWheelConstants.leftEncoder_HardwareMapName = PartsMap.DRIVE_BL.toString();
        ThreeWheelConstants.rightEncoder_HardwareMapName = PartsMap.DRIVE_BR.toString();
        ThreeWheelConstants.strafeEncoder_HardwareMapName = PartsMap.DRIVE_FL.toString();
        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.REVERSE;
    }
}





package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.0777;
        ThreeWheelConstants.strafeTicksToInches = 0.0025;
        ThreeWheelConstants.turnTicksToInches = 0.0045;
        ThreeWheelConstants.leftY = 6;
        ThreeWheelConstants.rightY = -6;
        ThreeWheelConstants.strafeX = -2.16535;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "FLmotor";

        ThreeWheelConstants.rightEncoder_HardwareMapName = "BRmotor";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "FRmotor";
        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
    }
}





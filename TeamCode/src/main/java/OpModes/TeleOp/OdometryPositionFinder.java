
package OpModes.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Odometry Position Finder", group = "Calibration")
public class OdometryPositionFinder extends LinearOpMode {
    private DcMotorEx leftEncoder, rightEncoder, strafeEncoder;

    @Override
    public void runOpMode() {
        // Configura os encoders (substitua pelos nomes corretos do seu HardwareMap)
        leftEncoder = hardwareMap.get(DcMotorEx.class, "FLmotor"); // Encoder esquerdo (roda vertical esquerda)
        rightEncoder = hardwareMap.get(DcMotorEx.class, "BLmotor"); // Encoder direito (roda vertical direita)
        strafeEncoder = hardwareMap.get(DcMotorEx.class, "FRmotor"); // Encoder horizontal (roda lateral)

        // Reseta os encoders
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        strafeEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        strafeEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            // Lê os valores atuais dos encoders
            int leftPos = leftEncoder.getCurrentPosition();
            int rightPos = rightEncoder.getCurrentPosition();
            int strafePos = strafeEncoder.getCurrentPosition();

            // Mostra os valores no telemetry
            telemetry.addData("Encoder Esquerdo (leftY)", leftPos);
            telemetry.addData("Encoder Direito (rightY)", rightPos);
            telemetry.addData("Encoder Lateral (strafeX)", strafePos);
            telemetry.addLine("Mova o robô manualmente e observe as mudanças.");
            telemetry.update();
        }
    }
}
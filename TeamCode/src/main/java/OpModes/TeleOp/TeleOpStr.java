package OpModes.TeleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import Subsystems.Extend;
import Subsystems.Intake;
import Subsystems.Lift;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@TeleOp ( name = "RevTeleOP")
public class TeleOpStr extends NextFTCOpMode {
    public TeleOpStr (){
        super (
                Lift.INSTANCE,
                Extend.INSTANCE,
                Intake.INSTANCE
        );
    }

    public String FLname = "FLmotor";
    public String BLname = "BLmotor";
    public String FRname = "FRmotor";
    public String BRname = "BRmotor";

    public MotorEx frontLeftMotor;
    public MotorEx backLeftMotor;
    public MotorEx frontRightMotor;
    public MotorEx backRightMotor;


    public  MotorEx [] motor;

    public MecanumDriverControlled driverControlled;

    private Follower follower;

    private final Pose startPose = new Pose(0, 0, 0);

    @Override
    public void onInit(){
        // inicializa os motores.
        frontLeftMotor = new MotorEx(FLname);
        backLeftMotor = new MotorEx(BLname);
        frontRightMotor = new MotorEx(FRname);
        backRightMotor = new MotorEx(BRname);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motor = new MotorEx[] {frontRightMotor, frontLeftMotor, backRightMotor, backLeftMotor};
    }

    @Override
    public void onStartButtonPressed(){
        driverControlled = new MecanumDriverControlled(motor, gamepadManager.getGamepad1());
        driverControlled.invoke();


    }


}

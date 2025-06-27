package OpModes.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import Subsystems.Intake;
import Subsystems.Extend;
import Subsystems.Lift;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@Config

@TeleOp(name = "TeleOpRev")
public class TeleOpRev extends NextFTCOpMode {

    public TeleOpRev() {
        super(
                Lift.INSTANCE,
                Extend.INSTANCE,
                Intake.INSTANCE);
    }

    public MotorEx frontLeftMotor, backRightMotor, frontRightMotor, backLeftMotor;
    public String FLmotor = "FLmotor";
    public String FRmotor = "FRmotor";
    public String BLmotor = "BLmotor";
    public String BRmotor = "BRmotor";




    public MotorEx[] motors;

    public MecanumDriverControlled driverControlled;
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    @Override
    public void onInit() {
        // -----Inicializa os Motores-----
        frontLeftMotor = new MotorEx(FLmotor);
        backLeftMotor = new MotorEx(BLmotor);
        backRightMotor = new MotorEx(BRmotor);
        frontRightMotor = new MotorEx(FRmotor);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);



        // -----Direção dos Motores-----
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = new MotorEx[]{frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};


    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());

        driverControlled.invoke();

        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(Lift.INSTANCE::toLow);
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(Lift.INSTANCE::toHigh);
        gamepadManager.getGamepad2().getX().setPressedCommand(Extend.INSTANCE::highbasket);
        gamepadManager.getGamepad2().getY().setPressedCommand(Extend.INSTANCE::retract);



        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(
                value -> new SequentialGroup(
                        Lift.INSTANCE.toHigh(),
                        Extend.INSTANCE.highbasket(),
                        Extend.INSTANCE.retract(),
                        Lift.INSTANCE.toLow()

                )
        );




        }
    }


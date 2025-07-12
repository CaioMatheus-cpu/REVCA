package OpModes.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.pedro.DriverControlled;

import Subsystems.Intake;
import Subsystems.Extend;
import Subsystems.Lift;
import Subsystems.Outtake;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@Config

@TeleOp(name = "TeleOpRev")
public class TeleOpRev extends NextFTCOpMode {

    public TeleOpRev() {
        super(
                Lift.INSTANCE,
                Extend.INSTANCE,
                Intake.INSTANCE,
                Outtake.INSTANCE);
    }

    public MotorEx frontLeftMotor, backRightMotor, frontRightMotor, backLeftMotor;
    public String FLmotor = "FLmotor";
    public String FRmotor = "FRmotor";
    public String BLmotor = "BLmotor";
    public String BRmotor = "BRmotor";


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


        Lift.INSTANCE.resetZero().invoke();
        Extend.INSTANCE.resetZero().invoke();

    }

    @Override
    public void onStartButtonPressed() {
        CommandManager.INSTANCE.scheduleCommand(new DriverControlled(gamepadManager.getGamepad1(), true));


        //------ RESET ENCODERS ------

        Lift.INSTANCE.getDefaultCommand().invoke();
        Extend.INSTANCE.getDefaultCommand().invoke();
        //------ Pose Maxima Braco ------



        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(
                Lift.INSTANCE::ToHigh
        );
        //------ Pose Minima Braco ------
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(
                Lift.INSTANCE::ToLow
        );
        //------ Pose Maxima Linear ------
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(
                Extend.INSTANCE::ToHigh
        );
        //------ Pose Minima Linear ------
        gamepadManager.getGamepad2().getDpadRight().setPressedCommand(
                Extend.INSTANCE::ToLow
        );
        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                Outtake.INSTANCE::Hang
        );


        gamepadManager.getGamepad2().getA().setPressedCommand(
                Intake.INSTANCE::openclaw
        );

        gamepadManager.getGamepad2().getB().setPressedCommand(
                Intake.INSTANCE::closeclaw
        );
        gamepadManager.getGamepad2().getY().setPressedCommand(
                Intake.INSTANCE::openangle
        );
        gamepadManager.getGamepad2().getX().setPressedCommand(
                Intake.INSTANCE::closeangle
        );

        if(gamepadManager.getGamepad2().getRightStick().getX()>0){

        }

        gamepadManager.getGamepad2().getRightStick().setDisplacedCommand(stick -> {
            float x = stick.getFirst();
            float y = stick.getSecond();

            if (y < -0.5) {
                return Lift.INSTANCE.ToHigh();
            } else if (y > 0.5) {
                return Lift.INSTANCE.ToLow();
            }

            return null;
        });

        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(
                value -> new SequentialGroup(
                        Intake.INSTANCE.openclaw(),
                        new Delay(0.2),
                        Lift.INSTANCE.ToHigh(),
                        new Delay(0.2),
                        Extend.INSTANCE.ToHigh(),
                        new Delay(0.2),
                        Intake.INSTANCE.closeclaw()
                )
        );
        }

    }


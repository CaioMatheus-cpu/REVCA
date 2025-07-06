package OpModes.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.pedro.DriverControlled;

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




    }

    @Override
    public void onStartButtonPressed() {
        CommandManager.INSTANCE.scheduleCommand(new DriverControlled(gamepadManager.getGamepad1(), true));


        //------ RESET ENCODERS ------
        Lift.INSTANCE.resetZero().invoke();
        Extend.INSTANCE.resetZero().invoke();
        Lift.INSTANCE.getDefaultCommand().invoke();
        Extend.INSTANCE.getDefaultCommand().invoke();
        //------ Pose Maxima Braco ------



        gamepadManager.getGamepad2().getDpadUp().setReleasedCommand(
                Lift.INSTANCE::vamoquerersubir
        );
        //------ Pose Minima Braco ------
        gamepadManager.getGamepad2().getDpadDown().setReleasedCommand(
                Lift.INSTANCE::vamoquererdescer
        );
        //------ Pose Maxima Linear ------
        gamepadManager.getGamepad2().getDpadLeft().setReleasedCommand(
                Extend.INSTANCE::vamoquerersubirne
        );
        //------ Pose Minima Linear ------
        gamepadManager.getGamepad2().getDpadRight().setReleasedCommand(
                Extend.INSTANCE::vamoquererdescer
        );



        gamepadManager.getGamepad2().getA().setReleasedCommand(
                Intake.INSTANCE::vamoquererabrir
        );

        gamepadManager.getGamepad2().getB().setReleasedCommand(
                Intake.INSTANCE::vamoquererfechar
        );
        gamepadManager.getGamepad2().getY().setReleasedCommand(
                Intake.INSTANCE::vamoquererangularfechar
        );
        gamepadManager.getGamepad2().getX().setReleasedCommand(
                Intake.INSTANCE::vamoquererangular
        );





        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(
                value -> new SequentialGroup(
                        Intake.INSTANCE.vamoquererabrir(),
                        new Delay(0.2),
                        Lift.INSTANCE.vamoquerersubir(),
                        new Delay(0.2),
                        Extend.INSTANCE.vamoquerersubirne(),
                        new Delay(0.2),
                        Intake.INSTANCE.vamoquererfechar()
                )
        );







        }

    }


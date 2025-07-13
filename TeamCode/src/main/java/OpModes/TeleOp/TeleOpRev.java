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

import Subsystems.Elevator;
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
                Intake.INSTANCE,
                Elevator.INSTANCE);
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


        //------ Pose Maxima Linear ------
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(
                Extend.INSTANCE::ToHigh
        );
        //------ Pose Minima Linear ------
        gamepadManager.getGamepad2().getDpadRight().setPressedCommand(
                Extend.INSTANCE::ToLow
        );
        //------ Pose Maxima Braco ------
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(
                Lift.INSTANCE::upAltasAventuras
        );
        //------ Pose Minima Braco ------
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(
                Lift.INSTANCE::upBaixasAventuras
        );
        //------ Pendurar-se ------
        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                Elevator.INSTANCE::Hang
        );

        //------ Abre a garra ------
        gamepadManager.getGamepad2().getA().setPressedCommand(
                Intake.INSTANCE::openclaw
        );
        //------ Fecha a garra ------
        gamepadManager.getGamepad2().getB().setPressedCommand(
                Intake.INSTANCE::closeclaw
        );
        //------ Abre o angulo da Garra ------
        gamepadManager.getGamepad2().getY().setPressedCommand(
                Intake.INSTANCE::openangle
        );
        //------ Fecha o angulo da Garra ------
        gamepadManager.getGamepad2().getX().setPressedCommand(
                Intake.INSTANCE::closeangle
        );
        //------ Controle Manual do Braço ------


        gamepadManager.getGamepad2().getRightStick().setStateChangeCommand(stick -> {
            float x = stick.getFirst();
            float y = stick.getSecond();

            if (y < -0.5) {
                return Lift.INSTANCE.ToHigh();
            } else if (y > 0.5) {
                return Lift.INSTANCE.ToLow();
            }

            return null;
        });
        }

    }


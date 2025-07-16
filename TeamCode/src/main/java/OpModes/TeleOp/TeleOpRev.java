package OpModes.TeleOp;

import static com.rowanmcalpin.nextftc.ftc.OpModeData.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
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


    public Command driverControlled;


    @Override
    public void onInit() {
        // -----Inicializa os Motores-----
        frontLeftMotor = new MotorEx(FLmotor);
        backLeftMotor = new MotorEx(BLmotor);
        backRightMotor = new MotorEx(BRmotor);
        frontRightMotor = new MotorEx(FRmotor);




        // -----Direção dos Motores-----
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = new MotorEx[] { frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor };




    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();


        //------ RESET ENCODERS ------





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
//------ Fecha o angulo da Garra ------
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(
                Lift.INSTANCE::controlepower
        );
        //------ Controle Manual do Braço ------




    }
        @Override
        public void onUpdate(){
        telemetry.addData("Posição vertical", Lift.INSTANCE.line_motor_stage1git .getCurrentPosition());
        telemetry.addData("Posição vertical", Extend.INSTANCE.line_motor_stage2.getCurrentPosition());
        telemetry.update();
    }
}


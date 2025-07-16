package Subsystems;




import static com.rowanmcalpin.nextftc.ftc.OpModeData.telemetry;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static Subsystems.Values.LiftPID.target;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

import Subsystems.Values.ExtendPID;
import Subsystems.Values.LiftPID;
import Subsystems.Values.RConstants;


    public class Lift extends Subsystem {

    public static final Lift INSTANCE = new Lift();

    private Lift() {}

    public MotorEx line_motor_stage2;
    public PIDFController l_liftController = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, new StaticFeedforward(LiftPID.f));
        public PIDFController colletPID = new PIDFController(0.0, 0.0000, 0.0000);
    public String angulo= "Angulo";



    public Command resetZero() {
        return new InstantCommand(() -> { line_motor_stage2.resetEncoder(); });

    }
    public Command toTarget() {
        return new RunToPosition(
                        line_motor_stage2,
                        target,
                        l_liftController,this);

    }

        public Command ToLow() {
            double pos = line_motor_stage2.getCurrentPosition();
            return new RunToPosition(
                            line_motor_stage2,
                            Math.min(pos+50, RConstants.minPosition_arm),
                            l_liftController,
                            this);
        }

    public Command ToHigh() {
        double pos = line_motor_stage2.getCurrentPosition();
        return new RunToPosition(
                        line_motor_stage2,
                        Math.max(pos-50, RConstants.maxPosition_arm),
                        l_liftController,
                        this
                );

    }
        public Command upAltasAventuras() {
            return new RunToPosition(
                    line_motor_stage2,
                    RConstants.maxPosition_arm,
                    l_liftController,
                    this


            );

        }
        public Command upBaixasAventuras() {
            return new RunToPosition(
                    line_motor_stage2,
                    RConstants.minPosition_arm,
                    l_liftController,
                    this


            );

        }
        public Command controlepower() {
            return new SetPower(
                    line_motor_stage2,
                    0.4


            );

        }



    public Command getDefaultCommand() {
        return new HoldPosition(
                line_motor_stage2,
                colletPID,
                this);
    }



    public void initialize(){
        line_motor_stage2 = new MotorEx(angulo);
        line_motor_stage2.setDirection(DcMotorSimple.Direction.REVERSE);



    }

}



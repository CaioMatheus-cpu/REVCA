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
    public String braco = "motor2";
    public Command resetZero() {
        return new InstantCommand(() -> { line_motor_stage2.resetEncoder(); });
    }
    public Command toTarget() {
        return new ParallelGroup(
                new RunToPosition(line_motor_stage2,
                        target,
                        l_liftController)
        );


    }

    public Command vamoquererdescer() {
        return new RunToPosition(line_motor_stage2,
                RConstants.minPosition_arm,
                l_liftController);
     }

    public Command vamoquerersubir() {
        return new RunToPosition(line_motor_stage2,
                RConstants.maxPosition_arm,
                l_liftController);
    }



    public Command getDefaltCommand() {
        return new HoldPosition(line_motor_stage2, l_liftController);
    }

    public Command powerControl(double power) {
        return new SetPower(line_motor_stage2,
                power);
    }

    public void initialize(){
        line_motor_stage2 = new MotorEx(braco);
        line_motor_stage2.resetEncoder();
        line_motor_stage2.setDirection(DcMotorSimple.Direction.FORWARD);
        l_liftController.setSetPointTolerance(LiftPID.tollerancel);
    }

}



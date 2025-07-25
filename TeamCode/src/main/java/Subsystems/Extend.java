package Subsystems;

import static Subsystems.Values.LiftPID.target;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;

import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

import Subsystems.Values.ExtendPID;
import Subsystems.Values.RConstants;

public class Extend extends Subsystem {
    public static final Extend INSTANCE = new Extend();

    private Extend(){}


    public MotorEx line_motor_stage2;
    public PIDFController e_controller = new PIDFController(ExtendPID.p, ExtendPID.i, ExtendPID.d, new StaticFeedforward(ExtendPID.f));
    public PIDFController colletPID = new PIDFController(0.0, 0.000, 0.000);
    public String linear = "Linear";




    public Command resetZero() {
        return new InstantCommand(() -> { line_motor_stage2.resetEncoder(); });
    }

    public Command ToHigh() {
        return new RunToPosition(line_motor_stage2,
            RConstants.maxPosition,
                e_controller,this);

    }
    public Command ToLow() {
        return new RunToPosition(line_motor_stage2,
                    RConstants.minPosition,
                    e_controller,this);

    }
    public Command getDefaultCommand(){
        return new HoldPosition(line_motor_stage2, colletPID, this);
    }
    @Override
    public void initialize(){
        line_motor_stage2 = new MotorEx(linear);
        line_motor_stage2.setDirection(DcMotorSimple.Direction.FORWARD);
    }

}

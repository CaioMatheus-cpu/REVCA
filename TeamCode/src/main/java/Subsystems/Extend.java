package Subsystems;

import static Subsystems.Values.LiftPID.target;

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


    public MotorEx line_motor_stage1;
    public PIDFController e_controller = new PIDFController(ExtendPID.p, ExtendPID.i, ExtendPID.d, new StaticFeedforward(ExtendPID.f));
    public String linear = "motor";

    public Command getDefaultCommand(){
        return new HoldPosition(line_motor_stage1, e_controller);
    }


    public Command resetZero() {
        return new InstantCommand(() -> { line_motor_stage1.resetEncoder(); });
    }

    public Command toTarget() {
        return new RunToPosition(line_motor_stage1,
                        target,
                        e_controller,this);
    }
    public Command vamoquerersubirne() {
        return new RunToPosition(line_motor_stage1,
            RConstants.maxPosition,
                e_controller,this);

    }
    public Command vamoquererdescer() {
        return new RunToPosition(line_motor_stage1,
                    RConstants.minPosition,
                e_controller,this);

    }



    public Command vamoquererfuncionar(double power) {
        return new SetPower(line_motor_stage1,
                power);
    }

    @Override
    public void initialize(){
        line_motor_stage1 = new MotorEx(linear);
        line_motor_stage1.resetEncoder();
        e_controller.setSetPointTolerance(ExtendPID.tollerance);
    }

}

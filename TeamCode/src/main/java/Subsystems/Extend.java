package Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;

import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
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

    public Command resetZero() {
        return new InstantCommand(() -> { line_motor_stage1.resetEncoder(); });
    }



    public Command highbasket() {
        return new RunToPosition(line_motor_stage1,
            RConstants.HiGHBASKET_lin,
                e_controller);

    }    public Command retract() {
        return new RunToPosition(line_motor_stage1,
                    RConstants.GROUND_lin,
                e_controller);

    }



    public Command getDefaltCommand(){
        return new SetPower ( line_motor_stage1, 0);
    }

    @Override
    public void initialize(){
        line_motor_stage1 = new MotorEx(linear);
        line_motor_stage1.resetEncoder();
        line_motor_stage1.setDirection(DcMotorSimple.Direction.REVERSE);
        e_controller.setSetPointTolerance(ExtendPID.tollerance);
    }

}

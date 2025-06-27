package Subsystems;




import static com.rowanmcalpin.nextftc.ftc.OpModeData.telemetry;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

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
    public Command toLow() {
        return new RunToPosition(line_motor_stage2,
                RConstants.GROUND_arm,
                l_liftController);
     }

    public Command toHigh() {
        return new RunToPosition(line_motor_stage2,
                RConstants.HIGHBASKET_arm,
                l_liftController);
    }
    public Command powerControl(double power) {
        return new SetPower(line_motor_stage2,
                power);
    }

    @Override
    public void initialize() {
        line_motor_stage2 = new MotorEx(braco);
    }

}



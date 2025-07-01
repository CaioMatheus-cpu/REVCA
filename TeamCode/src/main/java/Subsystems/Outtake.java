package Subsystems;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorConstantPowerParameters;


public class Outtake extends Subsystem {
    public static final Outtake INSTANCE = new Outtake();
    public MotorEx levantar;
    String outtake = "outtake";
    public PIDFController l_liftController = new PIDFController(0, 0, 0, new StaticFeedforward(0));
    private Outtake() {}
    double power = 1.0;
    int power1 = 0;




    public Command pendurar
            () {
            return new SetPower(levantar, power, this);
    }
    public Command paradegirarjesus () {
        return new SetPower(levantar, power, this);
    }
}



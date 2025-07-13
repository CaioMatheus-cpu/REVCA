package Subsystems;



import static com.rowanmcalpin.nextftc.ftc.OpModeData.telemetry;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MotorToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

;

public class Elevator extends Subsystem {
    public static final Subsystems.Elevator INSTANCE = new Subsystems.Elevator();

    private Elevator() {
    }
    public MotorEx motor;
    public String motorName = "levantar";
    public Command Hang(){
        return new SetPower(motor,
        1.0);
    }

    public void initialize(){
        motor = new MotorEx(motorName);
        motor.resetEncoder();
    }

}


package Subsystems;



import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Intake extends Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    public Servo Garra, Angulo;

    public String garra = "garra";
    public String angulo = "angulo";
    public Command vamoquererabrir(){
        return new ServoToPosition(Garra,
                1,
                this);
    }

    public Command vamoquererfechar () {
        return new ServoToPosition(Garra,
                0,
                this);
    }

    public Command vamoquererangular() {
        return new ServoToPosition(Angulo,
                1,
                this);
    }

    public Command vamoquererangularfechar() {
        return new ServoToPosition(Angulo,
                -1,
                this);
            }

    @Override
    public void initialize() {
        Garra = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, garra);
        Angulo= OpModeData.INSTANCE.getHardwareMap().get(Servo.class, angulo);
        Garra.setDirection(Servo.Direction.FORWARD);
        Angulo.setDirection(Servo.Direction.FORWARD);

    }



}




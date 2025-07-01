package Subsystems.Values;
import com.acmerobotics.dashboard.config.Config;

@Config

public class LiftPID {
    public static int target = 0;
    public static int tollerancel = 35;
    public static double p = 0.001;
    public static double i = 0.0001;
    public static double d = 0.0001;
    public static double f = 0.001;
}

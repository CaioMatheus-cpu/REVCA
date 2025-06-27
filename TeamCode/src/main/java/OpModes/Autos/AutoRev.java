package OpModes.Autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;





import Subsystems.Intake;
import Subsystems.Lift;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import Subsystems.Extend;

public class AutoRev extends PedroOpMode {
    public AutoRev(){
        super(Intake.INSTANCE, Lift.INSTANCE, Extend.INSTANCE);
    }
    private final Pose

            basket0Start = new Pose(8.0, 107.0, Math.toRadians(100)),
            basket0End = new Pose(8.0, 120.0, Math.toRadians(100)),
            basket1Start = new Pose(8.0, 120.0, 0),
            basket1End = new Pose(18.0, 120.0, 0),
            sample1Start = new Pose(18.0, 120.0, Math.toRadians(20)),
            sample1End = new Pose(23.0, 120.0, Math.toRadians(135)),
            basket2Start = new Pose(23.0, 120.0, Math.toRadians(0)),
            basket2End = new Pose(24.0, 137.0, Math.toRadians(0)),
            sample2Start = new Pose(24.0, 137.0, Math.toRadians(30)),
            sample2End = new Pose(30.0, 134.0, Math.toRadians(171)),
            basket3Start = new Pose(30.0, 134.0, Math.toRadians(30)),
            basket3End = new Pose(30.0, 132.0, Math.toRadians(45)),
            sample3Start = new Pose(30.0, 132.0, Math.toRadians(20)),
            sample3End = new Pose(31.0, 133.0, Math.toRadians(165)),
            submersivoStart = new Pose(31.0, 133.0, Math.toRadians(10)),
            submersivoEnd = new Pose(70.0, 100.0, Math.toRadians(263));

    private PathChain basket0, basket1, sample1, basket2, sample2, basket3, sample3, submersivo;

    public void buildPaths() {

        basket0 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(basket0Start), new Point(basket0End)))
                .setConstantHeadingInterpolation(Math.toRadians(100))
                .build();

        basket1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(basket1Start), new Point(basket1End)))
                .setTangentHeadingInterpolation()
                .build();

        sample1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample1Start), new Point(sample1End)))
                .setLinearHeadingInterpolation(Math.toRadians(20), Math.toRadians(135))
                .build();

        basket2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(basket2Start), new Point(basket2End)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        sample2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample2Start), new Point(sample2End)))
                .setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(171))
                .setReversed(true)
                .build();

        basket3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(basket3Start), new Point(basket3End)))
                .setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(45))
                .build();

        sample3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample3Start), new Point(sample3End)))
                .setLinearHeadingInterpolation(Math.toRadians(20), Math.toRadians(165))
                .setReversed(true)
                .build();

        submersivo = follower.pathBuilder()
                .addPath(new BezierLine(new Point(submersivoStart), new Point(submersivoEnd)))
                .setLinearHeadingInterpolation(Math.toRadians(10), Math.toRadians(263))
                .build();
    }
    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(basket0Start);
        buildPaths();
    }
    @Override
    public void onStartButtonPressed() {
        new SequentialGroup(
                new FollowPath(basket0),
                new FollowPath(basket1),
                new FollowPath(sample1),
                new FollowPath(basket2),
                new FollowPath(sample2),
                new FollowPath(basket3),
                new FollowPath(sample3),
                new FollowPath(submersivo)
        ).invoke();
    }
}



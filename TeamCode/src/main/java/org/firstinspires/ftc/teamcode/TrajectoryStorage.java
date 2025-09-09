package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class TrajectoryStorage {
    static final Pose2d start_pos = new Pose2d(0, 0,0);
    static TrajectorySequence sample_traj;

    public static void buildTrajectories(SampleMecanumDrive drive) {
        // Define Trajectories Here
        sample_traj = drive.trajectorySequenceBuilder(start_pos)
                .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                .splineToSplineHeading(new Pose2d(4*12, 2*12, Math.toRadians(180)), Math.toRadians(180))

                .build();
    }
}

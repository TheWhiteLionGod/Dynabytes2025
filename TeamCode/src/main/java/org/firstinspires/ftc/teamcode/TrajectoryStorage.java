package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class TrajectoryStorage {
    public static TrajectorySequence sample_traj;
    public static TrajectorySequence getRedBalls;
    public static TrajectorySequence getBlueBalls;
    public static TrajectorySequence shootRed;
    public static TrajectorySequence shootBlue;

    public static void buildTrajectories(SampleMecanumDrive drive) {
        // Define Trajectories Here
        sample_traj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                .splineToSplineHeading(new Pose2d(4*12, 2*12, Math.toRadians(180)), Math.toRadians(180))

                .build();

        getRedBalls = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-3 * 12, -4 * 12, Math.toRadians(270)))
                .waitSeconds(1)
                .build();

        getBlueBalls = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(-3 * 12, 4 * 12, Math.toRadians(90)))
                .waitSeconds(1)
                .build();

        shootRed = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(0, -1 * 12))
                .lineToLinearHeading(new Pose2d(4.5 * 12, -5 * 12, Math.toRadians(315)))
                .build();

        shootBlue = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToConstantHeading(new Vector2d(0, 12))
                .lineToLinearHeading(new Pose2d(4.5 * 12, 5 * 12, Math.toRadians(45)))
                .build();
    }
}

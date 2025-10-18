package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public enum Trajectories {
    SAMPLE_TRAJ,
    GET_RED_BALLS, GET_BLUE_BALLS,
    SHOOT_RED, SHOOT_BLUE,
    GO_RED_BASE, GO_BLUE_BASE;

    public TrajectorySequence getTrajectory(SampleMecanumDrive drive) {
        switch (this) {
            case SAMPLE_TRAJ:
                return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                        .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                        .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                        .splineToSplineHeading(new Pose2d(4*12, 2*12, Math.toRadians(180)), Math.toRadians(180))

                        .build();
                
            case GET_RED_BALLS:
                return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .lineToLinearHeading(new Pose2d(-3 * 12, -4 * 12, Math.toRadians(270)))
                        .waitSeconds(1)
                        .build();
                
            case GET_BLUE_BALLS:
                return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .lineToLinearHeading(new Pose2d(-3 * 12, 4 * 12, Math.toRadians(90)))
                        .waitSeconds(1)
                        .build();
                
            case SHOOT_RED:
                return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .lineToConstantHeading(new Vector2d(0, -1 * 12))
                        .lineToLinearHeading(new Pose2d(4.5 * 12, -5 * 12, Math.toRadians(315)))
                        .build();
                
            case SHOOT_BLUE:
                return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .lineToConstantHeading(new Vector2d(0, 12))
                        .lineToLinearHeading(new Pose2d(4.5 * 12, 5 * 12, Math.toRadians(45)))
                        .build();

            case GO_RED_BASE:
                return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineToLinearHeading(Positions.RED_BASE.getPose2D(), 0)
                        .build();

            case GO_BLUE_BASE:
                return drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                        .splineToLinearHeading(Positions.BLUE_BASE.getPose2D(), 0)
                        .build();
                
            default:
                throw new IllegalStateException("Unknown trajectory type");
        }
    }
}

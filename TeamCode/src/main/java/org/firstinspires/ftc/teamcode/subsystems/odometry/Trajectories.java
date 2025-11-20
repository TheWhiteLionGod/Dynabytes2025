package org.firstinspires.ftc.teamcode.subsystems.odometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.firstinspires.ftc.roadrunner.trajectorysequence.TrajectorySequence;

public enum Trajectories {
    SAMPLE_TRAJ, SHOOT_RED, SHOOT_BLUE;

    public TrajectorySequence build(RoadRunner roadRunner) {
        switch (this) {
            case SAMPLE_TRAJ:
                return roadRunner.trajSeqBuilder(roadRunner.getPose())
                        .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                        .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                        .splineToLinearHeading(new Pose2d(2*12, 0, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(0, -2*12, 0), 0)

                        .splineToSplineHeading(new Pose2d(4*12, 2*12, Math.toRadians(180)), Math.toRadians(180))
                        .build();
                
            case SHOOT_RED:
                return roadRunner.trajSeqBuilder(roadRunner.getPose())
                        .lineToLinearHeading(Positions.START.get())
                        .lineToLinearHeading(Positions.SCORE_RED.get())
                        .build();
                
            case SHOOT_BLUE:
                return roadRunner.trajSeqBuilder(roadRunner.getPose())
                        .lineToLinearHeading(Positions.START.get())
                        .lineToLinearHeading(Positions.SCORE_BLUE.get())
                        .build();
                
            default:
                throw new IllegalStateException("Unknown trajectory type");
        }
    }

    public static TrajectorySequence trajTo(Pose2d pose, RoadRunner roadRunner) {
        return roadRunner.trajSeqBuilder(roadRunner.getPose())
                .lineToLinearHeading(pose)
                .build();
    }
}

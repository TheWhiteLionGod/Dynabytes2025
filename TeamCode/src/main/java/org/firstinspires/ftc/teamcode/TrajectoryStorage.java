package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class TrajectoryStorage {
    static Pose2d start_pos = new Pose2d(0, 0,0);
    static Pose2d red_down_pos = new Pose2d(-5.25 * 12, -2.75 * 12, Math.toRadians(0));
    static Pose2d blue_down_pos = new Pose2d(-5.25 * 12, 2.75 * 12, Math.toRadians(0));
    static Pose2d red_up_pos = new Pose2d(4*12, -4*12, Math.toRadians(135));
    static Pose2d blue_up_pos = new Pose2d(4*12, 4*12, Math.toRadians(225));
    static TrajectorySequence sample_traj;
    static TrajectorySequence getRedBalls;
    static TrajectorySequence getBlueBalls;
    static TrajectorySequence shootRed;
    static TrajectorySequence shootBlue;

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

        getRedBalls = drive.trajectorySequenceBuilder(start_pos)
                .lineToLinearHeading(new Pose2d(-3 * 12, -4 * 12, Math.toRadians(270)))
                .waitSeconds(1)
                .build();

        getBlueBalls = drive.trajectorySequenceBuilder(start_pos)
                .lineToLinearHeading(new Pose2d(-3 * 12, 4 * 12, Math.toRadians(90)))
                .waitSeconds(1)
                .build();

        shootRed = drive.trajectorySequenceBuilder(getRedBalls.end())
                .lineToConstantHeading(new Vector2d(0, -1 * 12))
                .lineToLinearHeading(new Pose2d(4.5 * 12, -5 * 12, Math.toRadians(315)))
                .build();

        shootBlue = drive.trajectorySequenceBuilder(getBlueBalls.end())
                .lineToConstantHeading(new Vector2d(0, 12))
                .lineToLinearHeading(new Pose2d(4.5 * 12, 5 * 12, Math.toRadians(45)))
                .build();
    }

    public static void setStartPos(Pose2d pos) {
        start_pos = pos;
    }
}

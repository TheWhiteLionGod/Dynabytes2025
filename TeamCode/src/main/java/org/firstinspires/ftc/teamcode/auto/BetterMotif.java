package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Positions;

@Autonomous(name="\"Better\" Motif", group="FTC2026")
public class BetterMotif extends Dynawheels {
    TrajectorySequence traj1, traj2, traj3;
    int[] shootOrder;

    @Override
    public void config() {
        super.config();
        roadRunner.setPose(Positions.BLUE_DOWN.get());

        traj1 = roadRunner.trajSeqBuilder(roadRunner.getPose())
                // Shooting Preloaded Balls
                .splineToSplineHeading(Positions.SCORE_BLUE.get(), Positions.SCORE_BLUE.get().getHeading())
                .build();

        traj2 = roadRunner.trajSeqBuilder(traj1.end())
                // Facing Obelisk
                .turn(Positions.START.get().getHeading() - roadRunner.getPose().getHeading())
                .addDisplacementMarker(() -> {
                    // Getting Shooting Order From Obelisk
                    int tagId = Constants.PGP_TAG;
                    switch (tagId) {
                        case Constants.GPP_TAG: shootOrder = new int[]{1, 0, 0}; break;
                        case Constants.PGP_TAG: shootOrder = new int[]{0, 1, 0}; break;
                        case Constants.PPG_TAG:
                        default: shootOrder = new int[]{0, 0, 1}; break;
                    }
                })

                // Picking Up Balls
                .addDisplacementMarker(() -> roller.forward())
                .splineToSplineHeading(Positions.BLUE_PGP.get(), Positions.BLUE_PGP.get().getHeading())
                .addDisplacementMarker(() -> roller.stop())

                // Sorting First Ball While Moving
                .addDisplacementMarker(() -> {
                    if (shootOrder[0] == 1) carousel.findGreenBall();
                    else carousel.findPurpleBall();
                })
                .splineToSplineHeading(Positions.SCORE_BLUE.get(), Positions.SCORE_BLUE.get().getHeading())
                .build();

        traj3 = roadRunner.trajSeqBuilder(traj2.end())
                // Moving 2 Feet Away from Obelisk
                .splineToSplineHeading(
                        Positions.START.get().minus(new Pose2d(2*12, 0, 0)),
                        Positions.START.get().getHeading()
                )
                .build();
    }

    @Override
    public void run() {
        // Shooting Preloaded Balls
        roadRunner.followTraj(traj1);
        for (int i = 0; i < 3; i++) {
            shooter.start();
            while (!shooter.isIdle()) {
                shooter.updateState();
            }

            carousel.spin();
            while (!carousel.isIdle()) {
                carousel.updateState();
            }
        }

        // Getting Balls
        roadRunner.followTraj(traj2);

        // Shooting First Ball
        shooter.start();
        while (!shooter.isIdle()) {
            shooter.updateState();
        }

        // Shooting Second & Third Balls
        for (int i = 1; i < 3; i++) {
            if (shootOrder[i] == 1) carousel.findGreenBall();
            else carousel.findPurpleBall();

            while(!carousel.isIdle()) {
                carousel.updateState();
            }

            shooter.start();
            while (!shooter.isIdle()) {
                shooter.updateState();
            }
        }

        roadRunner.followTraj(traj3);
    }
}

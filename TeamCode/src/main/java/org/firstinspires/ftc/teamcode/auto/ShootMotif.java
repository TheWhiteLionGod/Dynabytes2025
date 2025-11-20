package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Positions;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Trajectories;

@Autonomous(name="Shoot Motif", group="FTC2026")
public class ShootMotif extends Dynawheels {
    @Override
    public void run() {
        roadRunner.setPose(Positions.BLUE_DOWN.get());

        // Shooting Preloaded Balls
        roadRunner.followTraj(Trajectories.SHOOT_BLUE.build(roadRunner));
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

        // Getting Motif from Obelisk
        roadRunner.followTraj(Trajectories.trajTo(Positions.START.get(), roadRunner));
        int tagId = camera.getTagId();

        int[] shootOrder; // 0 is Purple, 1 is Green
        switch (tagId) {
            case Constants.GPP_TAG:
                shootOrder = new int[]{1, 0, 0};
                break;

            case Constants.PGP_TAG:
                shootOrder = new int[]{0, 1, 0};
                break;

            case Constants.PPG_TAG:
            default:
                shootOrder = new int[]{0, 0, 1};
                break;
        }

        // Getting Balls
        roller.forward();
        roadRunner.followTraj(Trajectories.trajTo(Positions.BLUE_PGP.get(), roadRunner));
        roller.stop();

        // Sorting First Ball While Driving
        if (shootOrder[0] == 1) carousel.findGreenBall();
        else carousel.findPurpleBall();

        roadRunner.followTrajAsync(Trajectories.SHOOT_BLUE.build(roadRunner));
        // Updating Roadrunner and Carousel is Still Moving
        while (!carousel.isIdle() || !roadRunner.isIdle()) {
            carousel.updateState();
            roadRunner.update();
        }

        // Shooting First Ball
        shooter.start();
        while (!shooter.isIdle()) {
            shooter.updateState();
        }

        // Shooting Second and Third Ball
        for (int i = 1; i < 3; i++) {
            if (shootOrder[i] == 1) carousel.findGreenBall();
            else carousel.findPurpleBall();

            while (!carousel.isIdle()) {
                carousel.updateState();
            }

            shooter.start();
            while (!shooter.isIdle()) {
                shooter.updateState();
            }
        }

        // Moving Robot 2 Feet Backwards from Starting Position
        roadRunner.followTraj(Trajectories.trajTo(
                Positions.START.get().minus(new Pose2d(2*12, 0, 0)),
                roadRunner
        ));
    }
}

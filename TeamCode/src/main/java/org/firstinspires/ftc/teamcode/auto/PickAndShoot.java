package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Positions;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Trajectories;

@Autonomous(name="Pick and Shoot", group="FTC2026")
public class PickAndShoot extends Dynawheels {
    @Override
    public void run() {
        // Starts in Blue Down Position
        roadRunner.setPose(Positions.BLUE_DOWN.get());

        // Getting Blue PGP Ball Set
        roller.forward();
        roadRunner.followTraj(Trajectories.trajTo(Positions.BLUE_PGP.get(), roadRunner));
        roller.stop();

        // Going to Shooting Zone
        roadRunner.followTraj(Trajectories.trajTo(Positions.SCORE_BLUE.get(), roadRunner));

        // Shooting All Three Balls
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
    }
}

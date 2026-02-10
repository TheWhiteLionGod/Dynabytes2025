package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.TurnDrivetrainTo;

@Autonomous(name="AutoFarBlue", group="Auto")
public class AutoFarBlue extends Dynawheels {
    @Override
    public void config() {
        super.config();
        Coords.setCurrentPos(new Coords(-12, -60, 180, Coords.Unit.RoadRunner));
    }

    @Override
    public void run() {
        // Starting Shooter Ahead of Time
        shooter.start();

        drivetrain.backward();
        sleep(500);
        drivetrain.stop();

        Action turnDrivetrainTo = new TurnDrivetrainTo(
                drivetrain, imu,
                new Coords(197, Coords.Unit.RoadRunner)
        );

        telemetry.clear();
        while (!turnDrivetrainTo.run()) {
            telemetry.addData("Turning...", imu.getYaw().toRoadRunner().yaw);
            telemetry.update();
        }
        drivetrain.stop();

        // Shooting Balls
        sleep(5000); // Waiting For Shooter To Start Up
        for (int i = 0; true; i++) {
            lift.up();
            sleep(1000);

            lift.down();
            sleep(500);

            if (i == 2) break;
            carousel.spin();
            sleep(1000);
        }
        shooter.stop();

        // Getting New Balls
        turnDrivetrainTo = new TurnDrivetrainTo(
            drivetrain, imu,
            new Coords(270, Coords.Unit.RoadRunner)
        );

        telemetry.clear();
        while (!turnDrivetrainTo.run()) {
            telemetry.addData("Turning...", imu.getYaw().toRoadRunner().yaw);
            telemetry.update();
        }
        drivetrain.stop();

        roller.forward();
        drivetrain.forward();
        sleep(1000);
        drivetrain.stop();
        roller.stop();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        Coords.setCurrentPos(new Coords(-12, -54, 197, Coords.Unit.RoadRunner));
    }
}

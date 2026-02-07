package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.TurnDrivetrainTo;

@Autonomous(name="AutoBlue", group="Auto")
public class AutoBlue extends Dynawheels {
    @Override
    public void config() {
        super.config();

        Coords.setCurrentPos(new Coords(-60, -60, 225, Coords.Unit.RoadRunner));
    }

    @Override
    public void run() {
        // Turning On Shooter
        shooter.start();

        // Moving to Shooting Position
        drivetrain.forward();
        sleep(800);
        drivetrain.stop();

        // Shooting Balls
        for (int i = 0; true; i++) {
            lift.up();
            sleep(500);

            lift.down();
            if (i == 2) break;
            sleep(500);

            carousel.spin();
            sleep(1000);
        }
        shooter.stop();

        // Grabbing New Set of Balls
        drivetrain.forward();
        sleep(200);
        drivetrain.stop();

        // Moving Out Of Shooting Zone
        Action turnDrivetrain = new TurnDrivetrainTo(
                drivetrain, imu,
                new Coords(180, Coords.Unit.RoadRunner)
        );

        telemetry.clear();
        while (turnDrivetrain.run()) {
            telemetry.addData("Turning to 180", imu.getYaw().toRoadRunner().yaw);
            telemetry.update();
        }

        drivetrain.forward();
        sleep(500);
        drivetrain.stop();

        // Turning to Balls
        turnDrivetrain = new TurnDrivetrainTo(
                drivetrain, imu,
                new Coords(90, Coords.Unit.RoadRunner)
        );

        telemetry.clear();
        while (turnDrivetrain.run()) {
            telemetry.addData("Turning to 90", imu.getYaw().toRoadRunner().yaw);
            telemetry.update();
        }

        // Grabbing Balls
        roller.forward();

        drivetrain.forward();
        sleep(400);
        drivetrain.stop();

        carousel.spin();
        sleep(400);

        drivetrain.forward();
        sleep(400);
        drivetrain.stop();

        roller.stop();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        Coords.setCurrentPos(new Coords(-60, 12, 90, Coords.Unit.RoadRunner));
    }
}

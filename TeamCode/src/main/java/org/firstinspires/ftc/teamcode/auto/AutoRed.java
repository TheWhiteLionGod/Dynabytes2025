package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.TurnDrivetrainTo;

@Autonomous(name="AutoRed", group="Auto")
public class AutoRed extends Dynawheels {
    @Override
    public void config() {
        super.config();

        Coords.setCurrentPos(new Coords(-60, -60, 135, Coords.Unit.RoadRunner));
    }

    @Override
    public void run() {
        // Turning On Shooter
        shooter.setPwr(0.625);
        shooter.start();

        // Moving to Shooting Position
        drivetrain.forward();
        sleep(1500);
        drivetrain.stop();

        // Shooting Balls
        sleep(2250);
        for (int i = 0; true; i++) {
            lift.up();
            sleep(750);

            lift.down();
            sleep(750);

            if (i == 2) break;
            carousel.spin();
            sleep(750);
        }
        shooter.stop();

        // Turning to Balls
        Action turnDrivetrainTo = new TurnDrivetrainTo(
                drivetrain, imu,
                new Coords(237.5, Coords.Unit.RoadRunner)

        );

        telemetry.clear();
        while (!turnDrivetrainTo.run()) {
            telemetry.addData("Turning...", imu.getYaw().toRoadRunner().yaw);
            telemetry.update();
        }
        drivetrain.stop();

        // Grabbing Balls
        roller.forward();
        drivetrain.forward();
        sleep(1250);
        drivetrain.stop();

        sleep(500);
        roller.stop();

        drivetrain.backward();
        sleep(1000);
        drivetrain.stop();

        turnDrivetrainTo = new TurnDrivetrainTo(
                drivetrain, imu,
                new Coords(135, Coords.Unit.RoadRunner)
        );

        telemetry.clear();
        shooter.start();

        while (!turnDrivetrainTo.run()) {
            telemetry.addData("Turning...", imu.getYaw().toRoadRunner().yaw);
            telemetry.update();
        }
        drivetrain.stop();

        // Shooting Balls
        sleep(2250);
        for (int i = 0; true; i++) {
            lift.up();
            sleep(750);

            lift.down();
            sleep(750);

            if (i == 2) break;
            carousel.spin();
            sleep(750);
        }
        shooter.stop();

        drivetrain.left();
        sleep(1250);
        drivetrain.stop();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        Coords.setCurrentPos(new Coords(-60, 12, 90, Coords.Unit.RoadRunner));
    }
}

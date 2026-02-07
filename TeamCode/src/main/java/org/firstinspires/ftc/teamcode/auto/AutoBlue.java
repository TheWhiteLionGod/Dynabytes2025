package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Coords;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.TurnDrivetrainTo;

@Autonomous(name="AutoBlue", group="Auto")
public class AutoBlue extends Dynawheels {
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
                new Coords(-45, Coords.Unit.Imu)
        );

        telemetry.clear();
        while (turnDrivetrain.run()) {
            telemetry.addData("Turning to -45", imu.getYaw().yaw);
            telemetry.update();
        }

        drivetrain.forward();
        sleep(500);
        drivetrain.stop();

        // Turning to Balls
        turnDrivetrain = new TurnDrivetrainTo(
                drivetrain, imu,
                new Coords(-135, Coords.Unit.Imu)
        );

        telemetry.clear();
        while (turnDrivetrain.run()) {
            telemetry.addData("Turning to -135", imu.getYaw().yaw);
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
}

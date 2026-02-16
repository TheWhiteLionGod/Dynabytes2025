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
        shooter.setPwr(0.625);
        shooter.start();

        // Moving to Shooting Position
        drivetrain.forward();
        sleep(1500);
        drivetrain.stop();

        // Shooting Balls
        sleep(1750);
        for (int i = 0; true; i++) {
            lift.up();
            sleep(750);

            lift.down();
            sleep(250);

            if (i == 2) break;
            carousel.spin();
            sleep(350);
        }
        shooter.stop();

        // Turning to Balls
        Action turnDrivetrainTo = new TurnDrivetrainTo(
                drivetrain, imu,
                new Coords(110, Coords.Unit.RoadRunner)
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
        sleep(1375);
        drivetrain.stop();

        sleep(500);
        roller.stop();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        Coords.setCurrentPos(new Coords(-60, 12, 90, Coords.Unit.RoadRunner));
    }
}

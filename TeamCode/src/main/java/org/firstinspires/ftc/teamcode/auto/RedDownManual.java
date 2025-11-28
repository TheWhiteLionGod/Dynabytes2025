package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Roller;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

@Autonomous(name="Red Down Manual", group="FTC2026")
public class RedDownManual extends Dynawheels {
    ImuSensor imu;

    @Override
    public void config() {
        drivetrain = new FieldDrive(hardwareMap, telemetry);
        roller = new Roller(hardwareMap, telemetry);
        carousel = new Carousel(hardwareMap, telemetry);
        shooter = new Shooter(hardwareMap, telemetry);
        imu = new ImuSensor(hardwareMap, telemetry);
        telemetry.update();
    }

    @Override
    public void run() {
        // Going to Shooting Zone
        drivetrain.fieldDrive(1, 0, 0, imu.getYawRadians());
        sleep(2250);

        // Reading Obelisk
        int tagId = Constants.PGP_TAG;
        int[] shootingOrder;
        switch (tagId) {
            case Constants.GPP_TAG:
                shootingOrder = new int[]{1, 0, 0};
                break;

            case Constants.PGP_TAG:
                shootingOrder = new int[]{0, 1, 0};
                break;

            case Constants.PPG_TAG:
            default:
                shootingOrder = new int[]{0, 0, 1};
                break;
        }

        drivetrain.turnTo(imu, 135);

        for (int i = 0; i < 3; i++) {
            // Sorting Ball
            if (shootingOrder[i] == 1) carousel.findGreenBall();
            else carousel.findPurpleBall();

            while (!carousel.isIdle()) {
                carousel.updateState();
            }

            // Shooting First Ball
            shooter.start();
            while (shooter.isIdle()) {
                shooter.updateState();
            }
        }

        drivetrain.backward(imu.getYawRadians());
        sleep(500);

        drivetrain.turnTo(imu, 270);

        drivetrain.right(imu.getYawRadians());
        roller.forward();
        sleep(1500);

        roller.stop();
        drivetrain.left(imu.getYawRadians());
        sleep(1500);

        drivetrain.forward(imu.getYawRadians());
        sleep(500);

        drivetrain.turnTo(imu, 135);

        for (int i = 0; i < 3; i++) {
            // Sorting Ball
            if (shootingOrder[i] == 1) carousel.findGreenBall();
            else carousel.findPurpleBall();

            while (!carousel.isIdle()) {
                carousel.updateState();
            }

            // Shooting First Ball
            shooter.start();
            while (shooter.isIdle()) {
                shooter.updateState();
            }
        }

        drivetrain.backward(imu.getYawRadians());
        sleep(600);
    }

    @Override
    public void cleanup() {
        drivetrain.stop();
        roller.stop();
        carousel.stop();
        shooter.stop();
    }
}

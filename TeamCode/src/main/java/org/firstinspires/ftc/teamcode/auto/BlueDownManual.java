package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Roller;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

@Autonomous(name="Blue Down Manual", group="FTC2026")
public class BlueDownManual extends Dynawheels {
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
        drivetrain.forward(imu.getYawRadians());
        sleep(1050);

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

        drivetrain.turnTo(imu, 225);
        drivetrain.stop();

        shooter.stopMotor = false;
        for (int i = 0; i < 3; i++) {
            // Sorting Ball
            if (shootingOrder[i] == 1) carousel.findGreenBall();
            else carousel.findPurpleBall();

            while (!carousel.isIdle()) {
                carousel.updateState();
                telemetry.update();
            }

            // Shooting First Ball
            shooter.start();
            while (!shooter.isIdle()) {
                shooter.updateState();
                telemetry.update();
            }
        }
        shooter.stopMotor = true;
        shooter.stop();

        drivetrain.backward(imu.getYawRadians());
        sleep(500);

        drivetrain.turnTo(imu, 90);
        drivetrain.stop();

        drivetrain.left(imu.getYawRadians());
        roller.forward();
        sleep(1000);

        roller.stop();
        drivetrain.right(imu.getYawRadians());
        sleep(1000);

        drivetrain.forward(imu.getYawRadians());
        sleep(600);

        drivetrain.turnTo(imu, 225);
        drivetrain.stop();

        shooter.stopMotor = false;
        for (int i = 0; i < 3; i++) {
            // Sorting Ball
            if (shootingOrder[i] == 1) carousel.findGreenBall();
            else carousel.findPurpleBall();

            while (!carousel.isIdle()) {
                carousel.updateState();
                telemetry.update();
            }

            // Shooting First Ball
            shooter.start();
            while (!shooter.isIdle()) {
                shooter.updateState();
                telemetry.update();
            }
        }
        shooter.stopMotor = true;
        shooter.stop();

        drivetrain.backward(imu.getYawRadians());
        sleep(600);
    }

    @Override
    public void cleanup() {
        drivetrain.stop();
        roller.stop();
        carousel.stop();
        shooter.stopMotor = true;
        shooter.stop();
    }
}

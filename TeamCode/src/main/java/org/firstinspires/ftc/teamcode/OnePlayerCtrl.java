package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Roller;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;

@Disabled
//@TeleOp(name="1 Player Controller", group="FTC2026")
public class OnePlayerCtrl extends Dynawheels {
    ImuSensor imu;
    @Override
    public void config() {
        drivetrain = new FieldDrive(hardwareMap, telemetry);
        imu = new ImuSensor(hardwareMap, telemetry);
        roller = new Roller(hardwareMap, telemetry);
        carousel = new Carousel(hardwareMap, telemetry);
        lift = new Lift(hardwareMap, telemetry);
        shooter = new Shooter(hardwareMap, telemetry);

        telemetry.update();
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            // Drivetrain
            handleDrivetrain();

            // Intake
            handleIntake();

            // Carousel
            handleCarousel();

            // Outtake
            handleOuttake();

            telemetry.update();
            sleep(50);
        }
    }

    public void handleDrivetrain() {
        if (gamepad1.left_stick_y != 0 ||
                gamepad1.left_stick_x != 0 ||
                gamepad1.right_stick_x != 0) {

            drivetrain.fieldDrive(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    imu.getYaw()
            );
        }
        else {
            drivetrain.stop();
        }

        /*
        // Go to Red Base
        if (gamepad1.left_stick_button && roadRunner.isIdle()) {
            roadRunner.followTraj(
                    Trajectories.trajTo(Positions.RED_BASE.get(), roadRunner)
            );
        }
        // Go to Blue Base
        else if (gamepad1.right_stick_button && roadRunner.isIdle()) {
            roadRunner.followTraj(
                    Trajectories.trajTo(Positions.BLUE_BASE.get(), roadRunner)
            );
        }
        */
    }

    public void handleIntake() {
        if (gamepad1.left_trigger != 0) {
            roller.forward();
        }
        else if (gamepad1.left_bumper) {
            roller.reverse();
        }
        else {
            roller.stop();
        }
    }

    public void handleCarousel() {
        // Updating Carousel State
        carousel.updateState();

        if (gamepad1.dpad_left) {
            carousel.spin(Constants.CAROUSEL_POS_1);
        }
        else if (gamepad1.dpad_up) {
            carousel.spin(Constants.CAROUSEL_POS_2);
        }
        else if (gamepad1.dpad_right) {
            carousel.spin(Constants.CAROUSEL_POS_3);
        }
        else if (gamepad1.dpad_down) {
            carousel.spin();
        }
        else if (gamepad1.x) {
            carousel.findGreenBall();
        }
        else if (gamepad1.b) {
            carousel.findPurpleBall();
        }
    }

    public void handleOuttake() {
        // Lift
        if (gamepad1.y) lift.up();
        else if (gamepad1.a) lift.down();

        // Shooter
        if (gamepad1.right_trigger != 0) shooter.forward();
        else if (gamepad1.right_bumper) shooter.stop();
    }

    public void cleanup() {
        drivetrain.stop();
        roller.stop();
        carousel.stop();
        shooter.stop();
        lift.down();
    }
}

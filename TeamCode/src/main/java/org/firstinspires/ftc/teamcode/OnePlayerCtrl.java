package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.odometry.Positions;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Trajectories;

@TeleOp(name="1 Player Controller", group="FTC2026")
public class OnePlayerCtrl extends Dynawheels {
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
        roadRunner.update();

        if (gamepad1.left_stick_y != 0 ||
                gamepad1.left_stick_x != 0 ||
                gamepad1.right_stick_x != 0) {

            drivetrain.fieldDrive(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    roadRunner.getPose().getHeading()
            );
        }
        else {
            drivetrain.stop();
        }

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
        // Update State
        shooter.updateState();

        // Lift
        if (gamepad1.y) {
            shooter.lift.up();
        }
        else if (gamepad1.a) {
            shooter.lift.down();
        }

        // Shooter
        if (gamepad1.right_trigger != 0) {
            shooter.start();
        }
        else if (gamepad1.right_bumper) {
            shooter.stop();
        }
    }
}

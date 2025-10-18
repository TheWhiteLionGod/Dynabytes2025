package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Controller extends Robot {
    @Override
    public void configure() {
        BL = hardwareMap.dcMotor.get("back_left_motor");
        FL = hardwareMap.dcMotor.get("front_left_motor");
        FR = hardwareMap.dcMotor.get("front_right_motor");
        BR = hardwareMap.dcMotor.get("back_right_motor");

        BL.setDirection(DcMotor.Direction.REVERSE);
        FL.setDirection(DcMotor.Direction.REVERSE);

        OM = hardwareMap.dcMotor.get("OM");
        IM = hardwareMap.dcMotor.get("IM");

        Carousel = hardwareMap.servo.get("Carousel");
        Lift = hardwareMap.servo.get("Lift");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        drive = new SampleMecanumDrive(hardwareMap);
    }

    @Override
    public void run() {
        while (canRun()) {
            updateOdometry();

            // Switching Gears
            if (gamepad1.right_bumper) {
                changeGearMode(1);
            }
            else if (gamepad1.left_bumper) {
                changeGearMode(-1);
            }

            // Field Drive Movement
            if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                fieldDriveMove(gamepad1.left_stick_x, gamepad1.left_stick_y);
            }
            else {
                Reset();
            }

            // Turning
            if (gamepad1.right_stick_x != 0) {
                Turn(gamepad1.right_stick_x);
            }

            // Going to "Base"
            if (gamepad1.dpad_left) {
                goToRedBase();
            }
            else if (gamepad1.dpad_right) {
                goToBlueBase();
            }

            // Roller Controls
            if (gamepad1.left_trigger != 0) {
                forwardIntake();
            }
            else {
                stopIntake();
            }

            // Launcher Controls
            if ((gamepad1.right_trigger != 0) &&
                    (SpinCarousel == null || !RunLauncher.isAlive())) {
                if (RunLauncher == null || !RunLauncher.isAlive()) {
                    LauncherRunnable = new RunLauncherThread(OM, Lift);
                    RunLauncher = new Thread(LauncherRunnable);
                    RunLauncher.start();
                }
                else if (LauncherRunnable.canInterrupt) {
                    RunLauncher.interrupt();
                }
            }

            // Carousel Controls
            if ((SpinCarousel == null || !SpinCarousel.isAlive())
                    && (RunLauncher == null || !RunLauncher.isAlive())) {
                Runnable run = null;
                if (gamepad1.x) {
                    run = new SpinCarouselThread(colorSensor, Carousel, Constants.GREEN_BALL);
                }
                else if (gamepad1.a) {
                    run = new SpinCarouselThread(colorSensor, Carousel, Constants.PURPLE_BALL);
                }

                if (run != null) {
                    SpinCarousel = new Thread(run);
                    SpinCarousel.start();
                }
            }
        }

        Reset();
        SpinCarousel.interrupt(); // Stopping Carousel Spinning
        RunLauncher.interrupt(); // Stopping Shooting Mechanism
    }
}

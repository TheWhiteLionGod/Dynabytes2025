package org.firstinspires.ftc.dynabytes;

import android.graphics.Color;

import com.acmerobotics.roadrunner.path.EmptyPathSegmentException;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.dynabytes.drive.SampleMecanumDrive;
import org.firstinspires.ftc.dynabytes.trajectorysequence.TrajectorySequence;

@Disabled
@TeleOp(name = "Robot", group = "FTC2025")
public abstract class Robot extends LinearOpMode {
    public SampleMecanumDrive drive; // Roadrunner Driver
    public DcMotor BL, FL, FR, BR; // Wheel Motors
    public DcMotor roller, launcher; // Intake Outtake Motors
    public Servo carousel, lift; // Carousel and Lift Servos
    public ColorSensor colorSensor; // Color Sensor
    public FunctionThread spinCarouselThread, runLauncherThread; // Threads
    public LinearOpMode game = this; // Game Object

    double yawAngle; // Yaw Angle Data
    float[] hsvValues = {0F, 0F, 0F}; // Color Sensing Data

    // Gear Mode Variables
    double gearSwitchTime = 0.0;
    double curGearMode = 3.0;

    @Override
    public void runOpMode() {
        configure();
        waitForStart();
        run();
    }

    // "Configure" Runs Before Op Mode, "Run" Runs After.
    abstract public void configure();
    abstract public void run();

    // Checks if OpModeIsActive
    public boolean canRun() {
        return game.opModeIsActive();
    }

    // Update Odometry via RoadRunner
    public void updateOdometry() {
        drive.update();
        yawAngle = Math.toDegrees(drive.getPoseEstimate().getHeading());
    }

    // Making Robot Follow Trajectory
    public void moveRobot(TrajectorySequence traj) {
        drive.followTrajectorySequenceAsync(traj);

        // Updating Robot Position
        while (canRun() && drive.isBusy()) {
            drive.update();
            telemetry.addData("Robot Position: ", drive.getPoseEstimate());
            telemetry.update();
        }

        // Printing Deviation in Position Once Completed
        telemetry.addData("Expected End Position: ", traj.end());
        telemetry.addData("Current Position: ", drive.getPoseEstimate());
        telemetry.update();
    }

    // These Functions Make Robot Go To Base
    public void goToRedBase() {
        try {
            drive.followTrajectorySequence(Trajectories.GO_RED_BASE.getTrajectory(drive));
        }
        catch (EmptyPathSegmentException e) {
            System.out.println("Empty Path Segment Exception has Occurred\n" +
                    "This means that the robot is already at the defined position");
        }
    }

    public void goToBlueBase() {
        try {
            drive.followTrajectorySequence(Trajectories.GO_BLUE_BASE.getTrajectory(drive));
        }
        catch (EmptyPathSegmentException e) {
            System.out.println("Empty Path Segment Exception has Occurred\n" +
                    "This means that the robot is already at the defined position");
        }
    }

    // Change Gear Mode of Robot
    public void changeGearMode(int change_val) {
        if (getRuntime() - gearSwitchTime >= 0.25) {
            curGearMode = Math.min(Math.max(curGearMode + change_val, 1), Constants.MAX_GEAR);
            gearSwitchTime = getRuntime();

            telemetry.addData("Current Gear Mode", curGearMode);
            telemetry.update();
        }
    }


    // Field Drive Movement
    public void fieldDriveMove(double pwr_x, double pwr_y) {
        /* Adjust Joystick X/Y inputs by navX MXP yaw angle */
        double yaw_radians = Math.toRadians(yawAngle);
        double temp = pwr_y * Math.cos(yaw_radians) + pwr_x * Math.sin(yaw_radians);
        pwr_x = -pwr_y * Math.sin(yaw_radians) + pwr_x * Math.cos(yaw_radians);
        pwr_y = temp;

        /* At this point, Joystick X/Y (strafe/forward) vectors have been */
        /* rotated by the gyro angle, and can be sent to drive system */
        moveDrivetrain(pwr_x, pwr_y);
    }

    // Regular Movement
    public void moveDrivetrain(double pwrx, double pwry) {
        double gear_pwr = curGearMode / Constants.MAX_GEAR;
        BL.setPower(gear_pwr*(-pwrx-pwry));
        FR.setPower(gear_pwr*(-pwrx-pwry));

        FL.setPower(gear_pwr*(pwrx-pwry));
        BR.setPower(gear_pwr*(pwrx-pwry));
    }

    // Turning
    public void turnDrivetrain(double pwr) {
        double gear_pwr = curGearMode / Constants.MAX_GEAR;
        BL.setPower(gear_pwr*pwr);
        FR.setPower(gear_pwr*-pwr);

        FL.setPower(0);
        BR.setPower(0);
    }

    // Stopping Drivetrain
    public void stopDrivetrain() {
        BL.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
    }

    // Intake Functions
    public void forwardIntake() {
        roller.setPower(0.5);
    }

    public void backwardIntake() {
        roller.setPower(-0.5);
    }

    public void stopIntake() {
        roller.setPower(0);
    }

    // Updating Color Sensor
    public void updateHSV() {
        Color.RGBToHSV(
                (colorSensor.red() * Constants.HUE_SCALE_FACTOR),
                (colorSensor.green() * Constants.HUE_SCALE_FACTOR),
                (colorSensor.blue() * Constants.HUE_SCALE_FACTOR),
                hsvValues
        );
    }

    // Rotating Carousel to Next Position
    public void spinCarousel() {
        double cur_pos = carousel.getPosition();
        if (cur_pos == Constants.CAROUSEL_POS_1) {
            carousel.setPosition(Constants.CAROUSEL_POS_2);
        }
        else if (cur_pos == Constants.CAROUSEL_POS_2) {
            carousel.setPosition(Constants.CAROUSEL_POS_3);
        }
        else if (cur_pos == Constants.CAROUSEL_POS_3) {
            carousel.setPosition(Constants.CAROUSEL_POS_1);
        }
    }

    // Spinning Carousel to Given Position
    public void spinCarousel(double new_pos) {
        carousel.setPosition(new_pos);
    }

    // Spinning Carousel for Specific Ball Color
    public void findGreenBall() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            updateHSV();
            if (hsvValues[0] >= Constants.GREEN_HUE_MIN
                    && hsvValues[0] <= Constants.GREEN_HUE_MAX) {
                return;
            }

            spinCarousel();
            Thread.sleep(500);
        }
    }

    public void findPurpleBall() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            updateHSV();
            if (hsvValues[0] >= Constants.PURPLE_HUE_MIN
                    && hsvValues[0] <= Constants.PURPLE_HUE_MAX) {
                return;
            }
            spinCarousel();
            Thread.sleep(500);
        }
    }

    public void startLauncher() throws InterruptedException {
        launcher.setPower(1);
        Thread.sleep(1000);

        lift.setPosition(Constants.LIFT_OUT_POS);
        Thread.sleep(1000);
    }

    public void stopLauncher() {
        lift.setPosition(Constants.LIFT_IN_POS);
        launcher.setPower(0);
    }
}
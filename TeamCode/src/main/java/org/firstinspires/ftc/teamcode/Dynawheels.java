package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Roller;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Camera;
import org.firstinspires.ftc.teamcode.subsystems.odometry.RoadRunner;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;

@Disabled
public abstract class Dynawheels extends LinearOpMode {
    public FieldDrive drivetrain;
    public RoadRunner roadRunner;
    public Roller roller;
    public Carousel carousel;
    public Shooter shooter;
    public Camera camera;

    @Override
    public void runOpMode() {
        config(); // Configuring Subsystems
        waitForStart(); // Waiting for Start
        run(); // Main Function
        cleanup(); // Resetting Subsystems
    }

    public void config() {
        drivetrain = new FieldDrive(hardwareMap, telemetry);
        roadRunner = new RoadRunner(hardwareMap, telemetry);
        roller = new Roller(hardwareMap, telemetry);
        carousel = new Carousel(hardwareMap, telemetry);
        shooter = new Shooter(hardwareMap, telemetry);
        camera = new Camera(hardwareMap, telemetry);

        roadRunner.setPose(Constants.currentPose);
        telemetry.update();
    }

    public abstract void run();

    public void cleanup() {
        drivetrain.stop();
        roadRunner.stop();
        roller.stop();
        carousel.stop();
        shooter.stop();
        camera.stop();
        Constants.currentPose = roadRunner.getPose();
    }
}

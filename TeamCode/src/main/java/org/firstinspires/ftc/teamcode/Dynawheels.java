package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.FieldDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Roller;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Camera;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ImuSensor;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Carousel;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;

@Disabled
public abstract class Dynawheels extends LinearOpMode {
    public FieldDrive drivetrain;
    public Roller roller;
    public Carousel carousel;
    public Lift lift;
    public Shooter shooter;
    public ColorInput colorSensor;
    public ImuSensor imu;

    @Override
    public void runOpMode() {
        config(); // Configuring Subsystems
        waitForStart(); // Waiting for Start
        run(); // Main Function
        cleanup(); // Resetting Subsystems
    }

    public void config() {
        drivetrain = new FieldDrive(hardwareMap, telemetry);
        roller = new Roller(hardwareMap, telemetry);
        carousel = new Carousel(hardwareMap, telemetry);
        lift = new Lift(hardwareMap, telemetry);
        shooter = new Shooter(hardwareMap, telemetry);
        colorSensor = new ColorInput(hardwareMap, telemetry);
        imu = new ImuSensor(hardwareMap, telemetry);

        telemetry.update();
    }

    public abstract void run();

    public void cleanup() {
        drivetrain.stop();
        roller.stop();
        shooter.stop();
        lift.down();
    }
}

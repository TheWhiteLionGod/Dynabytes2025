package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.TrajectoryStorage;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "AutoPickAndShoot", group = "FTC2025")
public class AutoPickAndShoot extends Robot {
    @Override
    public void configure() {
        // Creating Drivetrain
        drive = new SampleMecanumDrive(hardwareMap);
        TrajectoryStorage.start_pos = TrajectoryStorage.blue_down_pos;

        TrajectoryStorage.buildTrajectories(drive); // Builds Trajectory Here
        drive.setPoseEstimate(TrajectoryStorage.start_pos);
    }

    @Override
    public void run() {
        // Moving Robot
        moveRobot(TrajectoryStorage.getBlueBalls);
        moveRobot(TrajectoryStorage.shootBlue);
    }
}
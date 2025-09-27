package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.TrajectoryStorage;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Disabled
@TeleOp(name = "SampleAuto", group = "FTC2025")
public class SampleAuto extends Robot {
    @Override
    public void configure() {
        // Creating Drivetrain
        drive = new SampleMecanumDrive(hardwareMap);
        TrajectoryStorage.buildTrajectories(drive); // Builds Trajectory Here
        drive.setPoseEstimate(TrajectoryStorage.start_pos);
    }

    public void run() {
        // Running Trajectory
        moveRobot(TrajectoryStorage.sample_traj);
    }
}
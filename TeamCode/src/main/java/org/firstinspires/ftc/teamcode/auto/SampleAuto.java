package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Positions;
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
        drive.setPoseEstimate(Positions.START.getPose2D());

        TrajectoryStorage.buildTrajectories(drive); // Builds Trajectory Here
    }

    public void run() {
        // Running Trajectory
        moveRobot(TrajectoryStorage.sample_traj);
    }
}
package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PositionStorage;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.TrajectoryStorage;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "AutoPickAndShoot", group = "FTC2025")
public class AutoPickAndShoot extends Robot {
    @Override
    public void configure() {
        // Creating Drivetrain
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(PositionStorage.blue_down_pos);

        TrajectoryStorage.buildTrajectories(drive); // Builds Trajectory Here
    }

    @Override
    public void run() {
        // Moving Robot
        moveRobot(TrajectoryStorage.getBlueBalls);

        TrajectoryStorage.buildTrajectories(drive);
        moveRobot(TrajectoryStorage.shootBlue);
    }
}
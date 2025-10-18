package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Positions;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Trajectories;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "AutoPickAndShoot", group = "FTC2025")
public class AutoPickAndShoot extends Robot {
    @Override
    public void configure() {
        // Creating Drivetrain
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(Positions.BLUE_DOWN.getPose2D());
    }

    @Override
    public void run() {
        // Moving Robot
        moveRobot(Trajectories.GET_BLUE_BALLS.getTrajectory(drive));
        moveRobot(Trajectories.SHOOT_BLUE.getTrajectory(drive));
    }
}
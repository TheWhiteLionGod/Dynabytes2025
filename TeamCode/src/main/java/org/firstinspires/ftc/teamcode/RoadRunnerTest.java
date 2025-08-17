package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.ArrayList;

@TeleOp(name = "RoadRunnerTest", group = "FTC2025")
public class RoadRunnerTest extends LinearOpMode implements Autonomous{
    SampleMecanumDrive drive;
    ArrayList<Trajectory> trajectories;   
    
    @Override
    public void runOpMode() {
        configureRobot();
        waitForStart();

        if (opModeIsActive()) {
            // Loop Over All Trajectories(ORDER MATTERS)
            for (Trajectory traj : trajectories) {
                // Moving Robot
                drive.followTrajectoryAsync(traj);
                
                // Updating Robot Position
                while (opModeIsActive() && drive.isBusy()) {
                    drive.update();
                    telemetry.addData("Robot Position: ", drive.getPoseEstimate());
                }

                // Printing Deviation in Position Once Completed
                System.out.println(traj.end());
                System.out.println(drive.getPoseEstimate());
            }
        }
    }

    @Override
    public void configureRobot() {
        // Creating Drivetrain
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d start_pos = new Pose2d(0, 0,0);
        drive.setPoseEstimate(start_pos);

        // Defining Trajectories
        Trajectory traj = drive.trajectoryBuilder(start_pos)
                .lineToConstantHeading(new Vector2d(12, 0))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj.end())
                .splineToConstantHeading(new Vector2d(-5, -12), 0)
                .build();
        
        // "Saving Trajectories"
        trajectories = new ArrayList<Trajectory>();
        trajectories.add(traj);
        trajectories.add(traj2);
    }

    // Since We Are Using Road Runner, We Need To Implement Dummy Methods Since They Are Required
    @Override
    public void moveDriveTrain(double pwrx, double pwry) {
        System.out.println("[ERROR] moveDriveTrain Function Called in Autonomous For Unknown Reasons");
    }
    
    @Override
    public void turnDriveTrain(double pwr) {
        System.out.println("[ERROR] turnDriveTrain Function Called in Autonomous For Unknown Reasons");
    }
    
    @Override
    public void resetDriveTrain() {
        System.out.println("[ERROR] resetDriveTrain Function Called in Autonomous For Unknown Reasons");
    }
}
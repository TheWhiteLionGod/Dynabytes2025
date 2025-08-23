package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
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
                    telemetry.update();
                }

                // Printing Deviation in Position Once Completed
                telemetry.addData("Expected End Position: ", traj.end());
                telemetry.addData("Current Position: ", drive.getPoseEstimate());
                telemetry.update();
            }
        }
    }

    @Override
    public void configureRobot() {
        // Creating Drivetrain
        Pose2d start_pos = new Pose2d(0, 0,0);
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(start_pos);

        // Defining Trajectories
        Trajectory traj = drive.trajectoryBuilder(start_pos)
                // The * 12 converts 4 feet to inches
                .lineToConstantHeading(new Vector2d(4 * 12, 0))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj.end())
                .splineToConstantHeading(new Vector2d(0, -2 * 12), 0)
                .build();
        
        // "Saving Trajectories"
        // The order that you add the trajectories to the list is the order they will be called.
        trajectories = new ArrayList<>();
        trajectories.add(traj);
        trajectories.add(traj2);
    }

    // Since We Are Using Road Runner, We Need To Implement Dummy Methods Since They Are Required
    @Override
    public void moveDriveTrain(double pwrx, double pwry) {
        telemetry.addData(
                "[ERROR] moveDriveTrain Function Called in Autonomous For Unknown Reasons: ",
                drive.getPoseEstimate()
        );
        telemetry.update();
    }
    
    @Override
    public void turnDriveTrain(double pwr) {
        telemetry.addData(
                "[ERROR] turnDriveTrain Function Called in Autonomous For Unknown Reasons: ",
                drive.getPoseEstimate()
        );
        telemetry.update();
    }
    
    @Override
    public void resetDriveTrain() {
        telemetry.addData(
                "[ERROR] resetDriveTrain Function Called in Autonomous For Unknown Reasons: ",
                drive.getPoseEstimate()
        );
        telemetry.update();
    }
}
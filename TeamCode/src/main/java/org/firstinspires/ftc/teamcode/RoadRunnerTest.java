package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "RoadRunnerTest", group = "FTC2025")
public class RoadRunnerTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d start_pos = new Pose2d(0, 0,0);
        drive.setPoseEstimate(start_pos);

        Trajectory traj = drive.trajectoryBuilder(start_pos)
                .lineToConstantHeading(new Vector2d(12, 0))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj.end())
                .splineToConstantHeading(new Vector2d(-5, -12), 0)
                .build();

        waitForStart();

        if (opModeIsActive()) {
            drive.followTrajectoryAsync(traj);
            while (opModeIsActive() && drive.isBusy()) {
                drive.update();
                telemetry.addData("Robot Position: ", drive.getPoseEstimate());
            }

            System.out.println(traj.end());
            System.out.println(drive.getPoseEstimate());

//            Second TRAJECTORY

            drive.followTrajectoryAsync(traj2);
            while (opModeIsActive() && drive.isBusy()) {
                drive.update();
                telemetry.addData("Robot Position: ", drive.getPoseEstimate());
            }

            System.out.println(traj2.end());
            System.out.println(drive.getPoseEstimate());
        }
    }
}
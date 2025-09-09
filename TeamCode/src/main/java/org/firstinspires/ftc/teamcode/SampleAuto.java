package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

//@Disabled
@TeleOp(name = "SampleAuto", group = "FTC2025")
public class SampleAuto extends LinearOpMode {
    SampleMecanumDrive drive;
    
    @Override
    public void runOpMode() {
        // Creating Drivetrain
        drive = new SampleMecanumDrive(hardwareMap);
        TrajectoryStorage.buildTrajectories(drive); // Builds Trajectory Here
        drive.setPoseEstimate(TrajectoryStorage.start_pos);

        waitForStart();

        // Moving Robot
        drive.followTrajectorySequenceAsync(TrajectoryStorage.sample_traj);

        // Updating Robot Position
        while (opModeIsActive() && drive.isBusy()) {
            drive.update();
            telemetry.addData("Robot Position: ", drive.getPoseEstimate());
            telemetry.update();
        }

        // Printing Deviation in Position Once Completed
        telemetry.addData("Expected End Position: ", TrajectoryStorage.sample_traj.end());
        telemetry.addData("Current Position: ", drive.getPoseEstimate());
        telemetry.update();
    }
}
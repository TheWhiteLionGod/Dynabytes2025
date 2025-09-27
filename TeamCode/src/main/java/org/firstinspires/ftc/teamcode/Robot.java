package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Disabled
@TeleOp(name = "Robot", group = "FTC2025")
public abstract class Robot extends LinearOpMode {
    public SampleMecanumDrive drive;
    public LinearOpMode game = this;

    @Override
    public void runOpMode() {
        configure();
        waitForStart();
        run();
    }

    public boolean canRun() {
        return game.opModeIsActive();
    }

    public void moveRobot(TrajectorySequence traj) {
        drive.followTrajectorySequenceAsync(traj);

        // Updating Robot Position
        while (canRun() && drive.isBusy()) {
            drive.update();
            telemetry.addData("Robot Position: ", drive.getPoseEstimate());
            telemetry.update();
        }

        // Printing Deviation in Position Once Completed
        telemetry.addData("Expected End Position: ", TrajectoryStorage.sample_traj.end());
        telemetry.addData("Current Position: ", drive.getPoseEstimate());
        telemetry.update();
    }

    abstract public void configure();
    abstract public void run();
}
package org.firstinspires.ftc.teamcode.subsystems.odometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class RoadRunner {

    private final SampleMecanumDrive drive;
    private final Telemetry telemetry;

    public RoadRunner(HardwareMap hardwareMap, Telemetry telemetry) {
        drive = new SampleMecanumDrive(hardwareMap);
        this.telemetry = telemetry;
    }

    public Pose2d getPose() {
        telemetry.addData("Robot Position", drive.getPoseEstimate());
        return drive.getPoseEstimate();
    }

    public void setPose(Pose2d pose) {
        telemetry.addData("New Robot Position", pose);
        drive.setPoseEstimate(pose);
    }

    public TrajectorySequenceBuilder trajSeqBuilder(Pose2d start) {
        return drive.trajectorySequenceBuilder(start);
    }

    public void update() {
        drive.update();
    }

    public boolean isIdle() {
        return !drive.isBusy();
    }

    public void followTraj(TrajectorySequence traj) {
        drive.followTrajectorySequenceAsync(traj);

        // Updating Robot Position
        while (drive.isBusy()) {
            drive.update();
            telemetry.addData("Robot Position", drive.getPoseEstimate());
            telemetry.update();
        }

        // Printing Deviation in Position Once Completed
        telemetry.addData("Expected End Position", traj.end());
        telemetry.addData("Current Position", drive.getPoseEstimate());
    }

    public void followTrajAsync(TrajectorySequence traj) {
        drive.followTrajectorySequenceAsync(traj);
    }

    public void stop() {
        followTraj(Trajectories.trajTo(getPose(), this));
    }
}

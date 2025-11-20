package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Positions;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Trajectories;

@Disabled
@Autonomous(name="Sample Auto", group="FTC2026")
public class SampleAuto extends Dynawheels {
    @Override
    public void run() {
        roadRunner.setPose(Positions.START.get());
        roadRunner.followTraj(Trajectories.SAMPLE_TRAJ.build(roadRunner));
    }
}

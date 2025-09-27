package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.auto.AutoPickAndShoot;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name="Game", group="FTC2025")
public class Game extends LinearOpMode {
    SampleMecanumDrive drive;
    Robot auto;
    Robot teleop;

    @Override
    public void runOpMode() {
        drive = new SampleMecanumDrive(hardwareMap);
        auto = new AutoPickAndShoot();
        teleop = new LeftDrive();

        waitForStart();

        // Starting Autonomous
        auto.configure();
        auto.drive = drive;

        auto.game = this;
        auto.run();

        // Fixing Drive Position
        drive.setPoseEstimate(auto.drive.getPoseEstimate());

        // Starting TeleOp
        teleop.configure();
        teleop.drive = drive;

        teleop.game = this;
        teleop.run();
    }
}

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "LeftDrive", group = "DYNABYTES")
public class TeleOp extends Dynawheels {
    @Override
    public void runOpMode() {
        // Initializing
        configureRobot();
        waitForStart();
        
        while (opModeIsActive()) {
            // Update Yaw
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            this.yaw_angle = orientation.getYaw(AngleUnit.DEGREES);

            // Switching Gears
            if (gamepad1.dpad_up) {changeGearMode(1);}
            else if (gamepad1.dpad_down) {changeGearMode(-1);}

            // Field Drive Movement
            if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                fieldMoveDriveTrain(gamepad1.left_stick_x, gamepad1.left_stick_y);}
            else {resetDriveTrain();}

            // Turning
            if (gamepad1.right_stick_x != 0) {turnDriveTrain(gamepad1.right_stick_x);}
        }
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "LeftDrive", group = "DYNABYTES")
public class LeftDrive extends Dynawheels {
    @Override
    public void run() {
        while (canRun()) {
            // Update Yaw Data Every Iteration
            updateYaw();

            // Switching Gears
            if (gamepad1.right_bumper) {changeGearMode(1);}
            else if (gamepad1.left_bumper) {changeGearMode(-1);}

            // Field Drive Movement
            if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                fieldMoveDriveTrain(gamepad1.left_stick_x, gamepad1.left_stick_y);}
            else {resetDriveTrain();}

            // Turning
            if (gamepad1.right_stick_x != 0) {turnDriveTrain(gamepad1.right_stick_x);}

            // Going to "Base"
            if (gamepad1.dpad_left) {goToRedBase();}
            else if (gamepad1.dpad_right) {goToBlueBase();}
        }
        resetDriveTrain();
    }
}

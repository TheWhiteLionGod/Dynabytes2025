package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.odometry.Camera;

@Disabled
//@TeleOp(name="CAMERA TEST", group="TEST")
public class CameraTest extends Dynawheels {
    @Override
    public void config() {
        camera = new Camera(hardwareMap, telemetry);
        telemetry.update();
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            camera.getTagId();
            camera.getTagXYArea();
            telemetry.update();
        }
    }

    @Override
    public void cleanup() {
        camera.stop();
    }
}

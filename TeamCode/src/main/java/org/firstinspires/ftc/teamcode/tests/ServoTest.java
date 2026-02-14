package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Dynawheels;

@TeleOp(name="Servo Test", group="TEST")
@Disabled
public class ServoTest extends Dynawheels {
    Servo servo;
    @Override
    public void config() {
        servo = hardwareMap.get(Servo.class, "Gate");
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            if (gamepad1.yWasPressed()) {
                servo.setPosition(servo.getPosition() + 0.1);
            }
            else if (gamepad1.aWasPressed()) {
                servo.setPosition(servo.getPosition() - 0.1);
            }

            telemetry.addData("Servo Pos", servo.getPosition());
            telemetry.update();
        }
    }

    @Override
    public void cleanup() {

    }
}

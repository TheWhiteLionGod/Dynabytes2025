package org.firstinspires.ftc.teamcode.subsystems.transfer;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public class Lift implements Subsystem {
    private final Servo leftLift, rightLift;
    private final Telemetry telemetry;

    public Lift(HardwareMap hardwareMap, Telemetry telemetry) {
        leftLift = hardwareMap.get(Servo.class, "LeftLIFT");
        rightLift = hardwareMap.get(Servo.class, "RightLIFT");
        leftLift.setDirection(Servo.Direction.REVERSE);

        leftLift.setPosition(Constants.LIFT_DOWN_POS);
        rightLift.setPosition(Constants.LIFT_DOWN_POS);
        this.telemetry = telemetry;
        telemetry.addData("Lift", "Initialized");
    }

    public boolean isUp() {
        return leftLift.getPosition() == Constants.LIFT_UP_POS;
    }

    public void up() {
        rightLift.setPosition(Constants.LIFT_UP_POS);
        try { Thread.sleep(Constants.LIFT_DELAY_TIME); }
        catch (InterruptedException ignored) {}
        leftLift.setPosition(Constants.LIFT_UP_POS);
        telemetry.addData("Lift", "Up");
    }

    public void down() {
        leftLift.setPosition(Constants.LIFT_DOWN_POS);
        rightLift.setPosition(Constants.LIFT_DOWN_POS);
        telemetry.addData("Lift", "Down");
    }
}

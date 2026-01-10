package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Roller {
    private final DcMotorEx roller;
    private final Telemetry telemetry;

    public Roller(HardwareMap hardwareMap, Telemetry telemetry) {
        roller = hardwareMap.get(DcMotorEx.class, "IntakeRoller");
        roller.setDirection(DcMotorEx.Direction.REVERSE);
        this.telemetry = telemetry;
        telemetry.addData("Roller", "Initialized");
    }

    public void forward() {
        roller.setPower(1);
        telemetry.addData("Roller", "Forward");
    }

    public void reverse() {
        roller.setPower(-1);
        telemetry.addData("Roller", "Reverse");
    }

    public void stop() {
        roller.setPower(0);
        telemetry.addData("Roller", "Stopped");
    }
}

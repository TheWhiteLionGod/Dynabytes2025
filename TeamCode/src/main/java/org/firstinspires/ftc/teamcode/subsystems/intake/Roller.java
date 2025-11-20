package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Roller {
    private final DcMotorEx roller;
    private final Telemetry telemetry;

    public Roller(HardwareMap hardwareMap, Telemetry telemetry) {
        roller = hardwareMap.get(DcMotorEx.class, "Roller");
        roller.setDirection(DcMotorEx.Direction.REVERSE);
        this.telemetry = telemetry;
    }

    public void forward() {
        roller.setPower(1);
        telemetry.addLine("Roller Moving Forward");
    }

    public void reverse() {
        roller.setPower(-1);
        telemetry.addLine("Roller Moving Reverse");
    }

    public void stop() {
        roller.setPower(0);
        telemetry.addLine("Roller Stopped");
    }
}

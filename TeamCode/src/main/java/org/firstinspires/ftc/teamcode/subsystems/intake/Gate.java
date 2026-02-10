package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public class Gate implements Subsystem {
    private final Servo gate;
    private final Telemetry telemetry;

    public Gate(HardwareMap hardwareMap, Telemetry telemetry) {
        gate = hardwareMap.get(Servo.class, "Gate");
        this.telemetry = telemetry;
        telemetry.addData("Gate", "Initialized");
    }

    public void open() {
        gate.setPosition(Constants.GATE_OPEN_POS);
        telemetry.addData("Gate", "Open");
    }

    public void close() {
        gate.setPosition(Constants.GATE_CLOSED_POS);
        telemetry.addData("Gate", "Closed");
    }
}

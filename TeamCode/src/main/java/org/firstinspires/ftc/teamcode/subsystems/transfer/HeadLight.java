package org.firstinspires.ftc.teamcode.subsystems.transfer;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HeadLight {
    private final Servo greenLight, purpleLight;
    private final Telemetry telemetry;

    public HeadLight(HardwareMap hardwareMap, Telemetry telemetry) {
        greenLight = hardwareMap.get(Servo.class, "GreenLight");
        purpleLight = hardwareMap.get(Servo.class, "PurpleLight");
        this.telemetry = telemetry;
        telemetry.addData("HeadLights", "Initialized");
    }

    public void greenOn() {
        greenLight.setPosition(0.5);
        purpleLight.setPosition(0);

        telemetry.addData("HeadLights", "Green");
    }

    public void purpleOn() {
        purpleLight.setPosition(0.5);
        greenLight.setPosition(0);

        telemetry.addData("HeadLights", "Purple");
    }

    public void stop() {
        greenLight.setPosition(0);
        purpleLight.setPosition(0);

        telemetry.addData("HeadLights", "Off");
    }
}

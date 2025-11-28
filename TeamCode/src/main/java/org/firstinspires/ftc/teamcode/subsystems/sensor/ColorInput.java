package org.firstinspires.ftc.teamcode.subsystems.sensor;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorInput {
    private final ColorSensor colorSensor;
    private final float[] hsv;
    private final Telemetry telemetry;

    public ColorInput(HardwareMap hardwareMap, Telemetry telemetry) {
        colorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        hsv = new float[3];
        this.telemetry = telemetry;
    }

    // Returns Hue Value from Color Sensor
    public float hue() {
        Color.RGBToHSV(
            colorSensor.red(),
            colorSensor.blue(),
            colorSensor.green(),
            hsv
        );

        telemetry.addData("Hue Value", hsv[0]);
        return hsv[0];
    }
}

package org.firstinspires.ftc.teamcode.subsystems.sensor;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

public class ColorInput {
    private final ColorSensor colorSensor;
    private final float[] hsv;
    private final Telemetry telemetry;

    public ColorInput(HardwareMap hardwareMap, Telemetry telemetry) {
        colorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        hsv = new float[3];
        this.telemetry = telemetry;
        telemetry.addData("Color Sensor", "Initialized");
    }

    // Returns Hue Value from Color Sensor
    public float hue() {
        Color.RGBToHSV(
            colorSensor.red(),
            colorSensor.blue(),
            colorSensor.green(),
            hsv
        );

        telemetry.addData("Color Sensor", "Hue Color of " + hsv[0]);
        return hsv[0];
    }

    public boolean isGreen() {
        return Constants.GREEN_MIN <= hue() && hue() <= Constants.GREEN_MAX;
    }

    public boolean isPurple() {
        return Constants.PURPLE_MIN <= hue() && hue() <= Constants.PURPLE_MAX;
    }
}

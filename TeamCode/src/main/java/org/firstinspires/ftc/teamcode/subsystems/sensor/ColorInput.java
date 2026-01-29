package org.firstinspires.ftc.teamcode.subsystems.sensor;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.HashMap;

public class ColorInput {
    private final ColorSensor[] colorSensors;
    private final float[] hsv;
    private final float[] hue;
    private final Telemetry telemetry;

    public ColorInput(HardwareMap hardwareMap, Telemetry telemetry) {
        ColorSensor pos1ColorSensor = hardwareMap.get(ColorSensor.class, "Pos1Color");
        ColorSensor pos2ColorSensor = hardwareMap.get(ColorSensor.class, "Pos2Color");
        ColorSensor pos3ColorSensor = hardwareMap.get(ColorSensor.class, "Pos3Color");
        colorSensors = new ColorSensor[]{pos1ColorSensor, pos2ColorSensor, pos3ColorSensor};

        hsv = new float[3];
        hue = new float[3];

        this.telemetry = telemetry;
        telemetry.addData("Color Sensor", "Initialized");
    }

    // Returns Hue Value from Color Sensor
    public float[] hue() {
        for (int i = 0; i < colorSensors.length; i++) {
            Color.RGBToHSV(
                    colorSensors[i].red(),
                    colorSensors[i].blue(),
                    colorSensors[i].green(),
                    hsv
            );

            hue[i] = hsv[0];
        }

        telemetry.addData("Color Sensor", hue[0] + ", " + hue[1] + ", " + hue[2]);
        return hue;
    }

    @Deprecated
    public boolean isGreen() {
        return Constants.GREEN_MIN <= hue()[0] && hue()[0] <= Constants.GREEN_MAX;
    }

    @Deprecated
    public boolean isPurple() {
        return Constants.PURPLE_MIN <= hue()[0] && hue()[0] <= Constants.PURPLE_MAX;
    }

    public boolean[] isGreenV2() {
        return new boolean[]{
                Constants.GREEN_MIN <= hue()[0] && hue()[0] <= Constants.GREEN_MAX,
                Constants.GREEN_MIN <= hue()[1] && hue()[1] <= Constants.GREEN_MAX,
                Constants.GREEN_MIN <= hue()[2] && hue()[2] <= Constants.GREEN_MAX
        };
    }

    public boolean[] isPurpleV2() {
        return new boolean[]{
                Constants.PURPLE_MIN <= hue()[0] && hue()[0] <= Constants.PURPLE_MAX,
                Constants.PURPLE_MIN <= hue()[1] && hue()[1] <= Constants.PURPLE_MAX,
                Constants.PURPLE_MIN <= hue()[2] && hue()[2] <= Constants.PURPLE_MAX
        };
    }
}

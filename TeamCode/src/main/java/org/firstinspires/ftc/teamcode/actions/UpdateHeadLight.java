package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorInput;
import org.firstinspires.ftc.teamcode.subsystems.transfer.HeadLight;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;

public class UpdateHeadLight implements Action {
    HeadLight headLight;
    ColorInput colorSensor;
    Lift lift;

    public UpdateHeadLight(HeadLight headLight, ColorInput colorSensor, Lift lift) {
        this.headLight = headLight;
        this.colorSensor = colorSensor;
        this.lift = lift;
    }

    @Override
    public boolean run() {
        // Flickering if Lift is Up
        if (lift.isUp()) {
            if (headLight.isGreen()) headLight.purpleOn();
            else headLight.greenOn();
        }

        // Color Detection
        else if (colorSensor.isGreen()) headLight.greenOn();
        else if (colorSensor.isPurple()) headLight.purpleOn();
        else headLight.stop();

        return true;
    }
}

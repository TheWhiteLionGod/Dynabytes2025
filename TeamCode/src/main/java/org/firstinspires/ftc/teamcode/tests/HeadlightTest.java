package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dynawheels;
import org.firstinspires.ftc.teamcode.subsystems.transfer.HeadLight;

@Disabled
//@TeleOp(name="Headlight TEST", group="TEST")
public class HeadlightTest extends Dynawheels {
    HeadLight headLight;

    @Override
    public void config() {
        headLight = new HeadLight(hardwareMap, telemetry);
        telemetry.update();
    }

    @Override
    public void run() {
        while (opModeIsActive()) {
            headLight.greenOn();
            sleep(1000);

            headLight.purpleOn();
            sleep(1000);

            headLight.stop();
            sleep(1000);
        }
    }

    @Override
    public void cleanup() {
        headLight.stop();
    }
}

package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class RunLauncherThread implements Runnable {
    DcMotor OM;
    Servo Lift;
    public volatile boolean canInterrupt = false;
    final double LIFT_IN_POS = 0.0;
    final double LIFT_OUT_POS = 0.0;
    public RunLauncherThread(DcMotor OM, Servo Lift) {
        this.OM = OM;
        this.Lift = Lift;
    }

    public void run() {
        try {
            // Starting Outtake
            OM.setPower(1);
            Thread.sleep(500);

            this.canInterrupt = true;
            Thread.sleep(500);

            Lift.setPosition(LIFT_OUT_POS);
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            telemetry.addLine("Interrupt Exception Thrown");
            telemetry.update();
        }
        finally {
            Lift.setPosition(LIFT_IN_POS);
            OM.setPower(0);
        }
    }
}

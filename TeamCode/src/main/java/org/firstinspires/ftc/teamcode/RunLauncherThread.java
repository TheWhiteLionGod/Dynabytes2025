package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class RunLauncherThread implements Runnable {
    DcMotor OM;
    Servo Lift;
    public volatile boolean canInterrupt = false;
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

            Lift.setPosition(Constants.LIFT_OUT_POS);
            Thread.sleep(1000);
        }
        catch (InterruptedException ignored) {

        }
        finally {
            Lift.setPosition(Constants.LIFT_IN_POS);
            OM.setPower(0);
        }
    }
}

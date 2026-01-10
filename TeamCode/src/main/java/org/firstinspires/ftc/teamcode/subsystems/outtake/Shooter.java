package org.firstinspires.ftc.teamcode.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    private final DcMotorEx shooter;
    private final Telemetry telemetry;
    double motorPwr = 0.9;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        shooter = hardwareMap.get(DcMotorEx.class, "Launcher");
        shooter.setDirection(DcMotorEx.Direction.REVERSE);

        this.telemetry = telemetry;
        telemetry.addData("Shooter", "Initialized");
    }

    public synchronized void forward() {
        shooter.setPower(motorPwr);
        telemetry.addData("Shooter", "Forward");
    }

    public synchronized void stop() {
        shooter.setPower(0);
        telemetry.addData("Shooter", "Stopped");
    }

    public void increasePwr() {
        motorPwr += 0.1;
    }

    public void decreasePwr() {
        motorPwr -= 0.1;
    }
}

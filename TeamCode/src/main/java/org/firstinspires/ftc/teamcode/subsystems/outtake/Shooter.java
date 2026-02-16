package org.firstinspires.ftc.teamcode.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public class Shooter implements Subsystem {
    private final DcMotorEx shooter;
    private final Telemetry telemetry;
    public double motorPwr = 0.9;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        shooter = hardwareMap.get(DcMotorEx.class, "Launcher");
        shooter.setDirection(DcMotorEx.Direction.REVERSE);
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, Constants.shooterPIDF);

        this.telemetry = telemetry;
        telemetry.addData("Shooter", "Initialized");
    }

    public void start() {
        shooter.setVelocity(Constants.shooterMaxVel*motorPwr);
        telemetry.addData("Shooter", "Forward");
    }

    public void stop() {
        shooter.setPower(0);
        telemetry.addData("Shooter", "Stopped");
    }

    public void increasePwr() { setPwr(motorPwr + 0.1); }
    public void decreasePwr() { setPwr(motorPwr - 0.1); }

    public void setPwr(double pwr) {
        motorPwr = Range.clip(pwr, 0, 1);
        telemetry.addData("Shooter", "Power set to " + motorPwr);

        if (shooter.getPower() != 0) start();
    }
}

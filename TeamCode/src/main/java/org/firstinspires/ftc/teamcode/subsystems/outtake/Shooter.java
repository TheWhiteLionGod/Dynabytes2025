package org.firstinspires.ftc.teamcode.subsystems.outtake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class Shooter {
    public enum States {
        IDLE, SPINNING, READY, FINISHED
    }
    public final Lift lift;
    private final DcMotorEx shooter;
    private final Telemetry telemetry;
    private Instant shootTime = Instant.now();
    private States state = States.IDLE;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        lift = new Lift(hardwareMap, telemetry);
        this.telemetry = telemetry;
    }

    public void start() {
        if (state == States.IDLE) {
            state = States.SPINNING;
            shootTime = Instant.now();
        }
    }

    public void stop() {
        state = States.IDLE;
        shooter.setPower(0);
        lift.down();
    }

    public boolean isIdle() {
        return state == States.IDLE;
    }

    public void updateState() {
        switch (state) {
            case IDLE:
                break;

            case SPINNING:
                // Checking If Its Been 1 Seconds Since Spin
                if (Duration.between(shootTime, Instant.now()).toMillis()
                        > Constants.SHOOTER_SPIN_TIME) {
                    state = States.READY;
                    shootTime = Instant.now();
                }
                // Telemetry if Shooter is Spinning
                else {
                    shooter.setPower(1);
                    telemetry.addData("Spinning Shooter", 1);
                }
                break;

            case READY:
                if (Duration.between(shootTime, Instant.now()).toMillis()
                    < Constants.LIFT_UP_TIME) {
                    lift.up();
                }
                else {
                    state = States.FINISHED;
                }
                break;

            case FINISHED:
                stop();
                break;
        }
    }
}

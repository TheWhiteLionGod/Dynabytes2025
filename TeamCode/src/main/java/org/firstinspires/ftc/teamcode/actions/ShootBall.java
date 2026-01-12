package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Lift;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class ShootBall implements Action {
    enum State {
        IDLE, SPINNING, READY, FINISHED
    }

    Shooter shooter;
    Lift lift;
    State state = State.IDLE;
    Instant timer = Instant.now();

    public ShootBall(Shooter shooter, Lift lift) {
        this.shooter = shooter;
        this.lift = lift;
    }

    @Override
    public boolean run() {
        switch (state) {
            case IDLE:
                state = State.SPINNING;
                break;

            case SPINNING:
                shooter.start();
                if (Duration.between(timer, Instant.now()).toMillis() > Constants.SHOOTER_SPIN_TIME) {
                    state = State.READY;
                    timer = Instant.now();
                }

            case READY:
                lift.up();
                if (Duration.between(timer, Instant.now()).toMillis() > Constants.LIFT_UP_TIME) {
                    state = State.FINISHED;
                }
                break;

            case FINISHED:
                shooter.stop();
                lift.down();
                return true;
        }

        return false;
    }
}

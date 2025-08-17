package org.firstinspires.ftc.teamcode;

// Base Robot Methods that All Robot Should Implement
public interface Robot {
    // Robot Configuration(Motors, Servos, Etc)
    public void configureRobot();

    // Drivetrain Movements
    public void moveDriveTrain(double pwrx, double pwry);
    public void turnDriveTrain(double pwr);
    public void resetDriveTrain();
}

// Teleop Specific Methods
public interface TeleOp extends Robot {
    // Field Drive Movements
    public void fieldMoveDriveTrain(double pwrx, double pwry);

    // Gear System
    public void changeGearMode(int val);
}

// Autonomous Specific Methods(MAY BE DEPRECATED DUE TO ROAD RUNNER)
public interface Autonomous extends Robot {
    // Drivetrain Movement Functions
    public default void moveForward(double pwr) {
        moveDriveTrain(Math.abs(pwr), 0);
    }

    public default void moveBackward(double pwr) {
        moveDriveTrain(-Math.abs(pwr), 0);
    }

    public default void moveLeft(double pwr) {
        moveDriveTrain(0, -Math.abs(pwr));
    }
    
    public default void moveRight(double pwr) {
        moveDriveTrain(0, Math.abs(pwr));
    }
}
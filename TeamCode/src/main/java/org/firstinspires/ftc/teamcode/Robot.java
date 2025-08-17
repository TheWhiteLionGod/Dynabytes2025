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
    public void moveForward(double pwr);
    public void moveBackward(double pwr);
    public void moveLeft(double pwr);
    public void moveRight(double pwr);
}
package com.github.tecuilacat.rfs.state;

/**
 * Represents the state of a robot, including its speed and movement status.
 */
public final class RobotState {

    private int speed;
    private boolean isMoving;

    /**
     * Constructs a new RobotState object with the given speed and movement status.
     *
     * @param speed     the speed of the robot
     * @param isMoving  the movement status of the robot
     */
    public RobotState(int speed, boolean isMoving) {
        this.speed = speed;
        this.isMoving = isMoving;
    }

    /**
     * Retrieves the speed of the robot.
     *
     * @return the speed of the robot
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the robot.
     *
     * @param speed the speed of the robot
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Checks if the robot is currently moving.
     *
     * @return true if the robot is moving, false otherwise
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Sets the movement status of the robot.
     *
     * @param moving the movement status of the robot
     */
    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}

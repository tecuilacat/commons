package com.github.tecuilacat.rfs.terminal;

import com.github.tecuilacat.rfs.control.RobotOperations;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides methods for executing terminal commands on a robot.
 */
public final class TerminalOperations {

    private TerminalOperations() { }

    /**
     * Enables servo operation on the robot.
     *
     * @param params the parameters for the command
     * @return the result of enabling servo
     */
    public static String enableServo(Params params) {
        return params.getOps().enableServo();
    }

    /**
     * Disables servo operation on the robot.
     *
     * @param params the parameters for the command
     * @return the result of disabling servo
     */
    public static String disableServo(Params params) {
        return params.getOps().disableServo();
    }

    /**
     * Sets the speed of the robot.
     *
     * @param params the parameters for the command
     * @return the result of setting the speed
     */
    public static String setSpeed(Params params) {
        return "override speed";
    }

    /**
     * Retrieves the current position of the robot.
     *
     * @param params the parameters for the command
     * @return the current position of the robot
     */
    public static String getCurrentPosition(Params params) {
        return params.getOps().getCurrentPosition().toString();
    }

    /**
     * Retrieves the state of the robot.
     *
     * @param params the parameters for the command
     * @return the state of the robot
     */
    public static String getState(Params params) {
        return params.getOps().getState();
    }

    /**
     * Moves the robot to the safe position.
     *
     * @param params the parameters for the command
     * @return the result of moving the robot to a safe position
     */
    public static String movToSafePosition(Params params) {
        return params.getOps().movToSafePosition();
    }

    /**
     * Executes a custom command on the robot.
     *
     * @param params the parameters for the command
     * @return the result of executing the custom command
     */
    public static String executeCustomCommand(Params params) {
        return params.getOps().executeCustomCommand(params.getCmd());
    }

    /**
     * Grabs an object using the robot's gripper.
     *
     * @param params the parameters for the command
     * @return an empty string
     */
    public static String grab(Params params) {
        RobotOperations ops = params.getOps();
        ops.grab();
        return "";
    }

    /**
     * Drops an object using the robot's gripper.
     *
     * @param params the parameters for the command
     * @return an empty string
     */
    public static String drop(Params params) {
        RobotOperations ops = params.getOps();
        ops.drop();
        return "";
    }

    /**
     * Moves the robot along a specified axis by a certain distance.
     *
     * @param params the parameters for the command
     * @return the result of moving the robot
     */
    public static String moveRobot(Params params) {
        String result;
        String parsedCoordinate = parseSingleCoordinate(params.getCmd());
        if (!parsedCoordinate.isBlank()) {
            result = switch (params.getTerminalCommand()) {
                case MOVX -> {
                    params.getOps().executeCustomCommand("EXECMOV P_CURR+(" + parsedCoordinate + ",0.0,0.0,0.0,0.0,0.0)");
                    yield "MOV " + parsedCoordinate + " on X-Axis";
                }
                case MOVY -> {
                    params.getOps().executeCustomCommand("EXECMOV P_CURR+(0.0," + parsedCoordinate + ",0.0,0.0,0.0,0.0)");
                    yield "MOV " + parsedCoordinate + " on Y-Axis";
                }
                case MOVZ -> {
                    params.getOps().executeCustomCommand("EXECMOV P_CURR+(0.0,0.0," + parsedCoordinate + ",0.0,0.0,0.0)");
                    yield "MOV " + parsedCoordinate + " on Z-Axis";
                }
                case MVSX -> {
                    params.getOps().executeCustomCommand("EXECMVS P_CURR+(" + parsedCoordinate + ",0.0,0.0,0.0,0.0,0.0)");
                    yield "MVS " + parsedCoordinate + " on X-Axis";
                }
                case MVSY -> {
                    params.getOps().executeCustomCommand("EXECMVS P_CURR+(0.0," + parsedCoordinate + ",0.0,0.0,0.0,0.0)");
                    yield "MVS " + parsedCoordinate + " on Y-Axis";
                }
                case MVSZ -> {
                    params.getOps().executeCustomCommand("EXECMVS P_CURR+(0.0,0.0," + parsedCoordinate + ",0.0,0.0,0.0)");
                    yield "MVS " + parsedCoordinate + " on Z-Axis";
                }
                default -> "Fatal error";
            };
        } else {
            result = "Syntax error in: " + params.getCmd() + " (" + params.getTerminalCommand().getPattern() + ")";
        }
        return result;
    }

    /**
     * Parses a single coordinate from a command string.
     *
     * @param cmd the command string
     * @return the parsed coordinate as a string
     */
    private static String parseSingleCoordinate(String cmd) {
        Pattern pattern = Pattern.compile("-?\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(cmd);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    /**
     * Displays help information for terminal commands.
     *
     * @param params the parameters for the command
     * @return an empty string
     */
    public static String help(Params params) {
        System.out.println("Available commands:");
        Arrays.stream(TerminalCommands.values()).forEach(command -> System.out.format("%15s|%30s|%20s", command.getCommand() + " ", command.getInfo() + " ", " " + command.getPattern() + "\n"));
        return "";
    }
}

package com.github.tecuilacat.rfs.terminal;

import com.github.tecuilacat.rfs.control.RobotOperations;

/**
 * Represents parameters for executing terminal commands on a robot.
 * <p>
 * This class encapsulates the necessary parameters for executing terminal commands on a robot,
 * including the type of terminal command, the robot operations instance, and the command string.
 * </p>
 */
public final class Params {

    private final RobotOperations ops;
    private final String cmd;
    private final TerminalCommands terminalCommand;

    /**
     * Constructs a new Params object with the specified parameters.
     *
     * @param terminalCommand the type of terminal command
     * @param ops the robot operations instance
     * @param cmd the command string
     */
    public Params(TerminalCommands terminalCommand, RobotOperations ops, String cmd) {
        this.ops = ops;
        this.cmd = cmd;
        this.terminalCommand = terminalCommand;
    }

    /**
     * Retrieves the robot operations instance.
     *
     * @return the robot operations instance
     */
    public RobotOperations getOps() {
        return ops;
    }

    /**
     * Retrieves the command string.
     *
     * @return the command string
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * Retrieves the type of terminal command.
     *
     * @return the type of terminal command
     */
    public TerminalCommands getTerminalCommand() {
        return terminalCommand;
    }

}
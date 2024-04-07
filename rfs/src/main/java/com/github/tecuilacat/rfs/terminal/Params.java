package com.github.tecuilacat.rfs.terminal;

import com.github.tecuilacat.rfs.control.RobotOperations;

public final class Params {

    private final RobotOperations ops;
    private final String cmd;
    private final TerminalCommands terminalCommand;

    public Params(TerminalCommands terminalCommand, RobotOperations ops, String cmd) {
        this.ops = ops;
        this.cmd = cmd;
        this.terminalCommand = terminalCommand;
    }

    public RobotOperations getOps() {
        return ops;
    }

    public String getCmd() {
        return cmd;
    }

    public TerminalCommands getTerminalCommand() {
        return terminalCommand;
    }

}

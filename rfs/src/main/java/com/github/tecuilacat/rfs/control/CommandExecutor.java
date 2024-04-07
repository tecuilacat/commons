package com.github.tecuilacat.rfs.control;

import com.github.tecuilacat.rfs.utils.DelayManager;
import com.github.tecuilacat.rfs.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Sends and executes commands
 */
public final class CommandExecutor {

    private static final Logger logger = new Logger(CommandExecutor.class);

    private final OutputStream writer;
    private final InputStream reader;

    private CommandExecutor(InputStream reader, OutputStream writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static CommandExecutor getFromSocket(Socket socket) throws IOException {
        return new CommandExecutor(socket.getInputStream(), socket.getOutputStream());
    }

    /**
     * Used for all commands except for those that move the robot
     *
     * @param command Command the robot has to execute
     * @return Answer of the robot
     */
    public String execute(String command) {
        logger.command("Executing command: " + command);
        String res = "";
        try {
            command = "1;1;" + command; // every command needs a leading 1;1; statement
            writer.write(command.getBytes(StandardCharsets.US_ASCII));
            DelayManager.defaultTimeout();
            byte[] answer = new byte[1024];
            reader.read(answer);
            res = new String(answer);
        } catch (Exception e) {
            logger.error("Command " + command + " could not be executed", e);
        }
        return res;
    }

}

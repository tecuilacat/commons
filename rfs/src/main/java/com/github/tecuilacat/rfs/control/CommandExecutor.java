package com.github.tecuilacat.rfs.control;

import com.github.tecuilacat.rfs.utils.DelayManager;
import com.github.tecuilacat.rfs.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Executes commands on a robot through a socket connection.
 * <p>
 * This class provides methods to send commands to a robot and receive its responses
 * over a socket connection. It handles commands that do not involve moving the robot.
 * </p>
 */
public final class CommandExecutor {

    private static final Logger logger = new Logger(CommandExecutor.class);

    private final OutputStream writer;
    private final InputStream reader;

    private CommandExecutor(InputStream reader, OutputStream writer) {
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * Creates a new CommandExecutor instance using the provided socket's input and output streams.
     *
     * @param socket the socket connected to the robot
     * @return a CommandExecutor instance
     * @throws IOException if an I/O error occurs while accessing the socket streams
     */
    public static CommandExecutor getFromSocket(Socket socket) throws IOException {
        return new CommandExecutor(socket.getInputStream(), socket.getOutputStream());
    }

    /**
     * Executes a command on the robot and retrieves its response.
     *
     * @param command the command to be executed on the robot
     * @return the response from the robot
     * @since v1.0
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

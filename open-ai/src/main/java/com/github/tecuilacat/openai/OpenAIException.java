package com.github.tecuilacat.openai;

/**
 * This exception can be used to put into the responses for better tracking of errors
 * @author tecuilacat
 * @since 1.0.2
 */
public class OpenAIException extends Exception {

    public OpenAIException(String message) {
        super(message);
    }

    public OpenAIException(String message, Throwable cause) {
        super(message, cause);
    }

}

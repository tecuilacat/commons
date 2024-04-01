package com.github.tecuilacat.openai.responses;

/**
 * This response is used when there are errors with the rest call
 * @author tecuilacat
 * @since 1.0.2
 */
public final class OpenAIErrorResponse extends OpenAIResponse {

    private final int errorCode;

    private final String message;

    private Exception exception;

    public OpenAIErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public OpenAIErrorResponse(int errorCode, String message, Exception exception) {
        this.errorCode = errorCode;
        this.message = message;
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

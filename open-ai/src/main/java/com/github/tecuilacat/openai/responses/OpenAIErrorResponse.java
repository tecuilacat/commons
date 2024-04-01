package com.github.tecuilacat.openai.responses;

public final class OpenAIErrorResponse extends OpenAIResponse {

    private int errorCode;

    private String message;

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

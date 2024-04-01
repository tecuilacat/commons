package com.github.tecuilacat.openai;

import java.util.Arrays;
import java.util.Optional;

public enum OpenAIErrorCode {

    ERROR_401(401, "Invalid Authentication"),
    ERROR_403(403, "You are accessing the API from an unsupported country, region, or territory."),
    ERROR_429(429, "You have run out of credits or hit your maximum monthly spend."),
    ERROR_500(500, "Issue on Open AI servers.")
    ;

    private int errorCode;

    private String message;

    OpenAIErrorCode(int errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public static Optional<OpenAIErrorCode> getByCode(int errorCode) {
        return Arrays.stream(OpenAIErrorCode.values())
                .filter(e -> e.getErrorCode() == errorCode)
                .findFirst();
    }

}

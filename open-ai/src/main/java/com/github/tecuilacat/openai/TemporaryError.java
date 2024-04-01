package com.github.tecuilacat.openai;

/**
 * This class is just for parsing purposes
 * @author tecuilacat
 * @since 1.0.2
 */
public class TemporaryError {

    private String message;

    private String type;

    private String param;

    private String code;

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getParam() {
        return param;
    }

    public String getType() {
        return type;
    }
}

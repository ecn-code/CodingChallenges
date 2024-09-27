package com.eliascanalesnieto.challenges.webserver.model;

public enum HttpStatus {
    OK("OK", 200),
    NOT_FOUND("Not Found", 400),
    SERVER_ERROR("Server error", 500);

    private final int code;
    private final String message;

    HttpStatus(String msg, int code) {
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

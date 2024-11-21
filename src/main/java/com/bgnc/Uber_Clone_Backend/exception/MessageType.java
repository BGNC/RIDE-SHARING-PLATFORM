package com.bgnc.Uber_Clone_Backend.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXISTS("1001", "The record is not found"),
    USERNAME_NOT_FOUND_EXCEPTION("1002", "Username not found"),
    USERNAME_ALREADY_EXISTS("1003", "Username already exists"),
    REFRESH_TOKEN_IS_NOT_FOUND("1004", "Refresh token not found"),
    REFRESH_TOKEN_IS_ALREADY_EXPIRED("1005", "Refresh token is already expired"),
    USERNAME_OR_PASSWORD_IS_INVALID("1006", "Username or password is invalid"),
    TOKEN_IS_EXPIRED("1007", "The token is expired"),
    CURRENCY_RATE_IS_OCCURED("1008", "The currency rate is occurred"),
    INVALID_CREDENTIALS("1111", "Invalid credentials"),
    CLIENT_ERROR("1009", "A client error occurred (4xx)"),
    INVALID_TOKEN("1012","Invalid token"),
    SERVER_ERROR("1010", "A server error occurred (5xx)"),
    INVALID_ROLE("1011", "Invalid role"),
    GENERAL_EXCEPTION("9999", "The general error.");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

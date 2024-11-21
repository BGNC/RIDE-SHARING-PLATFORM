package com.bgnc.Uber_Clone_Backend.exception;

public class BaseException extends RuntimeException {

    BaseException(){

    }
    public BaseException(ErrorMessage message) {
        super(message.prepareMessage());

    }
}

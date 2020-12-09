package com.company.exception;

public class CustomerException extends Exception {

    public CustomerException(ErrorMessage msg) {
        super(msg.getValue());
    }
}

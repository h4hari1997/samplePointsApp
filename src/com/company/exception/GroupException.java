package com.company.exception;

public class GroupException extends Exception {

    public GroupException(ErrorMessage msg) {
        super(msg.getValue());
    }
}

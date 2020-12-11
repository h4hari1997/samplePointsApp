package com.company.exception;

public class SchemeException extends Exception {

    public SchemeException(ErrorMessage msg) {
        super(msg.getValue());
    }
}

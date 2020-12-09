package com.company.exception;

public class BalanceNotSufficientException extends Exception {

    public BalanceNotSufficientException(ErrorMessage msg) {
        super(msg.getValue());
    }
}

package com.company.exception;

public class BalanceNotSufficientException extends Exception {

    public BalanceNotSufficientException(String msg) {
        super(msg);
    }
}

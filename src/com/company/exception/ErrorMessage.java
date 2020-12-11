package com.company.exception;

public enum ErrorMessage {

    GROUP_NOT_EXIST("Group does not exist"),
    CUSTOMER_NOT_EXIST("Customer does not exist"),
    GROUP_ALREADY_EXIST("Group already exist"),
    CUSTOMER_ALREADY_EXIST("Customer already exist"),
    CUSTOMER_BELONGS_TO_GROUP("Customer already belongs to a group"),
    CUSTOMER_DOESNOT_BELONG_TO_GROUP("Customer does not belong to a group"),
    INSUFFICIENT_BALANCE("Insufficient Balance"),
    GROUP_EMPTY("Group Empty"),
    GROUP_LIMIT_EXCEEDED("Group limit exceeded"),
    SCHEME_ALREADY_EXIST("Scheme already exist"),
    SCHEME_DOES_NOT_EXIST("Scheme does not exist"),
    CUSTOMER_OPTED_SCHEME("Customer already opted a scheme"),
    CUSTOMER_NOT_OPTED_SCHEME("Customer not opted a scheme");

    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

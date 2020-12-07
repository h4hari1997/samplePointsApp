package com.company.dto;

import java.util.HashMap;
import java.util.Objects;

public class Customer {

    private String customerId;
    private String name;
    private String groupId;

    public static HashMap<String,Customer> customerIdMap = new HashMap<>();

    public Customer() {
    }

    public Customer(String customerId, String name, int currentBalance, String groupId) {
        this.customerId = customerId;
        this.name = name;
        this.groupId = groupId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId){
        this.groupId = groupId;
    }


}
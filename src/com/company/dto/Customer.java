package com.company.dto;

public class Customer {

    private String customerId;
    private String name;
    private String groupId;
    private Scheme scheme;

    public Customer() {
    }

    public Customer(String customerId, String name, String groupId, Scheme scheme) {
        this.customerId = customerId;
        this.name = name;
        this.groupId = groupId;
        this.scheme = scheme;
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

    public Scheme getScheme() { return this.scheme; }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }
}
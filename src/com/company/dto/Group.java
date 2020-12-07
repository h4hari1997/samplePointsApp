package com.company.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Group {
    private String groupId;
    private ArrayList<Customer> customers;
    private int groupSize;

    public static HashMap<String, Group> groupIdMap = new HashMap<>();

    public Group() {
    }

    public Group(String groupId, ArrayList<Customer> customers, int groupSize) {
        this.groupId = groupId;
        this.customers = customers;
        this.groupSize = groupSize;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getGroupSize() { return this.groupSize; }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public ArrayList<Customer> getCustomers() { return this.customers; }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers= customers;
    }



}

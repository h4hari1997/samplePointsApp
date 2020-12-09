package com.company.dto;

import com.company.exception.ErrorMessage;
import com.company.exception.GroupException;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupId;
    private List<Customer> customers;
    private int size;

    public Group() {
        this.groupId = null;
        this.customers = new ArrayList<>();
        this.size = 0;
    }

    public Group(String groupId, List<Customer> customers, int groupSize) {
        this.groupId = groupId;
        this.customers = customers;
        this.size = groupSize;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getSize() { return this.size; }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Customer> getCustomers() { return this.customers; }

    public void setCustomers(List<Customer> customers) {
        this.customers= customers;
    }

    public void addCustomer(Customer customer) throws GroupException {
        if(this.getCustomers().size()>=this.getSize()) {
            throw new GroupException(ErrorMessage.GROUP_LIMIT_EXCEEDED);
        } else {
            List<Customer> customerList = this.getCustomers();
            customerList.add(customer);
            this.setCustomers(customerList);
        }
    }

    public void removeCustomer(Customer customer) throws GroupException {
        if(this.getCustomers().size()==0) {
            throw new GroupException(ErrorMessage.GROUP_EMPTY);
        } else {
            List<Customer> customerList = this.getCustomers();
            customerList.remove(customer);
            this.setCustomers(customerList);
        }
    }
}

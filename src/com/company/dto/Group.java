package com.company.dto;

import com.company.exception.CustomerException;
import com.company.exception.ErrorMessage;
import com.company.exception.GroupException;

import java.util.ArrayList;
import java.util.Arrays;
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

    //array as input
    public void joinGroup(Customer customer) throws GroupException, CustomerException {

        if (customer.getGroupId() != null) {
            throw new CustomerException(ErrorMessage.CUSTOMER_BELONGS_TO_GROUP);
        }

        if(this.getCustomers().size()>=this.getSize()) {
            throw new GroupException(ErrorMessage.GROUP_LIMIT_EXCEEDED);
        } else {
            customer.setGroupId(this.getGroupId());
            List<Customer> customerList = this.getCustomers();
            customerList.add(customer);
            this.setCustomers(customerList);
        }
    }

    public void joinGroup(Customer[] customer) throws GroupException, CustomerException {

        if(this.getCustomers().size() + customer.length>=this.getSize()) {
            throw new GroupException(ErrorMessage.GROUP_LIMIT_EXCEEDED);
        } else {

            for (int i = 0; i < customer.length; i++) {
                if (customer[i].getGroupId() != null) {
                    throw new CustomerException(ErrorMessage.CUSTOMER_BELONGS_TO_GROUP);
                }
            }

            for (int i = 0; i < customer.length; i++) {
                this.joinGroup(customer[i]);
            }
        }
    }

    public void leaveGroup(Customer customer) throws GroupException, CustomerException {
        if (customer.getGroupId() == null) {
            throw new CustomerException(ErrorMessage.CUSTOMER_DOESNOT_BELONG_TO_GROUP);
        }

        if(this.getCustomers().size()==0) {
            throw new GroupException(ErrorMessage.GROUP_EMPTY);
        } else {
            customer.setGroupId(null);
            List<Customer> customerList = this.getCustomers();
            customerList.remove(customer);
            this.setCustomers(customerList);
        }
    }

    public void leaveGroup(Customer[] customer) throws GroupException, CustomerException {
        if(this.getCustomers().size() - customer.length < 0) {
            throw new GroupException(ErrorMessage.GROUP_EMPTY);
        } else {
            for (int i = 0; i < customer.length; i++) {
                if (customer[i].getGroupId() == null) {
                    throw new CustomerException(ErrorMessage.CUSTOMER_DOESNOT_BELONG_TO_GROUP);
                }
            }
            for(int i=0; i<customer.length; i++) {
                this.leaveGroup(customer[i]);
            }
        }
    }
}

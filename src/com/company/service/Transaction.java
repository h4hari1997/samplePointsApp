package com.company.service;

import com.company.dto.Customer;
import com.company.dto.Group;
import com.company.exception.BalanceNotSufficientException;
import com.company.exception.CustomerException;
import com.company.exception.GroupException;

import java.util.HashMap;

public abstract class Transaction {

    public static HashMap<Customer,Integer> customerPointsMap = new HashMap<>();

    public static HashMap<Group,Integer> groupPointsMap = new HashMap<>();

    public abstract void execute(Customer cust, int points) throws CustomerException, BalanceNotSufficientException, GroupException;

    public void updateBalance(Customer cust, int points) {
        customerPointsMap.put(cust,points);
    }

    public void updateBalance(Group group, int points) {
        groupPointsMap.put(group,points);
    }

}
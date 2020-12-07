package com.company.service;

import com.company.dto.Customer;
import com.company.dto.Group;
import com.company.exception.BalanceNotSufficientException;
import com.company.exception.CustomerException;

import java.util.ArrayList;

import static com.company.dto.Group.groupIdMap;

public class DebitTransaction extends Transaction {

    @Override
    public void execute(Customer cust, int points) throws CustomerException, BalanceNotSufficientException {
        if(cust == null) {
            throw new CustomerException("Customer does not exist");
        }

        String groupId = cust.getGroupId();
        Group group = groupIdMap.get(groupId);

        if(group == null) {
            if (points <= customerPointsMap.get(cust)) {
                updateBalance(cust, customerPointsMap.get(cust) - points);
            } else {
                throw new BalanceNotSufficientException("Balance Not Sufficient");
            }
        } else {
            if(points <= customerPointsMap.get(cust)) {
                updateBalance(cust,customerPointsMap.get(cust) - points);
                updateBalance(group, groupPointsMap.get(group)- points);
            }
            else {
                if(points > customerPointsMap.get(cust) && points > groupPointsMap.get(group)) {
                    throw new BalanceNotSufficientException("Balance Not Sufficient");
                } else {
                    ArrayList<Customer> customerList = group.getCustomers();
                    int customerListSize = customerList.size();
                    int groupBalance = groupPointsMap.get(group);
                    updateBalance(group, groupPointsMap.get(group) - points);
                    for (int i = 0; i < customerListSize; i++) {
                        Customer customer = customerList.get(i);
                        int customerPoints = customerPointsMap.get(customer);
                        updateBalance(customer, customerPoints - ((customerPoints * points) / groupBalance));
                    }
                }
            }
        }
    }

}
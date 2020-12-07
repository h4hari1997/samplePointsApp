package com.company.service;

import com.company.dto.Customer;
import com.company.dto.Group;
import com.company.exception.CustomerException;

import static com.company.dto.Group.groupIdMap;

public class CreditTransaction extends Transaction {

    @Override
    public void execute(Customer cust, int points) throws CustomerException
    {
        if(cust == null){
            throw new CustomerException("Customer does not exist");
        }
        updateBalance(cust,points + customerPointsMap.get(cust));

        String groupId = cust.getGroupId();
        Group group = groupIdMap.get(groupId);

        if(groupId != null) {
            updateBalance(group, points + groupPointsMap.get(group));
        }
    }
}


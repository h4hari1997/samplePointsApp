package com.company.service;

import com.company.dto.Customer;
import com.company.dto.Group;
import com.company.exception.CustomerException;
import com.company.exception.ErrorMessage;

import static com.company.Main.groupIdMap;

public class CreditTransaction extends Transaction {

    @Override
    public void execute(Customer cust, int points) throws CustomerException
    {
        if(cust == null){
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_EXIST);
        }
        updateBalance(cust,points + customerPointsMap.get(cust));

        String groupId = cust.getGroupId();
        Group group = groupIdMap.get(groupId);

        if(groupId != null) {
            updateBalance(group, points + groupPointsMap.get(group));
        }
    }
}


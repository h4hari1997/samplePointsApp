package com.company.dto;

import com.company.exception.ErrorMessage;
import com.company.exception.SchemeException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UKSavers extends UKClubCard {

    private static Set<Customer> UkSaversCustomers;

    public UKSavers() {
        UkSaversCustomers = new HashSet<>();
    }

    @Override
    public boolean isPresent(Customer customer) {
        return UkSaversCustomers.contains(customer);
    }

    @Override
    public void joinScheme(Customer customer) throws SchemeException {
        if(!isPresent(customer)) {
            UkSaversCustomers.add(customer);
        } else {
            throw new SchemeException(ErrorMessage.CUSTOMER_OPTED_SCHEME);
        }
    }

    @Override
    public void joinScheme(Customer[] customer) {
        UkSaversCustomers.addAll(Arrays.asList(customer));
    }

    @Override
    public void leaveScheme(Customer customer) {
        UkSaversCustomers.remove(customer);
    }

    @Override
    public void leaveScheme(Customer[] customer) {
        UkSaversCustomers.removeAll(Arrays.asList(customer));
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}

package com.company.dto;

import com.company.exception.SchemeException;

import java.util.*;

public class UKClubCard extends Scheme {

    private static Set<Customer> UkClubCardCustomers;

    public UKClubCard()
    {
        UkClubCardCustomers = new HashSet<>();
    }

    @Override
    public boolean isPresent(Customer customer) {
        return UkClubCardCustomers.contains(customer);
    }

    @Override
    public void joinScheme(Customer customer) throws SchemeException {
        UkClubCardCustomers.add(customer);
    }

    @Override
    public void joinScheme(Customer[] customer) {
        UkClubCardCustomers.addAll(Arrays.asList(customer));
    }

    @Override
    public void leaveScheme(Customer customer) {
        UkClubCardCustomers.remove(customer);
    }

    @Override
    public void leaveScheme(Customer[] customer) {
        UkClubCardCustomers.removeAll(Arrays.asList(customer));
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


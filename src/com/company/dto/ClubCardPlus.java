package com.company.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClubCardPlus extends UKClubCard {

    private static Set<Customer> ClubCardPlusCustomers;

    public ClubCardPlus() {
        ClubCardPlusCustomers = new HashSet<>();
    }

    @Override
    public boolean isPresent(Customer customer) {
        return ClubCardPlusCustomers.contains(customer);
    }

    @Override
    public void joinScheme(Customer customer) {
        ClubCardPlusCustomers.add(customer);
    }

    @Override
    public void joinScheme(Customer[] customer) {
        ClubCardPlusCustomers.addAll(Arrays.asList(customer));
    }

    @Override
    public void leaveScheme(Customer customer) {
        ClubCardPlusCustomers.remove(customer);
    }

    @Override
    public void leaveScheme(Customer[] customer) {
        ClubCardPlusCustomers.removeAll(Arrays.asList(customer));
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
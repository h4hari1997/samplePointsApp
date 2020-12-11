package com.company.dto;

import java.util.*;

public class ColleagueDiscount extends Scheme {

    private static Set<Customer> colleagueDiscountCustomers;

    public ColleagueDiscount()
    {
        colleagueDiscountCustomers = new HashSet<>();
    }

    @Override
    public boolean isPresent(Customer customer) {
        return colleagueDiscountCustomers.contains(customer);
    }

    @Override
    public void joinScheme(Customer customer) {
        colleagueDiscountCustomers.add(customer);
    }

    @Override
    public void joinScheme(Customer[] customer) {
        colleagueDiscountCustomers.addAll(Arrays.asList(customer));
    }

    @Override
    public void leaveScheme(Customer customer) {
        colleagueDiscountCustomers.remove(customer);
    }

    @Override
    public void leaveScheme(Customer[] customer) {
        colleagueDiscountCustomers.removeAll(Arrays.asList(customer));
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

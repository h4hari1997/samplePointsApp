package com.company.dto;

import com.company.exception.SchemeException;

import java.util.HashSet;

public abstract class Scheme {

    public String schemeId;
    public String schemeName;

    public static HashSet<String> schemeTypes = new HashSet<>();

    public abstract boolean isPresent(Customer customer);

    public abstract void joinScheme(Customer customer) throws SchemeException;

    public abstract void joinScheme(Customer[] customer) throws SchemeException;

    public abstract void leaveScheme(Customer customer) throws SchemeException;

    public abstract void leaveScheme(Customer[] customer) throws SchemeException;
}

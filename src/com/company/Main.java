package com.company;

import com.company.dto.*;
import com.company.exception.BalanceNotSufficientException;
import com.company.exception.CustomerException;
import com.company.exception.GroupException;
import com.company.service.CreditTransaction;
import com.company.service.DebitTransaction;
import com.company.service.Transaction;

import java.util.ArrayList;
import java.util.Scanner;

import static com.company.dto.Customer.customerIdMap;
import static com.company.dto.Group.*;
import static com.company.service.Transaction.customerPointsMap;
import static com.company.service.Transaction.groupPointsMap;

public class Main {

    public static void main(String[] args) {
        String option;
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Add a new Group");
        System.out.println("2.Add a new Customer");
        System.out.println("3.Add Customer to a Group");
        System.out.println("4.Remove Customer from a Group");
        System.out.println("5.Credit Points");
        System.out.println("6.Debit Points");
        System.out.println("7.Show Customer Balance");
        System.out.println("8.Show Group Balance");
        System.out.println("9.Exit");

        while(true) {
            System.out.println("Enter your option : ");
            option = sc.nextLine();
            switch(option)
            {
                case "1": {
                    Group group = null;
                    try {
                        group = createNewGroup();
                    } catch (GroupException e) {
                        System.out.println(e.getMessage());

                        break;
                    }
                    groupIdMap.put(group.getGroupId(), group);
                    groupPointsMap.put(group, 0);
                    break;
                }
                case "2": {
                    Customer customer = null;
                    try {
                        customer = createNewCustomer();
                    } catch (CustomerException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    customerIdMap.put(customer.getCustomerId(), customer);
                    customerPointsMap.put(customer, 0);
                    break;
                }
                case "3": {
                    System.out.println("Enter your Customer ID : ");
                    String customerId = sc.nextLine();
                    System.out.println("Enter your Group id: ");
                    String groupId = sc.nextLine();
                    Customer customer = customerIdMap.get(customerId);
                    int balance = customerPointsMap.get(customer);
                    if(customer.getGroupId()!=null) {
                        System.out.println("Customer already belongs to a group");
                        break;
                    }
                    try {
                        updateCustomerList(groupId,customerId,1);
                    } catch (GroupException | CustomerException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.println("Enter your Customer ID : ");
                    String customerId = sc.nextLine();
                    System.out.println("Enter your Group id: ");
                    String groupId = sc.nextLine();
                    Customer customer = customerIdMap.get(customerId);
                    if(customer!=null) {
                        customer.setGroupId(null);
                    }
                    try {
                        updateCustomerList(groupId,customerId,0);
                    } catch (GroupException | CustomerException e) {
                        System.out.println(e.getMessage());                    }
                    break;
                }
                case "5": {
                    System.out.println("Enter your Customer ID : ");
                    String customerId = sc.nextLine();
                    System.out.println("Enter points to be credited : ");
                    int creditPoints = Integer.parseInt(sc.nextLine());
                    Customer customer = customerIdMap.get(customerId);
                    Transaction credit = new CreditTransaction();
                    try {
                        credit.execute(customer, creditPoints);
                    } catch (CustomerException | BalanceNotSufficientException | GroupException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "6": {
                    System.out.println("Enter your Customer ID : ");
                    String customerId = sc.nextLine();
                    System.out.println("Enter points to be debited : ");
                    int debitPoints = Integer.parseInt(sc.nextLine());
                    Customer customer = customerIdMap.get(customerId);
                    Transaction debit = new DebitTransaction();
                    try {
                        debit.execute(customer, debitPoints);
                    } catch (CustomerException | BalanceNotSufficientException | GroupException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "7": {
                    System.out.println("Enter your Customer ID : ");
                    String customerId = sc.nextLine();
                    Customer customer = customerIdMap.get(customerId);
                    if (customer == null) {
                        System.out.println("Customer does not exist");
                        break;
                    }
                    System.out.print("Balance = ");
                    System.out.println(customerPointsMap.get(customer));
                    break;
                }
                case "8": {
                    System.out.println("Enter your Group ID : ");
                    String groupId = sc.nextLine();
                    Group group = groupIdMap.get(groupId);
                    if (group == null) {
                        System.out.println("Group does not exist");
                        break;
                    }
                    System.out.print("Balance = ");
                    System.out.println(groupPointsMap.get(group));
                    break;
                }
                case "9": {
                    System.out.println("Exiting");
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Enter a valid option ");
                    break;
                }
            }
        }
    }

    public static Customer createNewCustomer() throws CustomerException {
        Customer cust = new Customer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        cust.setName(sc.nextLine());
        System.out.println("Enter your customer id: ");
        cust.setCustomerId(sc.nextLine());
        cust.setGroupId(null);

        if(customerIdMap.get(cust.getCustomerId())!=null) {
            throw new CustomerException("Customer already exists");
        }
        else {
            return cust;
        }
    }

    public static Group createNewGroup() throws GroupException {
        Group group = new Group();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Group id: ");
        group.setGroupId(sc.nextLine());
        System.out.println("Enter your Group size: ");
        group.setGroupSize(Integer.parseInt(sc.nextLine()));
        group.setCustomers(null);
        if(groupIdMap.get(group.getGroupId())!=null) {
            throw new GroupException("Group already exists");
        }
        else {
            return group;
        }
    }


    public static void updateCustomerList(String groupId, String customerId, int flag) throws GroupException, CustomerException {

        if(customerIdMap.get(customerId)==null) {
            throw new CustomerException("Customer does not exist");
        }
        if(groupIdMap.get(groupId)==null) {
            throw new GroupException("Group does not exist");
        }

        if (flag == 1) {
            addCustomerList(groupId, customerId);
        } else {
            removeCustomerList(groupId, customerId);
        }
    }

    public static void addCustomerList(String groupId, String customerId) throws GroupException {
        Group group = groupIdMap.get(groupId);
        int customerListSize = 0;

        ArrayList<Customer> customerList = group.getCustomers();
        if(customerList != null) {
            customerListSize = customerList.size();
        } else {
            customerList = new ArrayList<>();
        }
        if (customerListSize >= group.getGroupSize()) {
            throw new GroupException("Group Size limit reached");
        } else {
            Customer cust = customerIdMap.get(customerId);
            int groupBalance = groupPointsMap.get(group) + customerPointsMap.get(cust);
            customerList.add(cust);
            group.setCustomers(customerList);
            groupPointsMap.put(group, groupBalance);
            cust.setGroupId(groupId);
        }
    }

    public static void removeCustomerList(String groupId, String customerId) throws GroupException {
        Group group = groupIdMap.get(groupId);
        ArrayList<Customer> customerList = group.getCustomers();

        if (customerList == null) {
            throw new GroupException("Group Empty");
        } else {
            int customerListSize = customerList.size();
            Customer cust = customerIdMap.get(customerId);
            int groupBalance = groupPointsMap.get(group) - customerPointsMap.get(cust);
            customerList.remove(cust);
            group.setCustomers(customerList);
            groupPointsMap.put(group, groupBalance);
            cust.setGroupId(null);
        }
    }
}

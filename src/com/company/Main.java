package com.company;

import com.company.dto.*;
import com.company.exception.BalanceNotSufficientException;
import com.company.exception.CustomerException;
import com.company.exception.ErrorMessage;
import com.company.exception.GroupException;
import com.company.service.CreditTransaction;
import com.company.service.DebitTransaction;
import com.company.service.Transaction;

import java.util.HashMap;
import java.util.Scanner;

import static com.company.service.Transaction.customerPointsMap;
import static com.company.service.Transaction.groupPointsMap;

public class Main {

    enum customerGroup {
        ADD,REMOVE
    }

    public static HashMap<String,Customer> customerIdMap = new HashMap<>();
    public static HashMap<String, Group> groupIdMap = new HashMap<>();

    public static void main(String[] args) {

        printMenu();
        String option;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your option : ");
            option = sc.nextLine();

            switch (option) {

                case "1": {
                    try {
                        createNewGroup();
                    } catch (GroupException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                    break;
                }

                case "2": {
                    try {
                        createNewCustomer();
                    } catch (CustomerException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                    break;
                }

                case "3": {
                    try {
                        updateCustomerList(customerGroup.ADD);
                    } catch (GroupException | CustomerException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                    break;
                }

                case "4": {
                    try {
                        updateCustomerList(customerGroup.REMOVE);
                    } catch (GroupException | CustomerException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                    break;
                }

                case "5": {
                    creditPoints();
                    break;
                }
                case "6": {
                    debitPoints();
                    break;
                }
                case "7": {
                    try {
                        showCustomerBalance();
                    } catch (CustomerException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                    break;
                }
                case "8": {
                    try {
                        showGroupBalance();
                    } catch (GroupException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        break;
                    }
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

    private static void printMenu() {
        System.out.println("1.Create a new Group");
        System.out.println("2.Create a new Customer");
        System.out.println("3.Add Customer to a Group");
        System.out.println("4.Remove Customer from a Group");
        System.out.println("5.Credit Points");
        System.out.println("6.Debit Points");
        System.out.println("7.Show Customer Balance");
        System.out.println("8.Show Group Balance");
        System.out.println("9.Exit");
    }

    public static void createNewGroup() throws GroupException {
        Group group = new Group();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Group id: ");
        group.setGroupId(sc.nextLine());
        System.out.println("Enter your Group size: ");
        group.setSize(Integer.parseInt(sc.nextLine()));
        if (groupIdMap.get(group.getGroupId()) != null) {
            throw new GroupException(ErrorMessage.GROUP_ALREADY_EXIST);
        } else {
            groupIdMap.put(group.getGroupId(), group);
            groupPointsMap.put(group, 0);
        }
    }

    public static void createNewCustomer() throws CustomerException {
        Customer customer = new Customer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        customer.setName(sc.nextLine());
        System.out.println("Enter your customer id: ");
        customer.setCustomerId(sc.nextLine());
        customer.setGroupId(null);

        if (customerIdMap.get(customer.getCustomerId()) != null) {
            throw new CustomerException(ErrorMessage.CUSTOMER_ALREADY_EXIST);
        } else {
            customerIdMap.put(customer.getCustomerId(), customer);
            customerPointsMap.put(customer, 0);
        }
    }


    public static void updateCustomerList(customerGroup flag) throws GroupException, CustomerException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Customer ID : ");
        String customerId = sc.nextLine();
        System.out.println("Enter your Group id: ");
        String groupId = sc.nextLine();

        Customer customer = customerIdMap.get(customerId);
        Group group = groupIdMap.get(groupId);

        checkValidity(group, customer);

        if (flag == customerGroup.ADD) {
            addCustomerToGroup(group, customer);
        } else {
            removeCustomerFromGroup(group, customer);
        }
    }


    public static void checkValidity(Group group, Customer customer) throws CustomerException, GroupException {

        if (customer == null) {
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_EXIST);
        }
        if (group == null) {
            throw new GroupException(ErrorMessage.GROUP_NOT_EXIST);
        }
    }

    public static void addCustomerToGroup(Group group, Customer customer) throws CustomerException, GroupException {

        if (customer.getGroupId() != null) {
            System.out.println(ErrorMessage.CUSTOMER_BELONGS_TO_GROUP);
            return;
        }

        group.addCustomer(customer);

        int groupBalance = groupPointsMap.get(group) + customerPointsMap.get(customer);
        groupPointsMap.put(group, groupBalance);
        customer.setGroupId(group.getGroupId());
    }

    public static void removeCustomerFromGroup(Group group, Customer customer) throws CustomerException, GroupException {

        group.removeCustomer(customer);

        int groupBalance = groupPointsMap.get(group) - customerPointsMap.get(customer);
        groupPointsMap.put(group, groupBalance);
        customer.setGroupId(null);

    }

    public static void creditPoints() {
        Scanner sc = new Scanner(System.in);
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
    }

    public static void debitPoints() {
        Scanner sc = new Scanner(System.in);
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
    }

    public static void showCustomerBalance() throws CustomerException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Customer ID : ");
        String customerId = sc.nextLine();
        Customer customer = customerIdMap.get(customerId);
        if (customer == null) {
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_EXIST);
        }
        System.out.print("Balance = ");
        System.out.println(customerPointsMap.get(customer));
    }

    public static void showGroupBalance() throws GroupException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Group ID : ");
        String groupId = sc.nextLine();
        Group group = groupIdMap.get(groupId);
        if (group == null) {
            throw new GroupException(ErrorMessage.GROUP_NOT_EXIST);
        }
        System.out.print("Balance = ");
        System.out.println(groupPointsMap.get(group));

    }
}
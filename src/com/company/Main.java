package com.company;

import com.company.dto.*;
import com.company.exception.*;
import com.company.service.CreditTransaction;
import com.company.service.DebitTransaction;
import com.company.service.Transaction;

import java.util.HashMap;
import java.util.Scanner;

import static com.company.dto.Scheme.schemeTypes;
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

            try {
                switch (option) {
                    case "1": {
                        createNewGroup();
                        break;
                    }
                    case "2": {
                        createNewCustomer();
                        break;
                    }
                    case "3": {
                        createNewScheme();
                        break;
                    }
                    case "4": {
                        removeScheme();
                        break;
                    }
                    case "5": {
                        updateCustomerList(customerGroup.ADD);
                        break;
                    }
                    case "6": {
                        updateCustomerList(customerGroup.REMOVE);
                        break;
                    }
                    case "7": {
                        joinScheme();
                        break;
                    }
                    case "8": {
                        leaveScheme();
                        break;
                    }
                    case "9": {
                        creditPoints();
                        break;
                    }
                    case "10": {
                        debitPoints();
                        break;
                    }
                    case "11": {
                        showCustomerBalance();
                        break;
                    }
                    case "12": {
                        showGroupBalance();
                        break;
                    }
                    case "13": {
                        System.out.println("Exiting");
                        System.exit(0);
                        break;
                    }
                    default: {
                        System.out.println("Enter a valid option ");
                        break;
                    }
                }
            } catch (GroupException | CustomerException | SchemeException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void printMenu() {
        System.out.println("1.Create a new Group");
        System.out.println("2.Create a new Customer");
        System.out.println("3.Create a new Scheme");
        System.out.println("4.Remove a Scheme");
        System.out.println("5.Join Group");
        System.out.println("6.Leave Group");
        System.out.println("7.Join Scheme");
        System.out.println("8.Leave Scheme");
        System.out.println("9.Credit Points");
        System.out.println("10.Debit Points");
        System.out.println("11.Show Customer Balance");
        System.out.println("12.Show Group Balance");
        System.out.println("13.Exit");
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

    public static void createNewScheme() throws SchemeException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter scheme Id: ");
        String schemeId = sc.nextLine().toUpperCase();

        if(schemeTypes.contains(schemeId)) {
            throw new SchemeException(ErrorMessage.SCHEME_ALREADY_EXIST);
        } else {
            schemeTypes.add(schemeId);
        }
    }

    public static void removeScheme() throws SchemeException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter scheme Id: ");
        String schemeId = sc.nextLine().toUpperCase();

        if(!schemeTypes.contains(schemeId)) {
            throw new SchemeException(ErrorMessage.SCHEME_DOES_NOT_EXIST);
        } else {
            schemeTypes.remove(schemeId);
        }
    }

    public static void updateCustomerList(customerGroup flag) throws GroupException, CustomerException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of Customers to be updated : ");
        int n = Integer.parseInt(sc.nextLine());
        System.out.println("Enter your Group id: ");
        String groupId = sc.nextLine();
        Group group = groupIdMap.get(groupId);

        if(n==1) {
            System.out.println("Enter your Customer ID : ");
            String customerId = sc.nextLine();
            Customer customer = customerIdMap.get(customerId);

            checkValidity(group, customer);

            if (flag == customerGroup.ADD) {
                addCustomerToGroup(group, customer);
            } else {
                removeCustomerFromGroup(group, customer);
            }
        } else {
            Customer[] customers = new Customer[n];
            for(int i=0; i<n; i++) {
                System.out.println("Enter your Customer ID : ");
                String customerId = sc.nextLine();
                Customer customer = customerIdMap.get(customerId);
                customers[i] = customer;
                checkValidity(group, customer);
            }
                if (flag == customerGroup.ADD) {
                    addCustomerToGroup(group, customers);
                } else {
                    removeCustomerFromGroup(group, customers);
                }
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

        group.joinGroup(customer);

        int groupBalance = groupPointsMap.get(group) + customerPointsMap.get(customer);
        groupPointsMap.put(group, groupBalance);
    }

    public static void addCustomerToGroup(Group group, Customer[] customer) throws CustomerException, GroupException {

        int balance = 0;
        for(int i=0; i<customer.length; i++ ) {
            balance += customerPointsMap.get(customer[i]);
        }

        group.joinGroup(customer);

        int groupBalance = groupPointsMap.get(group) + balance;
        groupPointsMap.put(group, groupBalance);
    }

    public static void removeCustomerFromGroup(Group group, Customer customer) throws CustomerException, GroupException {

        group.leaveGroup(customer);

        int groupBalance = groupPointsMap.get(group) - customerPointsMap.get(customer);
        groupPointsMap.put(group, groupBalance);
    }

    public static void removeCustomerFromGroup(Group group, Customer[] customer) throws CustomerException, GroupException {

        int balance = 0;
        for(int i=0; i<customer.length; i++ ) {
            balance += customerPointsMap.get(customer[i]);
        }

        group.leaveGroup(customer);

        int groupBalance = groupPointsMap.get(group) - balance;
        groupPointsMap.put(group, groupBalance);
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

    public static void joinScheme() throws SchemeException, CustomerException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Customer ID : ");
        String customerId = sc.nextLine();
        Customer customer = customerIdMap.get(customerId);

        if(customer == null ) {
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_EXIST);
        }

        if(customer.getScheme() != null ) {
            throw new SchemeException(ErrorMessage.CUSTOMER_OPTED_SCHEME);
        }

        System.out.println("Enter schemeId ");
        String schemeId = sc.nextLine().toUpperCase();
        Scheme scheme;

        if (!schemeTypes.contains(schemeId)) {
            throw new SchemeException(ErrorMessage.SCHEME_DOES_NOT_EXIST);
        } else {
            if(schemeId.equalsIgnoreCase("UkClubCard")) {
                scheme = new UKClubCard();
            } else if(schemeId.equalsIgnoreCase("UkSavers")) {
                scheme = new UKSavers();
            } else if(schemeId.equalsIgnoreCase("ClubCardPlus")) {
                scheme = new ClubCardPlus();
            } else {
                scheme = new ColleagueDiscount();
            }
            customer.setScheme(scheme);
            scheme.joinScheme(customer);
        }
    }

    public static void leaveScheme() throws SchemeException, CustomerException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Customer ID : ");
        String customerId = sc.nextLine();
        Customer customer = customerIdMap.get(customerId);

        if(customer == null ) {
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_EXIST);
        }

        if(customer.getScheme() == null ) {
            throw new SchemeException(ErrorMessage.CUSTOMER_NOT_OPTED_SCHEME);
        }

        Scheme scheme = customer.getScheme();

        scheme.leaveScheme(customer);
        customer.setScheme(null);
    }

}
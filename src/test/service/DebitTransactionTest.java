package test.service;

import main.java.com.company.dto.Customer;
import main.java.com.company.dto.Group;
import main.java.com.company.exception.BalanceNotSufficientException;
import main.java.com.company.exception.CustomerException;
import main.java.com.company.exception.ErrorMessage;
import main.java.com.company.service.CreditTransaction;
import main.java.com.company.service.DebitTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DebitTransactionTest {
    @Mock
    private DebitTransaction debitTransaction;

    @Mock
    private Customer customer;

    @Mock
    private HashMap<String, Group> groupIdMap;

    @Mock
    private HashMap<Group,Integer> groupPointsMap;

    @Mock
    private HashMap<Customer,Integer> customerPointsMap;



    @Test(expected = CustomerException.class)
    public void shouldThrowExceptionWhwnNonExistingCustomerCallsDebit() throws CustomerException, BalanceNotSufficientException {
        DebitTransaction debitTransaction = new DebitTransaction();
        debitTransaction.execute(null,0);
    }

}
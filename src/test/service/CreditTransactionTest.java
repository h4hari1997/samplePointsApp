package test.service;

import main.java.com.company.dto.Customer;
import main.java.com.company.dto.Group;
import main.java.com.company.exception.CustomerException;
import main.java.com.company.exception.ErrorMessage;
import main.java.com.company.service.CreditTransaction;
import main.java.com.company.service.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreditTransactionTest {

    @InjectMocks
    private CreditTransaction creditTransaction;

    @Mock
    private Transaction transaction;

    @Mock
    private Customer customer;

    @Mock
    private HashMap<String, Group> groupIdMap;

    @Mock
    private HashMap<Group,Integer> groupPointsMap;

    @Mock
    private HashMap<Customer,Integer> customerPointsMap;


    @Test(expected = CustomerException.class)
    public void shouldThrowExceptionWhwnNonExistingCustomerCallsCredit() throws CustomerException {
        CreditTransaction creditTransaction = new CreditTransaction();
        creditTransaction.execute(null,0);
    }

}

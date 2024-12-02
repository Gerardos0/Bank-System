package tst;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import src.account.credit.Credit;
import src.fileUsage.Logger.Logger;
import src.person.customer.Customer;


/**
 * This class is to test if the methods in credit work properly.
 * The methods with returns in Accounts are canDeposit, CanWithdraw, changeBalance.
 * The changeBalance contains canDeposit and CanWithdraw
 * @author Gerardo Sillas
 */
public class CreditTest {
    Customer testHolder;
    Credit testCreditAccount;
    Logger testFile = Logger.getInstance();
 
    @Before
    public void setup(){
        testHolder = new Customer();
        testCreditAccount = new Credit(3000, 0, testHolder, 0);
    }

    @After
    public void tearDown(){
        testHolder = null;
        testCreditAccount = null;
    }


    /**
     * test the changeBalance function in Credit to see if withdraw is to large, will it fail
     */
    @Test
    public void testChangeBalanceNegativeInfinity(){
    //try a condition that should always fail
        assertFalse(testCreditAccount.changeBalance(Double.NEGATIVE_INFINITY));
    }  

    /**
     * test the changeBalance function in Credit to see if withdraw is nothing, will it pass
     */
    @Test
    public void testChangeBalanceZero(){
        //try 0
        assertTrue(testCreditAccount.changeBalance(0));
    }    

    /**
     * test the changeBalance function in Credit to see if withdraw of a decimal valued number with a diffrence of balance and amount is less than or equal to max, will pass
     */
    @Test
    public void testChangeBalanceDecimalValuesPass(){
        //try valeus with decimals
        //balance == 0
        testCreditAccount.setMax(0.5);
        assertTrue(testCreditAccount.changeBalance(-0.5));
    }    

    /**
     * test the changeBalance function in Credit to see if withdraw of a decimal valued number with a diffrence of balance and amount is greater than max, will fail
     */
    @Test
    public void testChangeBalanceDecimalValuesFail(){
        //try valeus with decimals
        //balance == 0
        //max == 0
        assertFalse(testCreditAccount.changeBalance(-0.5));
    }    

    /**
     * test the changeBalance function in Credit to see if withdraw of a whole valued number with a diffrence of balance and amount is less than or equal to max, will pass
     */
    @Test
    public void testChangeBalanceWholeValuesPass(){
        //change value for whole valued numbers
        testCreditAccount.setMax(1);
        assertTrue(testCreditAccount.changeBalance(-1));
    }   

    /**
     * test the changeBalance function in Credit to see if withdraw of a whole valued number with a diffrence of balance and amount is greater than max, will fail
     */
    @Test
    public void testChangeBalanceWholeValuesFail(){
        //change balance exceeds max
        //max == 0
        assertFalse(testCreditAccount.changeBalance(-1));
    }   

    /**
     * test the changeBalance function in Credit to see if paying off debt for a decimal valued amount that is less than or equal to balance, will pass
     */
    @Test
    public void testChangeBalanceDecimalValuePayOffCreditDebtPass(){
        //pay off debt on your credit 
        testCreditAccount.setBalance(-0.5);
        assertTrue(testCreditAccount.changeBalance(0.5));
    }  

    /**
     * test the changeBalance function in Credit to see if paying off debt for a decimal valued amount that is greater than balance, will fail
     */
    @Test
    public void testChangeBalanceDecimalValuePayOffCreditDebtFail(){
        //try to pay more than you owe
        //balance == 0
        assertFalse(testCreditAccount.changeBalance(0.5));
    } 

    /**
     * test the changeBalance function in Credit to see if paying off debt for a whole valued amount that is less than or equal to balance, will pass
     */
    @Test
    public void testChangeBalanceWholeValuePayOffCreditDebtPass(){
        //pay off debt on your credit 
        testCreditAccount.setBalance(-1);
        assertTrue(testCreditAccount.changeBalance(1));
    }  


    /**
     * test the changeBalance function in Credit to see if paying off debt with a whole valued amount that is greater than balance, will fail
     */
    @Test
    public void testChangeBalanceWholeValuePayOffCreditDebtFail(){
        //try to pay more than you owe
        //balance == 0
        assertFalse(testCreditAccount.changeBalance(1));
    } 


    @Test
    public void testWithdrawFalse(){
        testCreditAccount.setMax(0);
        assertFalse(testCreditAccount.canWithdraw(-1));
    }
 
    @Test
    public void testWithdrawTrue(){
        testCreditAccount.setBalance(1);
        assertTrue(testCreditAccount.canWithdraw(1));
    }
}

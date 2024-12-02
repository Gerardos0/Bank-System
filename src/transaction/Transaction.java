package src.transaction;

import java.util.List;

import src.account.Account;
import src.person.customer.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
/**
 * this class holds the transaction log of all the users and also customer data needed for generating a bank statment for a user
 */
public class Transaction {

    protected Customer accountHolder;
    /**
     * Dictionary of <Strin,Double> to store the starting and current values based on the account type.
     */
    Dictionary<String,Double> startingBalance = new Hashtable<>();
    Dictionary<String,Double> currentBalance = new Hashtable<>();
    /**
     * List of String to represent the transactions 
     */
    List<String> transactions = new ArrayList<>();
    /**
     * gets the current date
     */
    Date todaysDate = new Date();
    Dictionary<String,Account> accounts;

    /**
     * A setter to set all the information for the transaction. Does not set the Dictionary for current balance, that will be set when generating the bank statement
     * @param accountHolder a customer object that will be used to set all the attribtues besides the currentBalance and transactions
     */
    public void setInformation(Customer accountHolder){
        this.accountHolder = accountHolder;
        this.accounts = accountHolder.getAccounts();
        double checkingAccountStartingBalance = accounts.get("checking").getStartingBalance();
        double savingsAccountStartingBalance = accounts.get("saving").getStartingBalance();
        double creditAccountStartingBalance = accounts.get("credit").getStartingBalance();

        startingBalance.put("checking", checkingAccountStartingBalance);
        startingBalance.put("saving", savingsAccountStartingBalance);
        startingBalance.put("credit", creditAccountStartingBalance);
    }

    /**
     * sets the current balance
     * is going to be used when settint the current balance dictionary.
     * this is going to be used when generating the bank statment
     * @param accountHolder to get the currentBalance
     */
    public void setCurrentBalance(Customer accountHolder){
        double checkingAccountCurrentBalance = accounts.get("checking").getBalance();
        double savingsAccountCurrentBalance = accounts.get("saving").getBalance();
        double creditAccountCurrentBalance = accounts.get("credit").getBalance();

        startingBalance.put("checking", checkingAccountCurrentBalance);
        startingBalance.put("saving", savingsAccountCurrentBalance);
        startingBalance.put("credit", creditAccountCurrentBalance);
    }

    /**
     * add an entry to the transaction log
     * @param entry the log that is going to be inserted into the transactions list
     */
    public void addTransactionEntry(String entry){
        transactions.add(entry);
    }
}

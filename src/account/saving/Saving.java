package src.account.saving;

import src.account.Account;
import src.person.customer.Customer;

/**
 * Saving class exteneded off Account class to represent Saving Account
 * @author Gerardo Sillas
 */
public class Saving extends Account{
    public Saving(){}
    //constructor that takes in all the attributes and sets them
    public Saving(int accountNumber, double balance, Customer holder){
        super(accountNumber,balance, holder);
    }
}

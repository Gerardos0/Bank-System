package src.account.credit;

import src.account.Account;
import src.fileUsage.Logger.Logger;
import src.person.customer.Customer;

/**
 * Credit class that is extended off Account class to represent Credit Accounts
 * @author Gerardo Sillas
 */
 
 public class Credit extends Account{
    /**
     * represents the limit to how much the balance can be without being declined
     */
    private double max;
   
    public Credit(){}
   
    //constuctor that sets all the atttributes including max
    public Credit(int accountNumber, double balance, Customer holder, double max){
        super(accountNumber,balance, holder);
        this.max = max;
    }
 
    //max setter
    public void setMax(double max){
        this.max = max;
    }
    //max getter
    public double getMax(){
        return max;
    }
 
    /**
     * changes the balance based on the amount given
     * @param amount positive double that shows how much money is trying to be charged to the credit account
     * @return boolean that shows wether the charge went through
     */
    public boolean changeBalance(double amount, Logger log){
        String output = "";
        if(amount < 0  && canWithdraw(amount)){
            balance += amount;
            output = "Withdrew " + -amount + " from " + accountNumber + " account";
            System.out.println(output);
            log.Use(accountHolder.getName() + " " + output);
            return true;
        }
        else if(amount >= 0 && canDeposit(amount)){
            balance += amount;
            output = "Deposited " + amount + " into " + accountNumber + " account";
            System.out.println(output);
            log.Use(accountHolder.getName() + " " + output);
            return true;
        }
        System.out.println("Cannot perform transaction");
        return false;
    }
    /**
     * prints the account holder first name, last name, account number, max, and balance
     */
    public void displayAccount(){
        System.out.println("The account number for "+ accountHolder.getFirstName() + " " + accountHolder.getLastName() + "is " + accountNumber + "and this account has a max limit of: $" + max + "and has a used: $"+ balance);
    }
 
    /**
     * checks if you the amount will put the users credit total charge past the max amount
     * @param amount positive double that shows how much money is trying to be charged to the credit account
     * @return boolean that tells if the sum of balance and amount will exceeed the max
     */
    public boolean canWithdraw(double amount){
        if((balance + amount) < (max*-1) || lock){
            return false;
        }
        return true;
    }
   /**
    * check if the deposit amount is more than what is being owed
    * @param positive double, amount that is being used to pay off the debt
    * @return boolean to show if the amount was less than equal to what was owed
    */
    public boolean canDeposit(double amount){
        if((balance + amount) <= 0 && (balance + amount) >= (max*-1) ||lock ){
            return true;
        }
        return false;
    }
 
}

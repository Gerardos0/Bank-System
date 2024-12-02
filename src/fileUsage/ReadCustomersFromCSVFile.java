package src.fileUsage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import src.CSVException;
import src.RunBank;
import src.account.Account;
import src.account.checking.Checking;
import src.account.credit.Credit;
import src.account.saving.Saving;
import src.person.customer.Customer;
import src.transaction.Transaction;


/**
 * This class implements the FileUsage interface
 * The class takes an input filePath and reads the CSV file given. It then populateds the customerList, accountList and IDList, which are used for quickly look up customers throughout the programs life span.
 * @author Gerardo Sillas
 */
public class ReadCustomersFromCSVFile implements FileUsage {
    /**
     * In this method every input line read is converted into Customer with fully set attributes and Account attributes. They are then returned for use in the system
     * The names list is used as a way of sorting the list when you update the CSV file. There are no returns since the varibales that would have been returned are static variables in the RunBank class taht are being refrenced in this method. 
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
          * @throws CSVException 
          */
         public void Use(String filePath) throws CSVException{
        Random random = new Random();
        String line;
        //try to read CSV file
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            line = br.readLine();
            RunBank.titles = line;
            //Get the categories so you can set the attributes of the customer and accounts properly
            String[] categories = line.split(",");
            //in case the ID is not given in the input CSV file
            int currentID = 1;
            //read each line until no more lines
            while((line = br.readLine())!= null){
                //split line by the commas and store in a String array
                String[] customerData = line.split(",");
                // Create cutsomer and account objects and set the attibute account holder for each account
                Customer currentAccountHolder = new Customer();
                Account checkingAccount = new Checking();
                checkingAccount.setAccountHolder(currentAccountHolder);
                Account savingAccount = new Saving();
                savingAccount.setAccountHolder(currentAccountHolder);
                Credit creditAccount = new Credit();
                creditAccount.setAccountHolder(currentAccountHolder);
                // set the account numbers if the account numbers were not given in the input CSV file
                int checkingAccountNumber = 0;
                int savingAccountNumber = 0;
                int creditAccountNumber = 0;
                checkingAccountNumber = currentID + 999;
                checkingAccount.setAccountNumber(checkingAccountNumber);
                savingAccountNumber = currentID + 1999;
                savingAccount.setAccountNumber(savingAccountNumber);
                creditAccountNumber = currentID + 2999;
                creditAccount.setAccountNumber(creditAccountNumber);
                // set accountHolder ID in case it wasnt given in the input CSV
                currentAccountHolder.setId(currentID);
                currentID++;
                // current index to get the value in the customerData array to set the attributes
                int customerDataCurrentIndex = 0;
                // iterate through the categories and the customerData arrays to set the attributes
                for(String category: categories){
                    switch (category){
                        case "Identification Number":
                            currentAccountHolder.setId(Integer.parseInt(customerData[customerDataCurrentIndex]));
                            //customer password is going to be their id
                            currentAccountHolder.setPassword(Integer.parseInt(customerData[customerDataCurrentIndex]));

                        case "First Name":
                            currentAccountHolder.setFirstName(customerData[customerDataCurrentIndex]);
                            break;
                        case "Last Name":
                            currentAccountHolder.setLastName(customerData[customerDataCurrentIndex]);
                            break;
                        case "Date of Birth":
                            currentAccountHolder.setDOB(customerData[customerDataCurrentIndex]);
                            break;
                        case "Address":
                            currentAccountHolder.setAddress(customerData[customerDataCurrentIndex]+","+customerData[customerDataCurrentIndex+ 1]+","+ customerData[customerDataCurrentIndex+2]);
                            customerDataCurrentIndex += 2;
                            break;
                        case "Phone Number":
                            currentAccountHolder.setPhoneNumber(customerData[customerDataCurrentIndex]);
                            break;

                        case "Checking Account Number":
                            checkingAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            checkingAccount.setAccountNumber(checkingAccountNumber);
                            break;

                        case "Checking Starting Balance":
                            checkingAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Savings Account Number":
                            savingAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            savingAccount.setAccountNumber(savingAccountNumber);
                            break;

                        case "Savings Starting Balance":
                            savingAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Account Number":
                            creditAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            creditAccount.setAccountNumber(creditAccountNumber);
                            break;

                        case "Credit Max":
                            creditAccount.setMax(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Starting Balance":
                            creditAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Score":
                            int creditScore = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            currentAccountHolder.setCreditScore(creditScore);
                            if(creditScore <= 580)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(599) + 100);
                            else if(581<= creditScore && creditScore<= 669)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(4299) + 700);
                            else if(670<= creditScore && creditScore<= 739)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(2499) + 5000);
                            else if(740<= creditScore && creditScore<= 799)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(8499) + 7500);
                            else
                                creditAccount.setMax(random.nextDouble() + random.nextInt(9000) + 16000);
                            break;
                                
                    }
                    customerDataCurrentIndex++;
                }
                // create array of accounts to set the attribute in the customer object
                Dictionary<String,Account> accounts = new Hashtable<>();
                accounts.put("checking", checkingAccount);
                accounts.put("saving", savingAccount);
                accounts.put("credit", creditAccount);
                //put accoounts in the Customers accounts Dictionary
                currentAccountHolder.setAccounts(accounts);
                //create the object that will store the transactions
                Transaction transactions = new Transaction();
                //set the information in the Transaction object, informaiton is retrieved from object
                transactions.setInformation(currentAccountHolder);
                //put the Transaction object in the customer object
                currentAccountHolder.setTransactions(transactions);
                //Store Customer in a Dictionary of Customers with the key as the ID
                RunBank.IDList.put(currentAccountHolder.getId(), currentAccountHolder);
                // Strore the customers in a Dictionary of Customers with the key as the account numbers of the their 3 accounts
                RunBank.accountList.put(checkingAccountNumber, currentAccountHolder);
                RunBank.accountList.put(savingAccountNumber, currentAccountHolder);
                RunBank.accountList.put(creditAccountNumber, currentAccountHolder);
                // Store the customers int a Dictioanry of Customers with the key as their name
                RunBank.customerList.put(currentAccountHolder.getName(),currentAccountHolder);
                //create a list storing all the names so that when you update your CSV file you can keep the order
                RunBank.names.add(currentAccountHolder.getName());
            }
        }
        //catch if reading failed
        catch(IOException IOEXCEPTION){
            throw new CSVException("Reading the CSV File failed", IOEXCEPTION);
        }
        catch(NullPointerException NULLPOINTEXCEPTION){
            throw new CSVException("File path was NULL!", null);
        }
    }
}

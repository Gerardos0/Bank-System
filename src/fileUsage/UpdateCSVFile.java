package src.fileUsage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import src.CSVException;
import src.RunBank;
import src.account.Account;
import src.account.credit.Credit;
import src.person.customer.Customer;


/**
 * this file implements the FileUsage interface
 * this fille takes an filePath as input and writes down/ updates the file with the updated current information of each customer
 * the format is dependent on the format of the input CSV file
 * @author Gerardo Sillas
 */
public class UpdateCSVFile implements FileUsage{
    /**
     * Update the CSV File with the new entries and any altered entry that happened throughout the life cycle of the program. 
     * We decided to create a seperate file path for the updated CSV file rather than overwriting the original. If needed the file path can be changed to the original file so that the updates end up there. 
     * @param List<Dictionary<?,Customer>> list of Customers that are going to have there attributes and account attributes convereted into strings and updated in the CSV file
     * @param filePath to increase flexibility, filePath added incase needed again for future project
     * @throws CSVException  Exception thrown if Updating the file creates an error
     */
    public void Use(String filePath) throws CSVException{
        //try to update CSV
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            //write the titles before the data
            writer.write(RunBank.titles);
            writer.newLine();
            //Update each customer one by one
            //iterate through every customer and write their data in the CSV
            String[] categories = RunBank.titles.split(",");
            for(String name : RunBank.names){
                Customer currentAccountHolder = RunBank.customerList.get(name);
                Account checkingAccount = currentAccountHolder.getAccounts().get("checking");
                Account savingAccount = currentAccountHolder.getAccounts().get("saving");
                Credit creditAccount = (Credit) currentAccountHolder.getAccounts().get("credit");
                boolean addComma = false;
                for(String category : categories){
                    if(addComma)
                        writer.write(",");
                    else
                        addComma = true;
                    switch (category){
                        case "Identification Number":
                            writer.write(Integer.toString(currentAccountHolder.getId()));
                            break;
                        
                        case "First Name":
                            writer.write(currentAccountHolder.getFirstName());
                            break;
                        case "Last Name":
                            writer.write(currentAccountHolder.getLastName());
                            break;
                        case "Date of Birth":
                            writer.write(currentAccountHolder.getDOB());
                            break;
                        case "Address":
                            writer.write(currentAccountHolder.getAddress());
                            break;
                        case "Phone Number":
                            writer.write(currentAccountHolder.getPhoneNumber());
                            break;

                        case "Checking Account Number":
                            writer.write(Integer.toString(checkingAccount.getAccountNumber()));
                            break;

                        case "Checking Starting Balance":
                            writer.write(Double.toString(checkingAccount.getBalance()));
                            break;

                        case "Savings Account Number":
                            writer.write(Integer.toString(savingAccount.getAccountNumber()));
                            break;

                        case "Savings Starting Balance":
                            writer.write(Double.toString(savingAccount.getBalance()));
                            break;

                        case "Credit Account Number":
                            writer.write(Integer.toString(creditAccount.getAccountNumber()));
                            break;

                        case "Credit Max":
                            writer.write(Double.toString(creditAccount.getMax()));
                            break;

                        case "Credit Starting Balance":
                            writer.write(Double.toString(creditAccount.getBalance()));
                            break;

                        case "Credit Score":
                            writer.write(Integer.toString(currentAccountHolder.getCreditScore()));
                            break;
                    }  
                }
                writer.newLine();
            }
        }
        //catch if reading failed
        catch(IOException IOEXCEPTION){
            throw new CSVException("Writing the CSV File failed", IOEXCEPTION);
        }
        catch(NullPointerException NULLPOINTEXCEPTION){
            throw new CSVException("File path was NULL!", null);
        }
    }

}

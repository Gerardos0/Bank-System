package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import src.account.Account;
import src.account.checking.Checking;
import src.account.credit.Credit;
import src.account.saving.Saving;
import src.fileUsage.ReadCustomersFromCSVFile;
import src.fileUsage.UpdateCSVFile;
import src.fileUsage.Logger.Logger;
import src.person.customer.Customer;
import src.transaction.TransactionsAccess;
import src.transaction.TransactionsAccessProxy;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Dictionary;
import java.util.List;
import java.util.ArrayList;




/**
 * Driver class containg User Interface and CSV conversion to create the bank system. 
 * @author Hannah Ayala
 */

public class RunBank{
/** 
 * A dictionary that maps a combined key of first name and last name to a Customer object.
 */
public static Dictionary<String, Customer> customerList = new Hashtable<>(); 

/** 
 * A dictionary that maps IDs to Customer objects.
 */
public  static Dictionary<Integer, Customer> accountList = new Hashtable<>(); 

/*
 *A dictionary that maps account identifiers to Customer objects.
 */
public static Dictionary<Integer, Customer> IDList = new Hashtable<>();

/** 
 * A flag indicating whether the customer wants to exit the application.
 */
public static boolean exit = false; 

/** 
 * A flag indicating whether the customer wants to return to the main menu.
 */
public static boolean mainMenu = false; 

/** 
 * A flag indicating whether a customer's deposit was successful.
 */
private static boolean successD = true; 

/** 
 * A flag indicating whether a customer's withdrawal was successful.
 */
private static boolean successW = true; 

/** 
 * A flag indicating whether a customer's transfer was successful.
 */
private static boolean successT = true; 

/** 
 * A flag indicating whether a payment made by a customer was successful.
 */
private static boolean successP = true; 

/** 
 * A flag indicating whether creating a user made by a customer was successful.
 */
private static boolean successU = true;

/** 
 * A Scanner object used for input from the user.
 */
private static Scanner sc = new Scanner(System.in);

/**
 * List of names to maintain the order of the users, so that when the updatingCSVFile method  is invoked, it can update the CSV file based on the order they were read
 */
public static List<String> names = new ArrayList<>();
	
/**
 * titles for the updated CSV file(first line) 
 */
public static String titles;

/**
 * shows the total number of customers so the ID and the account numbers can be created.
 */
private static int totalCustomers;


/**
 * Logger instance for logging events within the class.
 * This `Logger` is protected and static, allowing it to be shared across
 * instances of this class and accessible to subclasses.
 */
public static Logger log = Logger.getInstance();


/**
 * Instance of ReadCustomersFromCSVFile used to read customer data from a CSV file.
 * This static instance is shared across the class, allowing easy access to methods
 * for retrieving customer data from a CSV source.
 */
static ReadCustomersFromCSVFile readCustomersFromCSVFile = new ReadCustomersFromCSVFile();

/**
 * Instance of UpdateCSVFile used to update or modify data in an existing CSV file.
 * This static instance is shared across the class, enabling centralized access to CSV
 * file modification methods.
 */
static UpdateCSVFile updateCSVFile = new UpdateCSVFile();
    /** 
     * The entry point of the application. This method initializes the bank application,
     * reads customer data from a CSV file, and runs the main banking loop.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String args[]){
        //declare file location (file path)
        String filePath =  "./src/resources/CS 3331 - Bank Users.csv";
        //read CSV file and create a list of "Customer"s from the entreis in the file
        try{
        readCustomersFromCSVFile.Use(filePath);
        }catch(CSVException readCSVFileException){
            System.out.println(readCSVFileException.getMessage());
            if(readCSVFileException.getCause() != null){
                System.out.println("Would you like to trace the error? Y/N");
                String response = sc.nextLine();
                if(response.equals("Y") || response.equals("Yes") || response.equals("yes") || response.equals("YES")){
                    readCSVFileException.printStackTrace();
                }
            }
        }
        totalCustomers = names.size();
        //get the log ready for input
        log.setUp("log");
        //runs the bank
        System.out.println("Welcome to El Paso Miners Bank!");

        while(!exit){ //will continue going to main manu unless the user chooses to exit
           main_menu(); //manager or customer        
        }
        System.out.println("Thank you for choosing us!");
        //ends

        filePath = "./src/resources/Result.csv";
        //Update the CSV with any changes made the to the list of "Customer"s
        try{
            updateCSVFile.Use(filePath);
    
        }catch(CSVException updateCSVFileException){
            System.out.println(updateCSVFileException.getMessage());
            if(updateCSVFileException.getCause() != null){
                System.out.println("Would you like to trace the error? Y/N");
                String response = sc.nextLine();
                if(response.equals("Y") || response.equals("Yes") || response.equals("yes") || response.equals("YES")){
                    updateCSVFileException.printStackTrace();
                }
            }
        }
    }
    /** 
     * Displays the main menu for the banking application, allowing access 
     * 
     * based on the type of user (manager or regular customer). It manages 
     * the flow of the application, directing users to the appropriate menu 
     * options based on their selection.
     *
     */
    private static void main_menu(){
        int users = typeOfUser(); //will get the type of user
           switch (users){
                case 0: //manager
                    System.out.println("You have chosen Bank Manager access");
                    mainMenu = false;
                    while(!mainMenu && !exit){  //while they dont choose to go back to the main manu
                        managerMenu(); //provide manager menu with options
                    }
                    break;
                case 1: //normal user
                    System.out.println("You have chosen regular user.");
                    mainMenu = false;
                    String customerName = getCustomer(); //will get name of customer and validate it
                    if (customerName != null){
                        boolean check = true;
                        while(check){
                            System.out.println("Please enter the password related to " + customerName  + " (main menu (m) or exit (e))");
                            String input = sc.next();
                            while (input.equals("")) input = sc.next();
                            switch (input.toLowerCase()){
                                case "exit":
                                case "e":
                                    mainMenu = true;
                                    exit = true;
                                    check = false;
                                    break;
                                case "m":
                                case "main menu":
                                case "main":
                                    mainMenu = true;
                                    check = false;
                                    break;
                            }
                            int password;
                            if (check){
                                try{
                                    password = Integer.parseInt(input);
                                    if (password != customerList.get(customerName).getPassword()) throw new Exception();
                                    check = false;
                                    System.out.println("Correct password, proceed");
                                }catch (Exception e){
                                    System.out.println("Incorrect password, try again");
                                }
                            }
                        }
                    } 
                    while (!mainMenu && !exit){ //while they do not choose to go back to the main menu
                        userMenu(customerName); //provide the user with transaction options
                    }
           }
           
    }

    /** 
     * Displays the manager menu, allowing bank managers to inquire about
     * customer accounts either by customer name or account number. 
     *
     */
    private static void managerMenu(){
        exit = false;
        mainMenu = false; 
        boolean managerMenu = false; //reset flags
        String name;
        boolean loop;
        System.out.println("\nPlease input what transaction you would like to do.\nInquire by name (a)\nInquire by account number: (b)\nGenerate Bank Statement (g)\nPerform Transactions (t)\nLock (l) /Unlock an Account (u)\n(exit (e) or main menu (m))"); //options
        String option = sc.nextLine();
        while(option.equals("")) option = sc.nextLine();
        switch (option.toLowerCase()){ //based on option
            case("a"): //by name
                String customerName = getCustomer(); //get customer name and validate it
                while (!exit && !mainMenu && !managerMenu){ //while they do not choose to leave
                    System.out.println("Enter the account type"); //get account type
                    String input = sc.nextLine();
                    while(input.equals("")) input = sc.nextLine();
                    switch (input){
                        case("e"):
                        case("exit"): //actions
                            mainMenu = true;
                            exit = true;
                            break;
                        case("m"):
                        case("main"):
                        case("mainMenu"): //actions
                            mainMenu = true;
                            break;
                        default: //provided type or invalid
                            int type = verifyAccount(customerName, input); //verify account exists
                            if (type>0){
                                System.out.println(customerList.get(customerName).getAccounts().get(input).toString()); //if it exists, print the tostring
                                managerMenu = true; //done with this person, return to managerMenu
                                break;
                            }
                    }
                }
                break;
            case("b"): //by account number
                int num = getAccountNumber(); //get account number and validate
                Customer customer = (accountList.get(num));
                System.out.println(customer.getAccounts().get(customer.findAccountType(num)).toString()); //print the tostring
                break;
            case("g"):
            case("generate"):
            case("generate bank statement"):
                generateStatement();
                break;
            case("t"):
            case("transactions"):
                TransactionsAccess managerAccess = new TransactionsAccessProxy("Manager"); //proxy for the transactions file
                managerAccess.performConfidentialTransactions();
                break;
            case("l"):
            case("lock"):
                name = getCustomer();
                loop = true;
                while (name != null && loop){
                    System.out.println("Please input the type of the account you wish to lock for customer " + name);
                    String type = sc.next();
                    switch (type.toLowerCase()){
                        case("checking"):
                        case("saving"):
                        case("credit"):
                            customerList.get(name).getAccounts().get(type).lockAccount();
                            System.out.println("Account of type " + type + " is now locked for customer " + name);
                            loop = false;
                            break;
                        default:
                            System.out.println("Input type incorrect, please input checking, saving, or credit");
                    }
                }
                break;
            case("u"):
            case("unlock"):
                name = getCustomer();
                loop = true;
                while (name != null && loop){
                    System.out.println("Please input the type of the account you wish to unlock for customer " + name);
                    String type = sc.next();
                    switch (type.toLowerCase()){
                        case("checking"):
                        case("saving"):
                        case("credit"):
                            customerList.get(name).getAccounts().get(type).unlockAccount();
                            System.out.println("Account of type " + type + " is now unlocked for customer " + name);
                            loop = false;
                            break;
                        default:
                            System.out.println("Input type incorrect, please input checking, saving, or credit");
                    }
                }
                break;
                
            case("e"):
            case("exit"): //actions
                mainMenu = true;
                exit = true;
                break;
            case("m"):
            case("main"):
            case("main menu"): //actions
                mainMenu = true;
                break;
            default: //invalid input
                System.out.println("Please select from the options");
                managerMenu();
        }
    }


    /**
     * Displays the user menu for the banking application, allowing 
     * customers to perform various transactions such as checking balance, 
     * depositing, withdrawing, transferring funds, paying another customer, 
     * or logging out. The method handles user input and ensures valid 
     * operations are performed based on the chosen transaction type.
     *
     * @param customerName The name of the customer using the menu.
     */
    private static void userMenu(String customerName){
        successD =true;
        successP = true;
        successT = true;
        successW = true; //reset flags
        String customerName2 = "";
        System.out.println("\nPlease input what transaction you would like to do.\nCheck Balance (B)\nDeposit (D)\nwithdraw (W)\nTransfer (T)\nPay someone (P)\nCreate User (U)\nUser Transactions File (R)\nLogout/Return to Main Menu (L/M)\nExit (E)"); //options for the customer
        String input = sc.nextLine(); //stores the transaction (or action)

        while(input.equals("")) input = sc.nextLine();
        switch (input.toLowerCase()){
            case("b"):
            case("balance"):
            case("check balance"):
                int status = customerList.get(customerName).getBalance(); //will get the balance and display it (or action)
                if (status == -1){ //exit
                    exit = true;
                    break;
                }else if (status == -2){ //return to main menu
                    mainMenu = true;
                    break;
                }
                break;
            case("d"):
            case("deposit"):
                successD = transaction("deposit", customerName); //will deposit into one of customerName's accounts
                break;
            case("w"):
            case("withdraw"):
                successW = transaction("withdraw", customerName); //will withdraw from one of customerName's acocunts
                break;
            case("t"):
            case("transfer"):
                successT = transfer(customerName, customerName);
                break;
            case("p"):
            case("pay"):
            case("pay someone"):
                System.out.println("You will be prompted on the person that you will be paying");
                customerName2 = getCustomer();
                successP = transfer(customerName2, customerName);
                break;
            case("u"):
            case("user"):
            case("create user"):
                //create user
                successU = createUser();
                break;
            case("r"):
            case("transactions"):
            case("user transactions"):
                generateStatementHelper(customerName);
                break;
            case("l"):
            case("logout"):
            case("m"):
            case("main menu"):
            case("return to main menu"): //actions
                mainMenu = true;
                break;
            case("e"):
            case("exit"): //actions
                exit = true;
                break;
            default: //user did not select an option
                System.out.println("Please choose from the options provided and/or ensure your spelling is correct");
        }
        //if they haven't selected the option to exit verify if the operation they chose was successful
        //if the transaction wasn't successful, try it again until the user selects to return to main menu or to the user menu
        if(!exit){
            if (!successD){
                successD = transaction("deposit", customerName);
            }
            if (!successW){
                successW = transaction("withdraw", customerName);
            }
            if (!successT){
                successT = transfer(customerName, customerName);              
            }
            if (!successP){
                successP = transfer(customerName2, customerName);;
            }
            if (!successU){
                successU = createUser();
            }
        }
    }

    /**
     * Prompts the user to generate a bank statement based on a customer's name or ID.
     * If the user chooses to generate the statement by ID, the method retrieves the
     * customer's name using the provided ID. If the user chooses to generate the statement
     * by name, the method prompts the user to input a valid customer name.
     * 
     * @return true if the bank statement is successfully generated; false otherwise.
     */
    private static boolean generateStatement(){
        System.out.println("(A) for creating bank statment based on name\n(B) for creating bank statement based on id");
        String option = sc.next();
        while (option.equals("")) option = sc.next();
        option = option.toLowerCase();

        String name;
        if (option.equals("b")){
            System.out.println("Please input the id of the user");
            int id = sc.nextInt();
            name = IDList.get(id).getName();
        }
        else{
            name = getCustomer();
            if (name == null) return true;
        }

        return generateStatementHelper(name);
        
    }


    /**
     * Generates a detailed bank statement for the specified customer.
     * The statement includes:
     * Current date, Customer details, Account details for checking, saving, and credit accounts, All transactions involving the customer
     * 
     * @param name The name of the customer for whom the bank statement is generated.
     * @return true if the bank statement is successfully created; false if an error occurs.
     */
    private static boolean generateStatementHelper(String name){
        Logger statement = Logger.getInstance();
        statement.setUp(name + " - Bank Statement"); //set up the file for the user

        statement.Use("Date: " + LocalDate.now()); //current date
        statement.Use("\nCustomer Details:\n" + customerList.get(name).toString()); //customer details
        statement.Use("\nAccount Details:"); //all of the account details
        statement.Use("Checking ("+ customerList.get(name).getAccounts().get("checking").getAccountNumber() + ")");
        statement.Use("Starting Balance: " + customerList.get(name).getAccounts().get("checking").getStartingBalance());
        statement.Use("Ending/Current Balance: " + customerList.get(name).getAccounts().get("checking").getBalance());
        statement.Use("Saving (" + customerList.get(name).getAccounts().get("saving").getAccountNumber() + ")");
        statement.Use("Starting Balance: " + customerList.get(name).getAccounts().get("saving").getStartingBalance());
        statement.Use("Ending/Current Balance: " + customerList.get(name).getAccounts().get("saving").getBalance());
        statement.Use("Credit (" + customerList.get(name).getAccounts().get("credit").getAccountNumber() + ")");
        statement.Use("Starting Balance: " + customerList.get(name).getAccounts().get("credit").getStartingBalance());
        statement.Use("Ending/Current Balance: " + customerList.get(name).getAccounts().get("credit").getBalance());

        statement.Use("\nAll transactions: ");
		try {
            String filePath = "./log.txt";
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();

			while (line != null) {
				if (line.contains(name)){ //if the log mentions the customer
                    statement.Use(line); //add the statement to the users statement
                }
                line = reader.readLine();
			}
			reader.close();
            return true;
		} catch (Exception e) {
			e.printStackTrace();
            return false;
		}
    }

    /**
     * Facilitates a transfer of funds between two customers' accounts. 
     * The method prompts the user for the amount to transfer and the 
     * account details for both the sender and the recipient. It verifies 
     * the accounts involved in the transaction and performs the transfer 
     * if valid.
     *
     * @param customerName2 The name of the customer receiving the payment.
     * @param customerName The name of the customer making the payment.
     * @return True if the transfer was successful; otherwise, false.
     */
    private static boolean transfer(String customerName2, String customerName){
        System.out.println("How much would you like to pay"); //gets the amount for the transaction
        Double payAmount = sc.nextDouble();
        System.out.println("You will be prompted to provide information regarding the account you are paying from");
        String typeOne = getAccount();
        int typeOneStat = verifyAccount(customerName, typeOne); //get and verify account source
        while (typeOneStat==0){ //if not found, try again
            typeOne = getAccount();
            typeOneStat = verifyAccount(customerName2, typeOne);
        }
        if (typeOneStat<0) return true; //action (exit or main)

        System.out.println("You will be prompted to provide information regarding the account you are paying");
        String typeTwo = getAccount();
        int typeTwoStat = verifyAccount(customerName2, typeTwo); //get and verify account dest
        while (typeTwoStat==0){ //if not found, try again
            typeTwo = getAccount();
            typeTwoStat = verifyAccount(customerName2, typeTwo);
        }
        if (typeTwoStat<0) return true; //action (exit or main)
        
        if(typeOneStat == 0 || typeTwoStat == 0){ 
            return false;
        }
        if (typeOneStat == 1 && typeOneStat == typeTwoStat){ //both are types
            return transferHelper(customerName, customerName2, typeTwo, typeOne, payAmount);
        }else if (typeOneStat ==2 && typeOneStat == typeTwoStat){ //both are account numbers
            return transferHelper(customerName, customerName2, Integer.parseInt(typeOne), Integer.parseInt(typeTwo), payAmount);
        }
        System.out.println("Please provide both types of accounts or both account numbers not mismatch"); //accounts not correct
        return false;
    }

    /**
     * Facilitates a transfer or payment transaction between accounts by customer names and account types.
     * 
     * This method checks if both customer names are the same. If so, it transfers funds between two accounts
     * owned by the same customer. Otherwise, it initiates a payment from one customer to another.
     * 
     * @param customerName  the name of the customer initiating the transaction
     * @param customerName2 the name of the receiving customer, or the same customer if transferring between accounts
     * @param fromAccount   the type of the source account (e.g., "checking", "saving")
     * @param toAccount     the type of the destination account
     * @param amount        the amount to be transferred or paid
     * @return true if the transaction is successful; false otherwise
     */
    public static boolean transferHelper(String customerName, String customerName2, String fromAccount, String toAccount, double amount){
        if (customerName.equals(customerName2)) return customerList.get(customerName).transfer(fromAccount, toAccount, amount);
        return customerList.get(customerName).pay(customerList.get(customerName2), fromAccount, toAccount, amount);
    }

    /**
     * Facilitates a transfer or payment transaction between accounts by customer names and account numbers.
     * 
     * This method checks if both customer names are the same. If so, it transfers funds between two accounts
     * owned by the same customer. Otherwise, it initiates a payment from one customer to another.
     * 
     * @param customerName  the name of the customer initiating the transaction
     * @param customerName2 the name of the receiving customer, or the same customer if transferring between accounts
     * @param fromAccount   the account number of the source account
     * @param toAccount     the account number of the destination account
     * @param amount        the amount to be transferred or paid
     * @return true if the transaction is successful; false otherwise
     */
    public static boolean transferHelper(String customerName, String customerName2, int fromAccount, int toAccount, double amount){
        if(customerName.equals(customerName2)) return customerList.get(customerName).transfer(fromAccount, toAccount, amount);
        return customerList.get(customerName).pay(customerList.get(customerName2), fromAccount, toAccount, amount);
    }

    /**
     * Handles a banking transaction (deposit or withdrawal) for a specified customer. 
     * It prompts for account and verifies the account type or number.
     * If the account is valid, it calls the appropriate helper method to complete the transaction.
     *
     * @param transaction A string indicating the type of transaction ("deposit" or "withdraw").
     * @param customerName The name of the customer making the transaction.
     * @return True if the transaction was successful; otherwise, false.
     */
    private static boolean transaction(String transaction, String customerName){
        String type = getAccount(); 
        int state = verifyAccount(customerName, type); //get and verify the account
        switch (state){
            case(-1):
            case(-2):
            case(-3): //actions
                return true; 
            case(0): //try again
                return transaction(transaction, customerName);
            case(1): //valid type
                return transactionHelper(transaction, customerName, type);
            case(2): //valid account number
                return transactionHelper(transaction, customerName, Integer.parseInt(type));
        }
        return false;
    }


    /**
     * Verifies the validity of the specified account for the given customer. 
     * It checks if the account type or account number exists and returns 
     * an appropriate status code indicating the result of the verification.
     *
     * @param customerName The name of the customer whose account is being verified.
     * @param type The account type or account number to be verified.
     * @return An integer representing the verification result:
     *          -1, -2, -3 for exit codes
     *          0 if the account is invalid,
     *          1 if the account type is valid,
     *          2 if the account number is valid.
 */
    private static int verifyAccount(String customerName, String type){
        switch (type){
            case("u"):
            case("user menu"): //action
                return -3;
            case("e"):
            case("exit"): //action
                mainMenu = true;
                exit = true;
                return -1; 
            case("m"):
            case("main menu"): //action
                mainMenu = true;
                return -2;
            default: //
                if (customerList.get(customerName).getAccounts().get(type.toLowerCase()) == null){ //type is not the accountType, it could be an account number or not correct input
                    try {
                        String str = customerList.get(customerName).findAccountType(Integer.parseInt(type)); //if it isnt int, throw error
                        if (str == null) throw new Exception(); //if it is an int but not an account number, throw error
                        return 2; 
                    } catch (Exception e) {
                        System.out.println("No such account found");
                        return 0;
                    }
                }else{ //there is an account for customerName of type accountType
                    return 1;
                } 
        }
    }


    /**
     * A helper method that performs the actual deposit or withdrawal transaction 
     * for the specified customer and account type. It prompts the user for the 
     * amount and processes the transaction accordingly.
     *
     * @param transaction A string indicating the type of transaction ("deposit" or "withdraw").
     * @param customerName The name of the customer making the transaction.
     * @param type The account type for the transaction.
     * @return True if the transaction was successful; otherwise, false.
     */
    private static boolean transactionHelper(String transaction, String customerName, String type){
        System.out.printf("How much would you like to %s into the account\n", transaction); //gets the amount for the transaction
        double amount = sc.nextDouble();
        if (transaction.equals("deposit")){ //if deposit, then run deposit, which will check if it is possible
            return customerList.get(customerName).deposit(type, amount);
        }else{ //else it must be withdraw which will check if it is possible
            return customerList.get(customerName).withdraw(type, amount);
        }
    }

    /**
     * A helper method that performs the actual deposit or withdrawal transaction 
     * for the specified customer and account number. It prompts the user for the 
     * amount and processes the transaction accordingly.
     *
     * @param transaction A string indicating the type of transaction ("deposit" or "withdraw").
     * @param customerName The name of the customer making the transaction.
     * @param number The account number for the transaction.
     * @return True if the transaction was successful; otherwise, false.
     */
    private static boolean transactionHelper(String transaction, String customerName, int number){
        System.out.printf("How much would you like to %s into the account\n", transaction); //gets the amount for the transaction
        double amount = sc.nextDouble();
        if (transaction.equals("deposit")){ //if deposit, then run deposit, which will check if it is possible
            return customerList.get(customerName).deposit(number, amount);
        }else{ //else it must be withdraw which will check if it is possible
            return customerList.get(customerName).withdraw(number, amount);
        }
    }

    /**
     * Prompts the user to specify their type: a manager or a regular user. 
     * The method also allows the user to exit the program. 
     * It validates the input and returns an integer indicating the user type:
     *
     * @return An integer representing the type of user:
     *         0 for manager,
     *         1 for regular user,
     *         -1 for exit.
     */
    private static int typeOfUser(){
        System.out.println("Will the following transaction be from a manager (m) or a regular user (u)? (exit (e))");
        String input = sc.nextLine();
        while(input.equals("")) input = sc.nextLine();
        switch (input.toLowerCase()){
            case("e"):
            case("exit"):
                exit = true;
                return -1;
            case("m"):
            case("manager"):
                return 0;
            case("u"):
            case("user"):
            case("regular user"):
                return 1;
            default:
                System.out.println("Please input the correct term");
                return (typeOfUser());
        }
    }

    /**
     * Creates a new customer with associated checking, savings, and credit accounts.
     *
     * Prompts the user for required information (name, date of birth, address, city,
     * state, zip, and phone number) and initializes the customer's details. If the user
     * cancels at any prompt, the method exits without creating a customer.
     *
     * @return true if user creation is successful or canceled by the user.
     */
    private static boolean createUser(){
        System.out.println("You will be prompted on the information needed to create a new user");

        String name = verifyNewCustomerInput("name"); //get name
        if (name == null) return true; //exit or main menu

        String dob = verifyNewCustomerInput("dob"); //get dob
        if (dob == null) return true; //exit or main menu

        String address = verifyNewCustomerInput("address"); //get address
        if (address == null) return true; //exit or main menu

        String city = verifyNewCustomerInput("city"); //get city
        if (city == null) return true; //exit or main menu

        String state = verifyNewCustomerInput("state"); //get state
        if (state == null) return true; //exit or main menu

        String zip = verifyNewCustomerInput("zip"); //get zip
        if (zip == null) return true; //exit or main menu

        String fullAddress = "\"" + address + ", " + city + ", " + state + " " + zip + "\"";

        String phone = verifyNewCustomerInput("phone"); //get phone number
        if (phone == null) return true; //exit or main menu

        totalCustomers++;

        //create user.
        Dictionary <String, Account> accounts = new Hashtable<>(); //create accounts
        Customer customer = new Customer(); //create customer
        //create checking account and add to accounts list
        int accountNum = totalCustomers + 999;
        Account checking = new Checking(accountNum, 0, customer);
        accounts.put("checking", checking);
        accountList.put(accountNum, customer);
        //create savings account and add to accounts list
        accountNum = totalCustomers + 1999;
        Account saving = new Saving(accountNum, 0, customer);
        accounts.put("saving", saving);
        accountList.put(accountNum, customer);
        //create credit account and add to accounts list
        int creditScore = (int)(Math.random()* 440) +  380; 
        accountNum = totalCustomers + 2999;
        Credit credit = new Credit(accountNum, 0, customer, 0);
        accounts.put("credit", credit);
        accountList.put(accountNum, customer);

        if(creditScore <= 580) credit.setMax((int) (Math.random()*600) + 100);
        else if(creditScore<= 669) credit.setMax((int) (Math.random()*4300) + 700);
        else if(creditScore<= 739) credit.setMax((int) (Math.random()*2500) + 5000);
        else if(creditScore<= 799) credit.setMax((int) (Math.random()*8500) + 7500);
        else credit.setMax((int) (Math.random()*9001) + 1600);
        
        //set the user information
        int id = totalCustomers;
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];

        customer.setId(id);
        customer.setAccounts(accounts);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setDOB(dob);
        customer.setAddress(fullAddress);
        customer.setPhoneNumber(phone);
        customer.setCreditScore(creditScore);
        customer.setPassword(id);
        //add customer to corresponding lists
        customerList.put(name, customer);
        names.add(name);
        
        System.out.println("User " + customer.getName() + " added");
        return true;
    }


    /**
     * Prompts the user to enter information for a specified input type and verifies the input.
     * 
     * Allows the user to exit or return to the main menu by entering specific commands.
     * 
     * @param inputType the type of information requested (e.g., "name", "address")
     * @return the verified input as a String, or null if the user chooses to exit or go to the main menu
     */
    private static String verifyNewCustomerInput(String inputType){
        String input = "";
        while (input == ""){ //verify we get an input
            System.out.println("Please enter the " + inputType + " for the account (exit (e) or main menu (m))"); //input or action
            input = sc.nextLine();
            switch (input){
                case ("e"):
                case("exit"): //action
                    exit = true;
                    mainMenu = true;
                    return null;
                case("m"):
                case("main"):
                case("main menu"): //action
                    mainMenu = true;
                    return null;

                case "name":
                    // Validate name format (first and last name with letters and optional space between)
                    if (input.matches("[A-Za-z]+(\\s[A-Za-z]+)*")) {
                        return input; // Valid name format (e.g., "John Doe")
                    } else {
                        System.out.println("Invalid name format. Please enter a valid name.");
                    }
                    break;

                case "dob":
                    // format = 05-Mar-1990
                    if (input.matches("\\d{2}-[A-Za-z]{3}-\\d{4}")) {
                        return input; // Valid DOB format
                    } else {
                        System.out.println("Invalid date of birth format. Please use dd-MMM-yyyy (e.g., 05-Mar-1990).");
                    }
                    break;

                case "address":
                    if (input.matches("\\d+\\s+\\w+(\\s\\w+)*")) {
                        return input; // Valid street address
                    } else {
                        System.out.println("Invalid street address format. Please try again.");
                    }
                    break;

                case "city":
                    if (input.matches("[A-Za-z]+(\\s[A-Za-z]+)*")) {
                        return input; // Valid city
                    } else {
                        System.out.println("Invalid city format. Please try again.");
                    }
                    break;

                case "state":
                    if (input.matches("[A-Z]{2}")) {
                        return input; // Valid state
                    } else {
                        System.out.println("Invalid state format. Please enter a two-letter state abbreviation.");
                    }
                    break;

                case "zip":
                    if (input.matches("\\d{5}(-\\d{4})?")) {
                        return input; // Valid ZIP code
                    } else {
                        System.out.println("Invalid ZIP code format. Please try again.");
                    }
                    break;

                case "phone":
                    if (input.matches("\\(\\d{3}\\) \\d{3}-\\d{4}")) {
                        return input; // Valid phone number
                    } else {
                        System.out.println("Invalid phone number format. Please try again.");
                    }
                    break;

                default:
                    System.out.println("Unknown input type. Please try again.");
                    break;
            }
        }
        return null;
    }

    /**
     * Prompts the user for their first and last name to identify the customer. 
     * It allows for options to exit the program or return to the main menu. 
     * If the customer exists in the database, it welcomes them; otherwise, 
     * it prompts the user to try again.
     *
     * @return The full name of the customer if found in the database; 
     *         otherwise, returns null.
     */
    private static String getCustomer(){
        System.out.println("Please provide a first and last name  (exit (e) or main menu (m))");
        String name = sc.nextLine(); //get name of customer or action to perform
        while(name.equals("")) name = sc.nextLine();
        switch(name){
            case ("e"):
            case("exit"):
                exit = true;
                return null;
            case ("m"):
            case("main"):
            case("main menu"):
                mainMenu = true;
                return null;
            default:
                if (customerList.get(name) == null){ //if the customer doesn't exist rerun the function
                System.out.println("Cannot find user in the database\nEnsure that the spelling is correct or that the user is in the database");
                return getCustomer();
                }
                else{ //else customer does exist in the bank
                //System.out.println("Welcome, " + customerList.get(name).getName()); //greets customer with full name
                return name;
                }
        }
    }

        /** 
     * Prompts the user to enter an account number, allowing for 
     * the option to exit or return to the main menu. The method 
     * validates the input and returns the account number if valid. 
     * If not, the user is prompted to enter the number again.
     *
     * @return The valid account number entered by the user, or 0 if the user 
     * chooses to exit or return to the main menu.
     */
    private static int getAccountNumber(){
        System.out.println("Enter account number: (exit (e) or main(m))"); 
        String number = sc.nextLine(); //get input
        while(number.equals("")) number = sc.nextLine();
        switch (number){
            case("e"):
            case("exit"): //actions
                mainMenu = true;
                exit = true;
                return 0;
            case("m"):
            case("main"):
            case("main menu"): //actions
                mainMenu = true;
                return 0;
            default: //actual number or invalid input
                try{
                    int num = Integer.parseInt(number); //if it cant be an int, throw error
                    if (accountList.get(num) == null) throw new Exception(); //if it is int but isnt an account num, throw error
                    return num; //it is an int valid account
                }catch (Exception e){
                    System.out.println("Account of number " + number + " was not found."); //not found, try again
                    return getAccountNumber();
                }
        }
    }

    /**
     * Prompts the user to specify which account to perform a transaction on, 
     * including the account type or account number. It also provides options 
     * to exit or return to the main or user menu.
     *
     * @return A string representing the account type or account number specified by the user.
     */
    private static String getAccount(){
        System.out.printf("Please specify which account you would like to perform this transaction to, include the type or the account number (exit (e) or main menu (m) or user menu (u))\n"); //get account number or account type to perform transaction for or action to perform
        String type = sc.nextLine();
        while(type.equals ("")) type = sc.nextLine();
        return type;
    }
}


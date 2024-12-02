package src.transaction;

/**
 * Acts as a proxy for accessing confidential transactions.
 * This class controls access to the RealTransactionsAccess class,
 * ensuring that only users with the appropriate role (e.g., "Manager") can
 * perform sensitive operations.
 * @author Hannah Ayala
 */
public class TransactionsAccessProxy implements TransactionsAccess {
    private RealTransactionsAccess realTransactionsAccess;
    private String userRole;

     /**
     * Constructs a new TransactionsAccessProxy with the specified user role.
     *
     * @param userRole The role of the user attempting to access confidential transactions.
     */
    public TransactionsAccessProxy(String userRole) {
        this.userRole = userRole;
    }


    /**
     * Attempts to perform confidential transactions.
     * 
     * If the user has a "Manager" role, access is granted, and the request
     * is forwarded to the RealTransactionsAccess class. Otherwise,
     * access is denied, and a message is displayed.
     */
    public void performConfidentialTransactions() {
        if ("Manager".equalsIgnoreCase(userRole)) {
            if (realTransactionsAccess == null) {
                realTransactionsAccess = new RealTransactionsAccess();
            }
            System.out.println("Proxy: Access granted. Forwarding request to RealTransactionsAccess.");
            realTransactionsAccess.performConfidentialTransactions();
        } else {
            System.out.println("Proxy: Access denied. Only managers can view the confidential Transactions.");
        }
    }
}
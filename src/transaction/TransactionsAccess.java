package src.transaction;

/**
 * Represents an interface for accessing and performing confidential transactions.
 * This interface defines a method that must be implemented by any class
 * wishing to perform sensitive or confidential transactional operations.
 * @author Hannah Ayala
 */
public interface TransactionsAccess {
    
    /**
     * Performs a confidential transaction.
     * This method should contain the logic for handling secure transactions
     * that require special permissions or access controls.
     */
    void performConfidentialTransactions();
}

package src;
/**
 * The Custom Exception class to be used in the Read and Update CSV Files
 * @author Gerardo Sillas
 */
public class CSVException extends Exception {
    /**
     *  constructor used to when catching errors
     * @param message the message tht is given when error is caught
     * @param exception the error itself
     */
    public CSVException(String message, Throwable exception) {
        super(message,exception);
    }
}
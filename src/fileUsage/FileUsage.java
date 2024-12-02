package src.fileUsage;

import src.CSVException;

/**
 * interface to be used by all classes that interact with Files
 * @author Gerardo Sillas
 */
public interface FileUsage {
    /**
     * abstract class to be used by classes that implement this inteface
     * the input is a string that specifies the file path that is going to be "Used".
     * @param filePath
     * @throws CSVException 
     */
    void Use(String filePath) throws CSVException;
}

package src.fileUsage.Logger;
import java.io.File; 
import java.io.IOException;

import src.fileUsage.FileUsage;

import java.io.FileWriter;

/**
 * The Files class provides methods to create a text file and write content to it.
 */
public class Logger implements FileUsage{
  /**
   * Static instance of the object, to create a Singleton Design Pattern
   */
  private static Logger instance;
  
  /**
   * The name of the file (without extension).
   */
  public String fileName;

  /**
   * Default constructor for the Files class.
   */
  private Logger(){
  }

  /**
   * Allow other classes to access the static object
   */
  public static Logger getInstance(){
    if (instance == null) {
        instance = new Logger();
    }
    return instance;
  }


  /**
   * Creates a new text file with the specified name.
   * If the file already exists, it notifies the user.
   */
  public void setUp(String fileName){
    try {
      this.fileName = fileName;
      File file = new File(fileName + ".txt");
      if (file.createNewFile()) {
        System.out.println("File created: " + file.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /**
   * Writes the provided input to the file. 
   * Appends the text to the file if it already exists.
   * 
   * @param input The text to be written to the file.
   */
  public void Use(String input){
    try {
      FileWriter myWriter = new FileWriter(fileName + ".txt", true);
      myWriter.write(input + "\n");
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}

// This class contains sign up and login authentication functions for teachers.
// The sign up function is not used at the moment. If this simple sign up function is used 
// then any one can sign up as a teacher and have access to restricted content.

package de.buw.se;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class DataStoreCsv {
  // Declaring an excel file as a storage space for ID's and Passwords...
  private static final String FILE_NAME = "src/main/resources/Data1.csv";

  // Adding a new teacher... Method is NOT USED...
  public static void SignUp(String Identity, String Password) {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME), 
        StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);) 
    {
      csvPrinter.printRecord(Identity, Password);
      csvPrinter.flush();
    } catch (IOException e) {
      e.printStackTrace();      
    }
  } 
  
  // Authentication function for teachers...
  public static boolean LogInAuthentication(String Identity, String Password) throws IOException {
      BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
      String line;
      while ((line = reader.readLine()) != null) {
          String[] parts = line.split(",");
          if (parts.length == 2) {
              String storedUsername = parts[0];
              String storedPassword = parts[1];
              // The following code performs the authentication process...
              if (storedUsername.equals(Identity) && storedPassword.equals(Password)) {
                  reader.close();
                  return true; 
              }
          }
      }
      reader.close();
      return false; 
  }
}

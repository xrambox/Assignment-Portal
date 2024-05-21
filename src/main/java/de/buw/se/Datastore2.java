// This class contains sign up and login authentication functions for students.
package de.buw.se;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Datastore2 {
  // Declaring an excel file as a storage space for ID's and Passwords...
  private static final String FILE_NAME = "src/main/resources/Data.csv";

  // Authentication function for students... This method is slightly different from the authentication
  // method for teachers. It is because the student's sign up method also generates a number (integer) 
  // automatically when a student signs up starting from 1 and increases one by one. So, This method 
  // actually performs the authentication function on the first 2 columns and of the excel file and 
  // if successful returns the integer or "UserID".
  public static int LogInAuthentication(String identity, String password) throws IOException {
	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	// This argument splits the data into three parts (Identity, Password, ID)
	            String[] parts = line.split(",");
	            if (parts.length >= 3) { 
	                String storedUsername = parts[0]; 
	                String storedPassword = parts[1];
	                // The following argument performs the authentication process...
	                if (storedUsername.equals(identity) && storedPassword.equals(password)) {
	                	// If successful it returns the user ID...
	                    return Integer.parseInt(parts[2]); 
	                }
	            }
	        }
	        // Returns -1 if authentication fails...
	        return -1;
	    }
	}
  
  // Adding a new student to the excel data sheet... This method stores the ID's/Passwords and also assigns 
  // an integer starting from 1 to each student.
  public static void SignUp(String identity, String password) {
      try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME),
            StandardOpenOption.APPEND, StandardOpenOption.CREATE)) 
            {
          // If the file is empty, it writes the headers to the file...
          if (Files.size(Paths.get(FILE_NAME)) == 0) {
              writer.write("Identity,Password,Id\n");
          }
          // This line calls the AssignUserID() method which returns the next available ID...
          int ID = AssignStudentID();
          // Writes the new user's information to the CSV file...
          String record = String.format("%s,%s,%d%n", identity, password, ID);
          writer.write(record);
            } catch (IOException e) {
                e.printStackTrace();
      }
  }
  
  // This is the actual method that does the job of assigning and writing an integer in front of each student
  // as they sign up...
  private static int AssignStudentID() {
      // Initializing the next ID
      int nextId = 1;
      try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_NAME))) 
      {
          reader.readLine();
          // Reading the remaining lines to determine the next ID
          while (reader.readLine() != null) {
              nextId++;
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return nextId;
  }
}  

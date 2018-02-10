/**
 * This class is used to configure and set up a MYSQL-database connection.
 * For connection, different type of SQL-objects are used together. 
 * Last, this class contains methods for performing specific SQL execute statements.
 * @author      Ruud Visser
 * @version     0.1 (300118)
 */

package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.Scanner;
import java.util.InputMismatchException;

public class DatabaseConnectorJDBC {
private static Connection connection;
private static PreparedStatement preparedStatement;
private static ResultSet resultSet;

private static final String MYSQL_DB_DRIVER_CLASS   = "com.mysql.jdbc.Driver";
private static final String MYSQL_DB_URL            = "jdbc:mysql://127.0.0.1:3306/testdb?useSSL=false";
private static final String MYSQL_DB_USERNAME       = "testuser";
private static final String MYSQL_DB_PASSWORD       = "testuser123";
private static       int    validatedNumber;
private static       Scanner scanner;
        
/**
 * Setting the needed SQL-objects, global variable and input scanner.
 */       
 
public DatabaseConnectorJDBC(){
    
connection          = null;
preparedStatement   = null;
resultSet           = null; 
validatedNumber     = 0;
scanner             = new Scanner(System.in);
}    

/**
 * Switch-method for executing specific query-method. 
 * Switch input is validated with a validation method.
 * @param inputNumber 
 */

public void ExecuteQueryStatement(int inputNumber) {
int selectedStatementNumber = inputNumber;    
    
switch (selectedStatementNumber) {
      case 1:
      //SELECT method
      System.out.println("Print all records in employee table? [1.yes | 2.no]"); 
      checkAndValidateInputNumber(2);
      if (validatedNumber == 1) {
      selectStatementPrintAllRecords();
    } if (validatedNumber == 2) {   
      selectStatementPrintSpecificRecords(); 
      } 
      break;
      case 2:
      //INSERT method
      insertStatementSpecific();
      break;
      case 3:
      //UPDATE method
      updateStatementSpecific();        
      break;
      case 4:
      //DELETE method
      deleteStatementSpecific();      
      break;
      default:
      System.out.println("Given input is not valid. Please try again");
    } 
  }

/**
 * Method for checking and validating specific number input and number range.
 * @param inputRange 
 */


private static void checkAndValidateInputNumber(int inputRange){ 
    int minRangeNumber = 1;
    int maxRangeNumber = inputRange;
    int tempNumber;
    boolean inputNotValidated = true;
    
    do{
    System.out.println("Please type in a number between 1 and " + maxRangeNumber);
    try{
    tempNumber = scanner.nextInt();
    if (tempNumber >= minRangeNumber && tempNumber <= maxRangeNumber ) {
    validatedNumber = tempNumber;
    inputNotValidated = false;
    break;
    } else {
     System.out.println("Invalid number. Please try again");
     continue;
      }
     } catch (InputMismatchException exception){
     System.out.println("Input is not a number. Please try again");
     scanner.next();
     continue;
     }
    } while (inputNotValidated);
}

/**
 *Below are the specific query-method, 
 *used in the switch ExecuteQueryStatement-method.
 */


/**
 *Method used for SELECT statement.
 *Printing all records from the database test table.  
 */

private void selectStatementPrintAllRecords() {
try{   
      connection = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD);    
      String query = "select * from testdb.employees";
      preparedStatement = connection.prepareStatement(query);
      resultSet = preparedStatement.executeQuery();
      System.out.println("");
      System.out.println("Current records in employee table:");
      System.out.println("");
      System.out.println("-----------------------------------------------------------------");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "number", "first name", "last name", "birth date", "gender", "hire date");
      
      while (resultSet.next()){
      String emp_number   = resultSet.getString("EMP_NUMBER"); //INT
      String first_name   = resultSet.getString("FIRST_NAME"); //VARCHAR
      String last_name    = resultSet.getString("LAST_NAME"); //VARCHAR
      String birth_date   = resultSet.getString("BIRTH_DATE"); //DATE
      String gender       = resultSet.getString("GENDER"); //ENUM
      String hire_date    = resultSet.getString("HIRE_DATE"); //DATE

      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", emp_number, first_name, last_name, birth_date, gender, hire_date);
        
      }
      System.out.println("-----------------------------------------------------------------");
      System.out.println(""); 
     }
      catch (SQLException exception) {
      System.out.println("Error while performing method:");
      exception.printStackTrace();
      }     
}

/**
 *Method used for SELECT statement.
 *Printing specific records from the database test table.  
 */

private void selectStatementPrintSpecificRecords() {
boolean settingQueryField = true; 
      String selectedFields =""; 
      String query = "";
       String emp_number = "";
       String first_name ="";
       String last_name ="";
       String birth_date ="";
       String gender ="";
       String hire_date ="";
      
      System.out.println("Structure of employee table is as follows:");
      System.out.println("");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "Field", "Type", "  Null", "Key", " Default", "Extra");   
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "emp_number", "int(10)", "  NO", "PRI", " NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "first_name", "varchar(20)", " YES","", "NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "last_name", "varchar(20)", " YES","", "NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "birth_date", "date", "  YES", "", " NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "gender", "enum(M,F)", "  YES", "", " NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "hire_date", "date", "  YES", "", " NULL", "");
      System.out.println(""); 
      System.out.println("Please type in the field(s) you wish to view. Use spaces to seperate fields");
      while (settingQueryField){
      selectedFields = scanner.nextLine();
      if (!selectedFields.isEmpty()){
      selectedFields = selectedFields.replaceAll(" ", ",");
      query = "select" + "?" + "from testdb.employees";
      settingQueryField = false;
       }
      }
      try{   
      //create preparedstatement  
      connection = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD);    
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, selectedFields);
      resultSet = preparedStatement.executeQuery();
 
      System.out.println("Result of SELECT query:");
      System.out.println("");
      System.out.println("-----------------------------------------------------------------");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "number", "first name", "last name", "birth date", "gender", "hire date");
      
      while (resultSet.next()){     //HOE OM TE GAAN MET VELDEN DIE LEEG ZIJN?? ONDERSTAAND WERKT NIET
      if(resultSet.getString("emp_number").isEmpty()) {
      emp_number = resultSet.getString("emp_number");
      } else {emp_number = "";}
      if(resultSet.getString("first_name").isEmpty()) {
      first_name = resultSet.getString("first_name");
      } else {first_name = "";}
      if(resultSet.getString("last_name").isEmpty()) {
      last_name = resultSet.getString("last_name");
      } else {last_name = "";}
      if(resultSet.getString("birth_date").isEmpty()) {
      birth_date = resultSet.getString("birth_date");
      } else {birth_date = "";}
      if(resultSet.getString("gender").isEmpty()) {
      gender = resultSet.getString("gender");
      } else {gender = "";}
      if(resultSet.getString("hire_date").isEmpty()) {
      hire_date = resultSet.getString("hire_date");
      } else {hire_date = "";}
      
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", emp_number, first_name, last_name, birth_date, gender, hire_date);
        
      }
      System.out.println("-----------------------------------------------------------------");
      System.out.println(""); 
     }
      catch (SQLException exception) {
      System.out.println("Error while performing method:");
      exception.printStackTrace();
       }     
}

/**
 *Method used for INSERT statement.
 *Insert a new record in the database test table.  
 */

private void insertStatementSpecific(){
System.out.println("Structure of employee table is as follows:");
      System.out.println("");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "Field", "Type", "  Null", "Key", " Default", "Extra");   
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "emp_name", "int(10)", "  NO", "PRI", " NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "first_name", "varchar(20)", " YES","", "NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "last_name", "varchar(20)", " YES","", "NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "birth_date", "date", "  YES", "", " NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "gender", "enum(M,F)", "  YES", "", " NULL", "");
      System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %n", "hire_date", "date", "  YES", "", " NULL", "");
      System.out.println("");  
      
      System.out.println("Enter your INSERT anwsers for statement below:");
      String insertQuery = "INSERT INTO testdb.employees (emp_number, first_name, last_name, birth_date, gender, hire_date)"
                     + " values (?,?,?,?,?,?)";

      System.out.println("Insert number for new employee: ");

      System.out.println("Insert first name for new employee: ");

      System.out.println("Insert last name for new employee: ");

      System.out.println("Insert birth date for new employee: ");

      System.out.println("Insert gender for new employee: ");

      System.out.println("Insert hire_date for new employee: ");

      try{   
      //create preparedstatement
      connection = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD);    
      preparedStatement = connection.prepareStatement(insertQuery);
      preparedStatement.setString(1, "4");
      preparedStatement.setString(2, "Esther");
      preparedStatement.setString(3, "van der wiel");
      preparedStatement.setString(4, "1960-04-06");
      preparedStatement.setString(5, "F");
      preparedStatement.setString(6, "1987-08-03");

      //Execute preparedstatement
      preparedStatement.executeUpdate();

      System.out.println("INSERT STATEMENT succesfully executed!"); }
      catch (SQLException exception) {
      System.out.println("Error while performing method:");
      exception.printStackTrace();
      }     
} 

/**
 *Method used for UPDATE statement.
 *Updating a specific record from the database test table.  
 */

private void updateStatementSpecific(){

}


/**
 *Method used for DELETE statement.
 *Deleting a specific record from the database test table.  
 */

private void deleteStatementSpecific(){

}


/**
 * General MYSQL-method: check if the JDBC-driver is implemented properly.
 */

private void checkJDBCDriver(){
try {
Class.forName(MYSQL_DB_DRIVER_CLASS);
} 
catch (ClassNotFoundException exception) {
System.out.println("MYSQL JDBC Driver not found!");
exception.printStackTrace();
 }
System.out.println("MYSQL JDBC Driver is found and loaded!");
}

/**
 * General MYSQL-method: check if the MYSQL-server is up and running and that a new connection can be made.
 */

private void checkMYSQLConnection(){
try {
connection = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USERNAME, MYSQL_DB_PASSWORD);
} 
catch(Exception exception) {
System.out.println("MYSQL connection could not be set up");
exception.printStackTrace();
  }    
System.out.println("MYSQL connection is set up and ready to be used!");
}  
















}
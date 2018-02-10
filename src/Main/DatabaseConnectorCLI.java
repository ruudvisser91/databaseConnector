/**
 * This class is used for printing the CLI-interface.
 * The class uses switches for navigating between the different sub-interfaces.
 * MYSQL-actions in these sub-interfaces, are provided by the DatabaseConnectionJDBC object.
 * @author      Ruud Visser
 * @version     0.1 (300118)
 */



package Main;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DatabaseConnectorCLI {
    
private static DatabaseConnectorJDBC  JDBCObject; 
private static int validatedNumber; 
private static int menuRangeNumber;
private static Scanner scanner; 

public DatabaseConnectorCLI(){
JDBCObject = new DatabaseConnectorJDBC(); 
validatedNumber = 0;
menuRangeNumber = 0;
scanner         = new Scanner(System.in);

} 

public void printingHomeInterface(){
    
    System.out.println("=====================================");   
    System.out.println("|Menu DatabaseConnector             |"); 
    System.out.println("====================================|"); 
    System.out.println("|Options:                           |"); 
    System.out.println("|         1.Query database          |"); 
    System.out.println("|         2.Server Status           |"); 
    System.out.println("|         3.Manage Server Connection|");
    System.out.println("|         4.Exit                    |");     
    System.out.println("=====================================");    
    System.out.println("");
     
    checkAndValidateInputNumber(4);
    
    switch (validatedNumber) {
      case 1:
      printWhiteSpace();    
      printingQueryInterface();
      break;
      
      case 2:
      printWhiteSpace();     
      printingStatusInterface();    
      break;
      
      case 3:
      printWhiteSpace();     
      //printingConnectionInterface();    
      break;
      
      case 4:
      System.exit(0);
      break;
      default:
      System.out.println("Given input is not valid. Please try again");
    }    
  }    


private void printingQueryInterface(){
        
    System.out.println("==================================================");   
    System.out.println("|Menu DatabaseConnector: Query Database          |"); 
    System.out.println("================================================ |"); 
    System.out.println("|Options for employee information:               |");
    System.out.println("|                                                |");
    System.out.println("|         1.Execute Statement                    |");  
    System.out.println("|         2.Return to main menu                  |");
    System.out.println("=================================================");    
    
    checkAndValidateInputNumber(3);
    
    switch (validatedNumber) {
      case 1:
      printWhiteSpace();    
      printingQueryExecuteStatementInterface();  
      break;
      
      case 2:
      printWhiteSpace();     
      printingHomeInterface(); 
      break;
           
      default:
      System.out.println("Given input is not valid. Please try again");
    }    
    
    
  } 

private void printingStatusInterface(){
    
    System.out.println("==================================================");   
    System.out.println("|Menu DatabaseConnector: Server Status           |"); 
    System.out.println("================================================ |"); 
    System.out.println("|Options:                                        |"); 
    System.out.println("|         1.TEST                                 |"); 
    System.out.println("|         2.TEST                                 |"); 
    System.out.println("|         3.TEST                                 |");
    System.out.println("|         4.Return to main menu                  |");     
    System.out.println("=================================================");     
    
    checkAndValidateInputNumber(4);
    
    switch (validatedNumber) {
      case 1:
      printWhiteSpace();    
      //printingStatusX();
      break;
      
      case 2:
      printWhiteSpace();     
      //printingStatusY();   
      break;
      
      case 3:
      printWhiteSpace();     
      //printingStatusZ();   
      break;
      
      case 4:
      printWhiteSpace();     
      printingHomeInterface();
      break;
      
      default:
      System.out.println("Given input is not valid. Please try again");
    }    
  } 

private void printingManageConnectionInterface(){
    
    System.out.println("==================================================");   
    System.out.println("|Menu DatabaseConnector: Manage Server Connection|"); 
    System.out.println("================================================ |"); 
    System.out.println("|Options:                                        |"); 
    System.out.println("|         1.TEST                                 |"); 
    System.out.println("|         2.TEST                                 |"); 
    System.out.println("|         3.TEST                                 |");
    System.out.println("|         4.Return to main menu                  |");     
    System.out.println("=================================================");    
    
   checkAndValidateInputNumber(4);
    
    switch (validatedNumber) {
      case 1:
      printWhiteSpace();    
      //printingManageX();
      break;
      
      case 2:
      printWhiteSpace();     
      //printingManageY();    
      break;
      
      case 3:
      printWhiteSpace();     
      //printingManageZ();   
      break;
      
      case 4:
      printingHomeInterface();
      break;
      default:
      System.out.println("Given input is not valid. Please try again");
    }    
    
  } 

//QueryInterface methods
private void printingQueryExecuteStatementInterface() {
    System.out.println("Please type the number for the statement you would like to peform");
    System.out.println("Option 1: SELECT STATEMENT - Option 3: UPDATE STATEMENT");
    System.out.println("Option 2: INSERT STATEMENT - Option 4: DELETE STATEMENT");
    
    checkAndValidateInputNumber(4);    

switch (validatedNumber) {
      case 1:
      printWhiteSpace();    
      JDBCObject.ExecuteQueryStatement(1); //SELECT STATEMENT
      break;
      
      case 2:
      JDBCObject.ExecuteQueryStatement(2); //INSERT STATEMENT  
      break;
      
      case 3:
      JDBCObject.ExecuteQueryStatement(3); //UPDATE STATEMENT 
      break;
      
      case 4:
      JDBCObject.ExecuteQueryStatement(4); //DELETE STATEMENT
      break;
      default:
      System.out.println("Given input is not valid. Please try again");
    }    
}  

//StatusInterface methods


//ManageConnectionInterface methods


//Standard Methods for this class
    
private void printWhiteSpace(){
    for (int i=0; i < 50; ++i) System.out.println();
    }

private static void checkAndValidateInputNumber(int inputRange){ 
    int minRangeNumber = 1;
    int maxRangeNumber = inputRange;
    int tempNumber;
    boolean inputNotValidated = true;
    
    do{
    System.out.println("Please type in a number between 1 and 4");
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





 














}

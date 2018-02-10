package Main;

public class DatabaseConnectorMain {
       
    private static DatabaseConnectorCLI   CLIObject;

                
public static void main(String[] args) {
     /**
     *Initiating the needed classes as objects 
    */
    CLIObject  = new DatabaseConnectorCLI();  
    CLIObject.printingHomeInterface();
            
    }
    
}

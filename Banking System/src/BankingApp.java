import java.sql.*;
import java.util.*;

public class BankingApp {

	private static final String url = "jdbc:mysql://localhost:3306/banking_system";
	private static final String username = "root";
	private static final String password = "Ratankumar@27";
	
	public static void main(String[] args) {
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Connection connection = DriverManager.getConnection(url, username, password); 
			Scanner sc = new Scanner(System.in);
			
			User user = new User(connection, sc);
			Accounts accounts = new Accounts(connection, sc);
			AccountsManager accountsManager = new AccountsManager(connection, sc);
			
			String email;
			long acc_number;
			
			System.out.println(" ---- WELCOME TO BAKING SYSTEM ----");
			System.out.println("''''''''''''''''''''''''''''''''''''");
			
			while(true) {
				System.out.println();
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Exit");
				
				int choice1 = sc.nextInt();
				
				switch(choice1) {
				    case 1 : user.register();
				    break;
				    
				    case 2 : email = user.login();
				    		
				            if(email!=null) {
				            	System.out.println();
				            	System.out.println("User Logged In!");
				            	
				            	if(!accounts.accountExist(email)) {
				            		System.out.println();
				            		System.out.println("1. Open New Bank Account");
				            		System.out.println("2. Exit");
				            		
				            		if(sc.nextInt()==1) {
				            		    acc_number = accounts.openAccount(email);
				            		    System.out.println("Account Created Successfully!!");
				            		    System.out.println("Your Account Number: "+ acc_number);
				            		}
				            		else {
				            			break;
				            		}
				            	}
				            	
				            	acc_number = accounts.getAccountNum(email);
				            	int choice2 =0;
				            	
				            	while(choice2!=5) {
				            		System.out.println();
				            		System.out.println("1. Debit Money");
				            		System.out.println("2. Credit Money");
				            		System.out.println("3. Transfer Money");
				            		System.out.println("4. Check Balance");
				            		System.out.println("5. Log Out"); 
				            		System.out.println();
				            		System.out.println("Enter Your Choice:");
				            		choice2 = sc.nextInt();
				            		
				            		switch(choice2) {
				            		     case 1 : accountsManager.debitAmount(acc_number);
				            		     break;
				            		     
				            		     case 2 : accountsManager.creditAmount(acc_number);
				            		     break;
				            		     
				            		     case 3 : accountsManager.tansferMoney(acc_number);
				            		     break;
				            		        
				            		     case 4 :  accountsManager.getBalance(acc_number);  
				            		     break;
				            		     
				            		     case 5 : break;
				            		     
				            		     default : System.out.println("Enter valid choice");
				            		     break;
				            		}
				            	}
				               
				            }
				            else {
				            	System.out.println("Incorrect Email or Password!!");
				            }
				    
				    break;
				    
				    case 3 : System.out.println("THANK YOUR FOR USING BANKING SYSYTEM!!");
				             System.out.println("EXITING SYSTEM!!");
				             return;
				             
				    default : System.out.println("Enetr valid choice");
				    break;
				}
			}
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}

}

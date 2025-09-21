import java.sql.*;
import java.util.Scanner;

public class Accounts {
	
	private Connection connection;
	
	private Scanner sc;
	
	public Accounts(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
	}
	
	
	public long openAccount(String email) {
		if(!accountExist(email)) {
		   String query = "insert into accounts(account_num, full_name, email, balance, security_pin) values(?, ?, ?, ?, ?)";
		   sc.nextLine();
		   
		   System.out.println("Enter your Full Name: ");
		   String name = sc.nextLine();
		   
		   System.out.println("Enter Initial Amount: ");
		   long amnt = sc.nextLong();
		   
		   sc.nextLine();
		   
		   System.out.println("Enter Security Pin: ");
		   String pin = sc.nextLine();
		   
		   try {
	
			   long accountnum = generateAccountnum();
			   
			   PreparedStatement preparedstatement = connection.prepareStatement(query);
			   preparedstatement.setLong(1, accountnum);
			   preparedstatement.setString(2, name);
			   preparedstatement.setString(3, email);
			   preparedstatement.setLong(4, amnt);
			   preparedstatement.setString(5, pin);
			   
			   int rowsAffect = preparedstatement.executeUpdate();
			   
			   if(rowsAffect > 0) {
				   return accountnum;
				   
			   }
			   else {
	
				   throw new  RuntimeException("Account Creating Failed!!");
			   }
			   
		   }
		   catch(SQLException e ) {
			   System.out.println(e.getMessage());
		   }
	    }
		
		throw new RuntimeException("Account Already Exist!!");
	}
	
	
	public long getAccountNum(String email) {
		String query = "select account_num from accounts where email = ?";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setString(1, email);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				return resultset.getLong("account_num");
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		throw new RuntimeException ("Account Number doesnt exist!!"); 
 	} 
	
	
	
	
	private long generateAccountnum() {
		String query = "select account_num from accounts order by account_num desc limit 1";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if(resultset.next()) {
				long lastacc = resultset.getLong("account_num");
				return lastacc+1;
			}
			else{
				return 10000100; 
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return 10000100;
	}
	
	
	
	public boolean accountExist(String email) {
		
		String query = "select account_num from accounts where email = ?";
		
		try{
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setString(1, email);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
		return false;
	}

}

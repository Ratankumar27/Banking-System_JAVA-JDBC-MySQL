import java.sql.*;
import java.util.*;

public class AccountsManager {

	private Connection connection;
	private Scanner sc;
	
	AccountsManager(Connection connection, Scanner sc){
		this.connection = connection;
		this.sc= sc;
	}
	
	
	
	public void tansferMoney(long senderAccNum) throws SQLException {
		sc.nextLine();
		System.out.println("Enter Reciever Account Number: ");
		long recAccNum = sc.nextLong();
		System.out.println("Enter Amount to Transfer:");
		double amnt = sc.nextDouble();
		sc.nextLine();
		System.out.println("Enter Security PIN:");
		String pin = sc.nextLine();
		
		try {
			connection.setAutoCommit(false);
			
			if(senderAccNum!=0 && recAccNum!=0) {
				String query = "Select * from accounts where account_num= ? and security_pin = ?";
				PreparedStatement preparedstatement = connection.prepareStatement(query);
				preparedstatement.setLong(1, senderAccNum);
				preparedstatement.setString(2, pin);
				ResultSet resultset = preparedstatement.executeQuery();
				
				if(resultset.next()) {
					double bal = resultset.getDouble("balance");
					
					if(bal>=amnt) {
						String deb_query="update accounts set balance = balance-? where account_num = ?";
						String cred_query="update accounts set balance = balance+? where account_num = ?";
						PreparedStatement preparedstatement1 = connection.prepareStatement(deb_query);
						PreparedStatement preparedstatement2 = connection.prepareStatement(cred_query);
						preparedstatement1.setDouble(1, amnt);
						preparedstatement1.setLong(2, senderAccNum);
						preparedstatement2.setDouble(1, amnt);
						preparedstatement2.setLong(2, recAccNum);
						int rowsAffected1 =  preparedstatement1.executeUpdate();
						int rowsAffected2 =  preparedstatement2.executeUpdate();
						
						if(rowsAffected1>0 && rowsAffected2>0) {
							System.out.println("Transaction Successfull!!");
							connection.commit();
							connection.setAutoCommit(true);
							return;
						}
						else {
							System.out.println("Transaction Failed!!");
							connection.rollback();
							connection.setAutoCommit(true);
						}
					}
				    else {
					  System.out.println("Insufficient Balance!!");
				    }
				}
				else {
					System.out.println("Invalid PIN!!");
				}
			}
			else {
				System.out.println("Invalid Account Number");
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		connection.setAutoCommit(true);
	}
	
	
	
	
	
	public void debitAmount(long accNum)  throws SQLException {
		sc.nextLine();
	    System.out.println("Enter Amount to Debit:");
	    double amount = sc.nextDouble();
	    
	    sc.nextLine();
	    
	    System.out.println("Enter Security Pin:");
	    String pin = sc.nextLine();
	    
	    try {
	    	connection.setAutoCommit(false);
	    	if(accNum!=0) {
	    		String query = "select * from accounts where account_num=? and security_pin = ?";
	    		PreparedStatement preparedstatement = connection.prepareStatement(query);
	    		preparedstatement.setLong(1, accNum);
	    		preparedstatement.setString(2, pin);
	    		ResultSet resultset = preparedstatement.executeQuery();
	    		if(resultset.next()) {
	    			double bal = resultset.getDouble("balance");
	    			if(amount<= bal) {
	    				String deb_query = "update accounts set balance = balance- ? where account_num=?";
	    				PreparedStatement preparedstatement1 = connection.prepareStatement(deb_query);
	    				preparedstatement1.setDouble(1, amount);
	    				preparedstatement1.setLong(2, accNum);
	    				int rowsAffected = preparedstatement1.executeUpdate();
	    				if(rowsAffected > 0) {
	    					System.out.println("Amount "+amount+" Debited Successfully!!");
	    					connection.commit();
	    					connection.setAutoCommit(true);
	    					return;
	    				}
	    				else {
	    					System.out.println("Transaction Failed!!");
	    					connection.rollback();
	    					connection.setAutoCommit(true);
	    				}	    				
	    			}
	    			else {
	    				System.out.println("Insufficient Balance!!");
	    			}    			
	    		}
	    		else {
	    			System.out.println("Invalid PIN!!");
	    		}
	    		
	    	}
	    	else {
	    		System.out.println("invalid Account Number!!");
	    	}
	    	
	    }
	    catch(SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    
	    connection.setAutoCommit(true);
	}
	
	
	
	
	
	public void creditAmount(long acc_num) throws SQLException{
		sc.nextLine();
		System.out.println("Enter Amount");
		double amount = sc.nextDouble();
		
		sc.nextLine();
		System.out.println("Enter Security PIN");
		String pin = sc.nextLine();
		
		try {
			connection.setAutoCommit(false);
			String query = "select * from accounts where account_num = ? and security_pin=?";
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setLong(1, acc_num);
			preparedstatement.setString(2, pin);
			ResultSet resultset = preparedstatement.executeQuery();
			
			if(resultset.next()) {
				String cred_query = "update accounts set balance = balance + ? where account_num = ?";
				PreparedStatement preparedstatement1 = connection.prepareStatement(cred_query);
				preparedstatement1.setDouble(1, amount);
				preparedstatement1.setLong(2, acc_num);
				int rowsAffected = preparedstatement1.executeUpdate();
				
				if(rowsAffected > 0 ) {
					System.out.println("Rs."+ amount + " Credited Successfully!!");
					connection.commit();
					connection.setAutoCommit(true);
					return;
				}
				else {
					System.out.println("Transaction Failed!!");
					connection.rollback();
					connection.setAutoCommit(true);
				}
			}
			else {
				System.out.println("Invalid PIN!!");
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		connection.setAutoCommit(true);
	}
	
	
	
	
	
	public void getBalance(long acc_num) throws SQLException {
		sc.nextLine();
		System.out.println("Enter Security PIN");
		String pin = sc.nextLine();
		try {
			String query = "Select * from accounts where account_num = ? and security_pin = ?";
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setLong(1, acc_num);
			preparedstatement.setString(2, pin);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				double bal = resultset.getDouble("balance");
				System.out.println("Balance: "+bal);
			}
			else {
				System.out.println("Invalid PIN!!");
			}
		}
		catch(SQLException e) {
		   System.out.println(e.getMessage());	
		}
		
	}
}

import java.sql.*;
import java.util.*;
public class User {
	
	private Connection connection;
	
	private Scanner sc;
	
	public User(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.sc = scanner;
	}
	
	public void register() {
		sc.nextLine();
	    System.out.println("Enter Full Name: ");
	    String name = sc.nextLine();
	    System.out.println("Email: ");
	    String email = sc.nextLine();
	    System.out.println("Password: ");
	    String password = sc.nextLine();
	    
	    if(userExist(email)) {
	    	System.out.println("User already exists!!");
	    	return;
	    }
	    
	    String register_query = "Insert into user(full_name, email, password) values(?, ?, ?)";
	    try {
	    	
			PreparedStatement preparedstatement = connection.prepareStatement(register_query);
			preparedstatement.setString(1, name);
			preparedstatement.setString(2, email);
			preparedstatement.setString(3, password);
			
			int rowsAffect = preparedstatement.executeUpdate();
			
			if(rowsAffect > 0) {
				System.out.println("Registration Successfull!!");
			}
			else {
				System.out.println("Registration failed!!");
			}
	    }
	    catch(SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    
	    
	}
	
	
	//login method that returns email if the user matches with email and password
	public String login() {
		sc.nextLine();
		
		System.out.println("Enter email: ");
		String email = sc.nextLine();
		System.out.println("DEBUG: Email entered -> " + email);
		
		
		System.out.println("Enter password: ");
		String password = sc.nextLine();
		System.out.println("DEBUG: Password entered -> " + password);
		
		String login_query = "select * from `user` where email =? and password = ?";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(login_query);
			preparedstatement.setString(1, email);
			preparedstatement.setString(2, password);
			ResultSet resultset = preparedstatement.executeQuery();
			
			if(resultset.next()) {
				return email;
			}
			else {
				return null;
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	

	
	public boolean userExist(String email) {
		String query = "select * from user where email = ?";
		try {
		
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

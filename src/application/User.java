package application;

public class User {
	String username;
	String email;
	String password;
	String role;
	
	public User(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public User(String username, String email, String password, String role) {
		if (username != null) {
			this.username = username;			
		} 
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
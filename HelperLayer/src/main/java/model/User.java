package model;

public class User {

	private int userId;
	private String msisdn;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private int passChangeCode;
	
	public User() {}
	
	public User(int userId,String msisdn, String name, String lastName, String email, String password, int passChangeCode) {
		this.userId = userId;
		this.msisdn = msisdn;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.passChangeCode = passChangeCode;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPassChangeCode() {
		return passChangeCode;
	}

	public void setPassChangeCode(int passChangeCode) {
		this.passChangeCode = passChangeCode;
	}
	
	
	
	
	
	
	
}

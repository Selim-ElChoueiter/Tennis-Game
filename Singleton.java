package application;

public class Singleton {
	private static Singleton instance=null;
	private User user;
	
	private Singleton() {}
	
	
	public static Singleton getInstance() {
		
		if (instance == null) {
			instance = new Singleton();
			
		}
		
		return instance;
	}

	public void createUser(String name,String email,String password,String dateofbirth) {
		//logs in the user
		this.user=new User(name,email,password,dateofbirth);
	}
	
	public boolean checkUser(String email,String password) {
		//check user login info
		if(this.user!=null) {
			if(this.user.getEmail().equals(email) && this.user.getPassword().equals(password)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public User getUser() {
		if(this.user!=null) {
			return this.user;
		}else {
			return null;
		}
	}
	
	public void saveUser(String Email,String Password) {
		this.user.setEmail(Email);
		this.user.setPassword(Password);
	}
	
	public void logout() {
		this.user.setDateofbirth(null);
		this.user.setName(null);
		this.user.setEmail(null);
		this.user.setPassword(null);
		this.user.setHighscoreSolo(0);
		this.user.setBackhandMulti(0);
		this.user.setBackhandSolo(0);
		this.user.setForehandMulti(0);
		this.user.setForehandSolo(0);
		this.user.setHighestmissedSolo(0);
		this.user.setLossesMulti(0);
		this.user.setWinsMulti(0);
		this.user=null;
	}
}

package application;

public class User {

	private String name,email,password,dateofbirth;
	private int highscoreSolo,forehandSolo,backhandSolo,highestmissedSolo;
	private int forehandMulti,backhandMulti,winsMulti,lossesMulti,playedMulti;
	
	
	public User(String name, String email, String password, String dateofbirth) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.dateofbirth = dateofbirth;
		this.highscoreSolo=0;
		this.forehandSolo=0;
		this.backhandSolo=0;
		this.highestmissedSolo=0;
		this.forehandMulti=0;
		this.backhandMulti=0;
		this.backhandMulti=0;
		this.winsMulti=0;
		this.lossesMulti=0;
		this.playedMulti=0;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public String getDateofbirth() {
		return dateofbirth;
	}


	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}


	public int getHighscoreSolo() {
		return highscoreSolo;
	}


	public void setHighscoreSolo(int highscoreSolo) {
		this.highscoreSolo = highscoreSolo;
	}


	public int getForehandSolo() {
		return forehandSolo;
	}


	public void setForehandSolo(int forehandSolo) {
		this.forehandSolo = forehandSolo;
	}


	public int getBackhandSolo() {
		return backhandSolo;
	}


	public void setBackhandSolo(int backhandSolo) {
		this.backhandSolo = backhandSolo;
	}


	public int getHighestmissedSolo() {
		return highestmissedSolo;
	}


	public void setHighestmissedSolo(int highestmissedSolo) {
		this.highestmissedSolo = highestmissedSolo;
	}


	public int getForehandMulti() {
		return forehandMulti;
	}


	public void setForehandMulti(int forehandMulti) {
		this.forehandMulti = forehandMulti;
	}


	public int getBackhandMulti() {
		return backhandMulti;
	}


	public void setBackhandMulti(int backhandMulti) {
		this.backhandMulti = backhandMulti;
	}


	public int getWinsMulti() {
		return winsMulti;
	}


	public void setWinsMulti(int winsMulti) {
		this.winsMulti = winsMulti;
	}


	public int getLossesMulti() {
		return lossesMulti;
	}


	public void setLossesMulti(int lossesMulti) {
		this.lossesMulti = lossesMulti;
	}


	public int getPlayedMulti() {
		return playedMulti;
	}


	public void setPlayedMulti(int playedMulti) {
		this.playedMulti = playedMulti;
	}



	
	
}

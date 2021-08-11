package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ProfileController {
	@FXML private Text userName;
	
	@FXML private Text userDate;
	
	@FXML private TextField userEmail;
	
	@FXML private PasswordField userPassword;
	
	@FXML private Text saveStatusFail;
	
	@FXML private Text saveStatusSuccess;
	
	@FXML private Text personName;
	
	@FXML private Text personDate;
	
	@FXML private Text personEmail;
	
	@FXML private Text personPassword;
	
	
	@FXML
	protected void initialize() {
		
		User user=Singleton.getInstance().getUser();
		
		userName.setText(user.getName());
		userDate.setText(user.getDateofbirth());
		userEmail.setText(user.getEmail());
		userPassword.setText(user.getPassword());
		
		personName.setFill(Color.ORANGE);
		personEmail.setFill(Color.ORANGE);
		personDate.setFill(Color.ORANGE);
		personPassword.setFill(Color.ORANGE);
		
		userName.setFill(Color.ORANGE);
		userDate.setFill(Color.ORANGE);
		
		userName.setStyle("-fx-font: 20 arial;");
		userDate.setStyle("-fx-font: 20 arial;");
		
		personName.setStyle("-fx-font: 20 arial");
		personEmail.setStyle("-fx-font: 20 arial");
		personPassword.setStyle("-fx-font: 20 arial");
		personDate.setStyle("-fx-font: 20 arial");
		
	}
	
	
	@FXML
	public void saveUser() {
		if(!userEmail.getText().toString().isEmpty() || ! userPassword.getText().toString().isEmpty()) {
			Singleton.getInstance().saveUser(userEmail.getText().toString(), userPassword.getText().toString());
			saveStatusFail.setText("");
			saveStatusSuccess.setFill(Color.GREEN);
			saveStatusSuccess.setText("Profile Updated Successfully");
		}else {
			saveStatusSuccess.setText("");
			saveStatusFail.setFill(Color.RED);
			saveStatusFail.setText("Please enter all fields");
		}
	}

}

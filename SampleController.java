package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class SampleController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private ComboBox dayofbirthField,monthofbirthField,yearofbirthField;
    
    @FXML
    private Label status;
    
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
    	
    	if(nameField.getText().isEmpty() || nameField.getText().isEmpty() || passwordField.getText().isEmpty() || dayofbirthField.getValue().toString().isEmpty() ||monthofbirthField.getValue().toString().isEmpty() || yearofbirthField.getValue().toString().isEmpty()) {
    		status.setText("Please enter all the field");
    		status.setTextFill(Color.RED);
    	
        }else{
        	
        	if(monthofbirthField.getValue().toString().equals("April") || monthofbirthField.getValue().toString().equals("June") || monthofbirthField.getValue().toString().equals("February") || monthofbirthField.getValue().toString().equals("September") || monthofbirthField.getValue().toString().equals("November")) {
    			if(Integer.parseInt(dayofbirthField.getValue().toString())==31 || (monthofbirthField.getValue().toString().equals("February") && Integer.parseInt(dayofbirthField.getValue().toString())==30)){
    				status.setText("Please enter a valid date of birth");
    				status.setTextFill(Color.RED);
    			}else {
    					status.setTextFill(Color.GREEN);
    					status.setText("Sign up successfull");
    					String name=nameField.getText().toString();
    					String email=emailField.getText().toString();
    					String password=passwordField.getText().toString();
    					String date=dayofbirthField.getValue().toString()+"/"+monthofbirthField.getValue().toString()+"/"+yearofbirthField.getValue().toString();
    					Singleton.getInstance().createUser(name, email, password, date);
    			}
        }else {
        	status.setTextFill(Color.GREEN);
			status.setText("Sign up successful!");
			String name=nameField.getText().toString();
			String email=emailField.getText().toString();
			String password=passwordField.getText().toString();
			String date=dayofbirthField.getValue().toString()+"/"+monthofbirthField.getValue().toString()+"/"+yearofbirthField.getValue().toString();
			Singleton.getInstance().createUser(name, email, password, date);
        }
        

        }
         
    }
}
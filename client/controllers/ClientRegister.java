package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

public class ClientRegister {

    @FXML
    private TextField emailField, usernameField, telephoneField;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private DatePicker dobPicker;

    @FXML
    public void initialize() {
        // Correctly populate Gender ChoiceBox
        genderChoiceBox.getItems().addAll("Male", "Female");
        
        // Optionally, set a default value
        genderChoiceBox.setValue("Male");  
    }

    public void handleSubmit() {
        // Retrieve input values		
		UserLogin login = new UserLogin(usernameField.getText(),"");	
        String email = emailField.getText();
        String username = usernameField.getText();
        String telephone = telephoneField.getText();
        String gender = genderChoiceBox.getValue();
        String dob = (dobPicker.getValue() != null) ? dobPicker.getValue().toString() : "";
		User user = new User(login,email,telephone,gender,dob);

        // Validate input
        if (email.isEmpty() || username.isEmpty() || telephone.isEmpty() || gender == null || dob.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        // Print user details (Replace this with database logic if needed)
        System.out.println("User Registered Successfully!");
        System.out.println("Email: " + email);
        System.out.println("Username: " + username);
        System.out.println("Telephone: " + telephone);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + dob);
    }
}

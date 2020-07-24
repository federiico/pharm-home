package it.univaq.disim.oop.pharmathome.controller;


import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.ConnectionException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;
import it.univaq.disim.oop.pharmathome.domain.Utente;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import it.univaq.disim.oop.pharmathome.view.ViewException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable, DataInitializable<Object>{
	
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button loginButton;
	@FXML
	private Label warningLabel; 

	private ViewDispatcher instance;

	private UserServices userServices;
	
	public LoginController() {
		instance = ViewDispatcher.getInstance();
		BusinessFactory factory = BusinessFactory.getInstance();
		userServices = factory.getUserServices();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		loginButton.disableProperty().bind( //disabilita il bottone di login finchè non inserisci le credenziali
				usernameField.textProperty().isEmpty().
				or(passwordField.textProperty().isEmpty())
		); 
	}
	
	@FXML
	private void loginAction(ActionEvent event) throws ViewException, ConnectionException{
		
		try {
			Utente user = userServices.authenticate(usernameField.getText(), passwordField.getText());
			instance.loggedInView(user);
		}
		catch (UserNotFoundException e) {
			warningLabel.setText("Username e/o password errati");
		}
		catch (BusinessException e) {
			instance.renderError(e);
		}
	}

	@FXML
	private void registerAction(ActionEvent event) throws ViewException {
	
			ViewDispatcher instance = ViewDispatcher.getInstance();
			instance.registrationView();
	}

}
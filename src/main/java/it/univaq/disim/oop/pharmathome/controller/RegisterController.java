package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;
import it.univaq.disim.oop.pharmathome.domain.Utente;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import it.univaq.disim.oop.pharmathome.view.ViewException;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RegisterController implements Initializable, DataInitializable<Utente>{

	@FXML
	private TextField nameField;
	
	@FXML
	private TextField surnameField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField fiscalCodeField;

	@FXML
	private TextField passwordField;

	@FXML
	private ComboBox<String> userTypeComboBox;
	
	@FXML
	private Button registerButton;
	
	private BooleanBinding bindOptions;
	
	private UserServices userServices;
	
	private ViewDispatcher instance;
	
	public RegisterController() {
		
		instance = ViewDispatcher.getInstance();
		BusinessFactory factory = BusinessFactory.getInstance();
		userServices = factory.getUserServices();
	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		userTypeComboBox.getItems().addAll("Paziente","Farmacista","Medico"); //aggiunge le opzioni al menu a tendina
		
		bindOptions = buildBinding(bindOptions).or(userTypeComboBox.valueProperty().isNull()); 
		
		registerButton.disableProperty().bind(bindOptions); //disabilita il bottone "registrami" sulle condizioni di bindOptions
		
	}
	
	@FXML
	private void goBackToLogin(ActionEvent event) {
		
		try {
			ViewDispatcher instance = ViewDispatcher.getInstance();
			instance.loginView(instance.getStage());
		}
		catch(ViewException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void signIn(ActionEvent event) throws ViewException {
		
		Utente user = null;
		
		try {
			user = userServices.register(nameField.getText(), 
											   surnameField.getText(), 
											   fiscalCodeField.getText(),
											   emailField.getText(), 
											   passwordField.getText(),
											   userTypeComboBox.getSelectionModel().getSelectedItem());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		instance.loggedInView(user);
	}
	
	private BooleanBinding buildBinding(BooleanBinding bind) {
		
		bind = nameField.textProperty().isEmpty().	  		
				or(surnameField.textProperty().isEmpty()).
				or(emailField.textProperty().isEmpty()).
				or(fiscalCodeField.textProperty().isEmpty()).
				or(passwordField.textProperty().isEmpty());
		
		return bind;
	}
}

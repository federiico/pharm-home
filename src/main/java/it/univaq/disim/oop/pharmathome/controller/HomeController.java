package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.domain.Utente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable, DataInitializable<Utente>{
	
	@FXML
	private Label userLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void initializeData(Utente utente) { //scrive nome e cognome dell'utente nella home
		
		String nominativo = utente.getNome() + " " + utente.getCognome();
		userLabel.setText(nominativo); 
	}

}

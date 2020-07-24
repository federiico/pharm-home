package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Amministratore;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AggiungiFarmacoController implements Initializable, DataInitializable<Amministratore>{
	
	@FXML
	private TextField codiceField;
	
	@FXML
	private TextField nomeField;
	
	@FXML
	private TextField produttoreField;
	
	@FXML
	private TextField prezzoField;
	
	@FXML
	private TextField disponibilitaField;
	
	@FXML
	private TextField quantitaMinField;
	
	@FXML
	private Button confermaButton;
	
	private Amministratore admin;
	
	private ViewDispatcher instance;
	
	private FarmacoServices farmacoServices;
	
	public AggiungiFarmacoController() {
		BusinessFactory factory = BusinessFactory.getInstance();
		farmacoServices = factory.getFarmacoServices();
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		confermaButton.disableProperty().bind(codiceField.textProperty().isEmpty().
											  or(nomeField.textProperty().isEmpty()).
											  or(produttoreField.textProperty().isEmpty()).
											  or(prezzoField.textProperty().isEmpty()).
											  or(disponibilitaField.textProperty().isEmpty()).
											  or(quantitaMinField.textProperty().isEmpty()));	
	}
	
	@Override
	public void initializeData(Amministratore admin) {
		this.admin = admin;
	}
	
	@FXML
	private void goBackToFarmaciView(ActionEvent e) {
		instance.renderView("farmaci", admin);
	}
	
	@FXML
	private void inserisciFarmaco(ActionEvent e) throws BusinessException{
		
		String codice, nome, produttore;
		double prezzo;
		int disponibilita, quantitaMin;
	
		codice = codiceField.getText();
		nome = nomeField.getText();
		produttore = produttoreField.getText();
		prezzo = Double.parseDouble(prezzoField.getText());
		disponibilita = Integer.parseInt(disponibilitaField.getText());
		quantitaMin = Integer.parseInt(quantitaMinField.getText());
		
		Farmaco farmaco = new Farmaco();
		farmaco.setCodiceMinisteriale(codice);
		farmaco.setNome(nome);
		farmaco.setCasaFarmaceutica(produttore);
		farmaco.setPrezzo(prezzo);
		farmaco.setDisponibilita(disponibilita);
		farmaco.setQuantitaMinima(quantitaMin);
		
		farmacoServices.aggiungiFarmaco(farmaco);
		instance.renderView("farmaci", admin);
	}
	

}

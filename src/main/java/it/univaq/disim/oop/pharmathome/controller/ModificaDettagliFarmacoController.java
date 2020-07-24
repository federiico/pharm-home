package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModificaDettagliFarmacoController implements Initializable, DataInitializable<Farmaco>{
	
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
	private Button applicaButton;
	
	private ViewDispatcher instance;
	
	private Farmaco farmaco;
	
	private FarmacoServices farmacoServices;
	
	
	public ModificaDettagliFarmacoController() {
		
		BusinessFactory factory = BusinessFactory.getInstance();
		farmacoServices = factory.getFarmacoServices();
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		applicaButton.disableProperty().bind(codiceField.textProperty().isEmpty().
				  or(nomeField.textProperty().isEmpty()).
				  or(produttoreField.textProperty().isEmpty()).
				  or(prezzoField.textProperty().isEmpty()).
				  or(disponibilitaField.textProperty().isEmpty()).
				  or(quantitaMinField.textProperty().isEmpty()));	
	}
	
	public void initializeData(Farmaco farmaco) {
		this.farmaco = farmaco;
		codiceField.setText(farmaco.getCodiceMinisteriale());
		nomeField.setText(farmaco.getNome());
		produttoreField.setText(farmaco.getCasaFarmaceutica());
		prezzoField.setText(farmaco.getPrezzo().toString());
		disponibilitaField.setText(String.valueOf(farmaco.getDisponibilita()));
		quantitaMinField.setText(String.valueOf(farmaco.getQuantitaMinima()));
	}
	
	@FXML
	private void applicaModifiche(ActionEvent e) throws BusinessException {
		
		String codice, nome, produttore;
		double prezzo;
		int disponibilita, quantitaMin;
		codice = codiceField.getText();
		nome = nomeField.getText();
		produttore = produttoreField.getText();
		prezzo = Double.parseDouble(prezzoField.getText());
		disponibilita = Integer.parseInt(disponibilitaField.getText());
		quantitaMin = Integer.parseInt(quantitaMinField.getText());
		
		farmacoServices.modificaFarmaco(farmaco,codice,nome,produttore,prezzo,disponibilita,quantitaMin);
		instance.renderView("farmaci", null);
	}
	
	@FXML
	private void goBackToFarmaciView(ActionEvent e) {
		instance.renderView("farmaci", null);
	}

}

package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.CancellaFarmacoException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DettagliFarmacoAdminController implements Initializable, DataInitializable<Farmaco>{
	
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
	private Label errorLabel;
	
	private ViewDispatcher instance;
	
	private Farmaco farmaco;
	
	private FarmacoServices farmaciServices;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		BusinessFactory factory = BusinessFactory.getInstance();
		farmaciServices = factory.getFarmacoServices();
		instance = ViewDispatcher.getInstance();
	}
	
	public void initializeData(Farmaco farmaco) {
		
		this.farmaco = farmaco;
		codiceField.setText(farmaco.getCodiceMinisteriale());
		nomeField.setText(farmaco.getNome());
		produttoreField.setText(farmaco.getCasaFarmaceutica());
		prezzoField.setText(farmaco.getPrezzo().toString() + " €");
		disponibilitaField.setText(String.valueOf(farmaco.getDisponibilita()));
		quantitaMinField.setText(String.valueOf(farmaco.getQuantitaMinima()));
	}
	
	@FXML
	private void modificaDettagli(ActionEvent e) {
		instance.renderView("modificaDettagliFarmaco", farmaco);
	}
	
	@FXML
	private void cancellaFarmaco(ActionEvent e) throws BusinessException {
		
		try {
			farmaciServices.cancellaFarmaco(farmaco);
			instance.renderView("farmaci", null);
		}
		catch(CancellaFarmacoException ex) {
			errorLabel.setText("Errore: il farmaco è presente in una o più prescrizioni");
		}
		
	}
	
	@FXML
	private void goBackToFarmaciView(ActionEvent e) {
		instance.renderView("farmaci", null);
	}
	

}

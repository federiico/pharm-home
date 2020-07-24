package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CercaFarmacoController implements Initializable, DataInitializable<Farmaco>{
	
	@FXML
	private TextField nomeField;
	
	@FXML
	private Button cercaButton;
	
	@FXML
	private Label warningLabel;
	
	private ViewDispatcher instance;
	
	private Farmaco farmacoDaCercare;
	
	private FarmacoServices farmacoServices;
	
	public CercaFarmacoController() {
		
		BusinessFactory factory = BusinessFactory.getInstance();
		farmacoServices = factory.getFarmacoServices();
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		warningLabel.setVisible(false);
		cercaButton.disableProperty().bind(nomeField.textProperty().isEmpty());
	}
	
	@FXML
	private void cercaFarmaco(ActionEvent e) {
		
			try {
				farmacoDaCercare = farmacoServices.cercaFarmaco(nomeField.getText());
				instance.renderView("dettagliFarmacoPaziente", farmacoDaCercare);
			}
			catch(FarmacoNotFoundException ex) {
				warningLabel.setVisible(true);
				nomeField.clear();
			}
			catch(BusinessException ex) {
				instance.renderError(ex);
			}
	}

}

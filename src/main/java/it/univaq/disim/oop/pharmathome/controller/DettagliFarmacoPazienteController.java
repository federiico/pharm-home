package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DettagliFarmacoPazienteController implements Initializable, DataInitializable<Farmaco>{
	
	@FXML
	private TextField codiceField;
	
	@FXML
	private TextField nomeField;
	
	@FXML
	private TextField produttoreField;
	
	@FXML
	private TextField prezzoField;
	
	private ViewDispatcher instance;
	
	
	public DettagliFarmacoPazienteController() {
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@Override
	public void initializeData(Farmaco farmaco) {

			codiceField.setText(farmaco.getCodiceMinisteriale());
			nomeField.setText(farmaco.getNome());
			produttoreField.setText(farmaco.getCasaFarmaceutica());
			prezzoField.setText(farmaco.getPrezzo().toString() + " €");
		
	}
	
	@FXML
	public void goBackToCerca(ActionEvent e) {
		instance.renderView("cercaFarmaco", null);
	}

}

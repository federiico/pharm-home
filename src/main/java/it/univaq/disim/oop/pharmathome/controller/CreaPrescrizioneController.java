package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreaPrescrizioneController implements Initializable, DataInitializable<Medico>{
	
	@FXML
	private TextField nomeField;
	
	@FXML
	private TextField cognomeField;
	
	@FXML
	private TextArea farmaciArea;
	
	@FXML
	private TextField dataField;
	
	@FXML
	private Button confermaButton;
	
	@FXML
	private Label errorLabel;
	
	private Medico medico;
	
	private PrescrizioneServices prescrizioneServices;
	
	ViewDispatcher instance;
	
	
	public CreaPrescrizioneController() {
		
		BusinessFactory factory = BusinessFactory.getInstance();
		prescrizioneServices = factory.getPrescrizioneServices();
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		confermaButton.disableProperty().bind(nomeField.textProperty().isEmpty().
				  or(cognomeField.textProperty().isEmpty()).
				  or(farmaciArea.textProperty().isEmpty()));
		
		dataField.setText(LocalDate.now().toString());
	}
	
	
	@Override
	public void initializeData(Medico medico) {
		this.medico = medico;
	}
	
	@FXML
	private void creaPrescrizione(ActionEvent e) throws BusinessException {
		
		List<String> farmaci = new ArrayList<String>();
		
		for (String line : farmaciArea.getText().split("\n")) { 
			farmaci.add(line);
		}
		
		try {
			prescrizioneServices.creaPrescrizione(medico.getId(), nomeField.getText(), cognomeField.getText(), farmaci);
			instance.renderView("prescrizioniMedico", medico);
		}
		catch(UserNotFoundException ex) {
			errorLabel.setText("Errore: il paziente non è registrato");
		}
		catch(FarmacoNotFoundException ex) {
			errorLabel.setText("Errore: uno o più farmaci non sono registrati");
		}
		
	}
	
	@FXML
	private void goBackToPrescrizioni(ActionEvent e) {
		instance.renderView("prescrizioniMedico", medico);
	}
}

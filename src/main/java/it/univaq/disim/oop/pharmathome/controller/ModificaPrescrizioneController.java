package it.univaq.disim.oop.pharmathome.controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.domain.Prescrizione;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ModificaPrescrizioneController implements Initializable, DataInitializable<Prescrizione>{
	
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
	
	private Prescrizione prescrizioneAttuale;
	
	private PrescrizioneServices prescrizioneService;
	
	private ViewDispatcher instance;
	
	
	public ModificaPrescrizioneController() {
	
		BusinessFactory factory = BusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneServices();
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		      
		confermaButton.disableProperty().bind(nomeField.textProperty().isEmpty().
											  or(cognomeField.textProperty().isEmpty()).
											  or(farmaciArea.textProperty().isEmpty()));
	}
		
	@Override
	public void initializeData(Prescrizione prescrizione) {
		
		prescrizioneAttuale = prescrizione;
		nomeField.setText(prescrizione.getPaziente().getNome());
		cognomeField.setText(prescrizione.getPaziente().getCognome());
		String farmaci = "";
		for(Farmaco farmaco : prescrizione.getFarmaco())
			farmaci = farmaci + farmaco.getNome() + "\n";
		farmaciArea.setText(farmaci);
		dataField.setText(prescrizione.getData().toString());
	}
	
	@FXML
	private void modificaPrescrizione(ActionEvent e) throws BusinessException {
		
		List<String> farmaci = new ArrayList<String>();
		
		for (String line : farmaciArea.getText().split("\n")) { //legge riga per riga la TextArea con i nomi dei farmaci
			farmaci.add(line);
		}
		
		try {
			prescrizioneService.modificaPrescrizione(prescrizioneAttuale, nomeField.getText(), cognomeField.getText(), farmaci);
			instance.renderView("prescrizioniMedico", prescrizioneAttuale.getMedico());
		}
		catch(UserNotFoundException ex) {
			errorLabel.setText("Errore: il paziente non è registrato");
		}
		catch(FarmacoNotFoundException ex) {
			errorLabel.setText("Errore: uno o più farmaci non sono registrati");
		}
	
	}
	
	@FXML
	private void cancellaPrescrizione(ActionEvent e) throws BusinessException {
		
		prescrizioneService.cancellaPrescrizione(prescrizioneAttuale);
		instance.renderView("prescrizioniMedico", prescrizioneAttuale.getMedico());
	}
	
	@FXML
	private void goBackToPrescrizioni(ActionEvent e) {
		instance.renderView("prescrizioniMedico", prescrizioneAttuale.getMedico());
	}
	

}

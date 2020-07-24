package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Prescrizione;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PrescrizioniPazienteController implements Initializable, DataInitializable<Paziente>{
	
	@FXML
	private TableView<Prescrizione> prescrizioniTableView;
	
	@FXML
	private TableColumn<Prescrizione, String> numeroColumn;
	
	@FXML
	private TableColumn<Prescrizione,String> medicoColumn;
	
	@FXML
	private TableColumn<Prescrizione, String> farmaciColumn;
	
	@FXML
	private TableColumn<Prescrizione, String> dataColumn;
	
	@FXML
	private TextField medicoField;
	
	@FXML
	private TextField farmacoField;
	
	@FXML
	private DatePicker dataPicker;
	
	@FXML
	private Button cercaButton;
	
	private Paziente paziente;
	
	private ViewDispatcher instance;
	
	private List<Prescrizione> prescrizioni;
	
	private ObservableList<Prescrizione> prescrizioniData;
	
	private PrescrizioneServices prescrizioneServices;
	
	
	public PrescrizioniPazienteController() {
		
		BusinessFactory factory = BusinessFactory.getInstance();
		prescrizioneServices = factory.getPrescrizioneServices();
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { //costruisce la TableView e disabilita il bottone di ricerca delle prescrizioni
		
		cercaButton.disableProperty().bind(medicoField.textProperty().isEmpty().
										   and(farmacoField.textProperty().isEmpty().
										   and(dataPicker.getEditor().textProperty().isEmpty())));
		
		dataPicker.setValue(LocalDate.now());
		
		numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
		medicoColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prescrizione, String> param) {
						return new SimpleStringProperty(param.getValue().getMedico().getCognome() + " " + param.getValue().getMedico().getNome());
					}
				});
		farmaciColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prescrizione, String> param) {
						String farmaci = "";
						for(Farmaco f : param.getValue().getFarmaco()) {
							farmaci = farmaci + f.getNome() + "\n";
						}
						return new SimpleStringProperty(farmaci);
					}
				});
	    dataColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prescrizione, String> param) {
						String data =param.getValue().getData().toString();
						return new SimpleStringProperty(data);
					}
				});
	}
	
	@Override
	public void initializeData(Paziente paziente) { //riempie la TableView con i dati delle prescrizioni
		
		this.paziente = paziente;
		try {
			prescrizioni = prescrizioneServices.findPrescrizioniFromPaziente(paziente);

			prescrizioniData = FXCollections.observableArrayList(prescrizioni);
			
			prescrizioniTableView.setItems(prescrizioniData);
		
		} catch (BusinessException e) {
			instance.renderError(e);
		}
	}
	
	@FXML
	private void cercaPrescrizione(ActionEvent e) throws BusinessException { //modifica la TableView con le prescrizioni corrispondenti ai dati inseriti nei campi di ricerca
	
		prescrizioniData.removeAll(prescrizioni);
		prescrizioni = prescrizioneServices.cercaPrescrizioni(paziente, medicoField.getText(), farmacoField.getText(),dataPicker.getValue());
		prescrizioniData = FXCollections.observableArrayList(prescrizioni);
		prescrizioniTableView.setItems(prescrizioniData);
		
	}

}

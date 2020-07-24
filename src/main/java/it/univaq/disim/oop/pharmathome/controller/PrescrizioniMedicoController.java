package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Prescrizione;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PrescrizioniMedicoController implements Initializable, DataInitializable<Medico>{
	
	@FXML
	private TableView<Prescrizione> prescrizioniTableView;
	
	@FXML
	private TableColumn<Prescrizione, String> numeroColumn;
	
	@FXML
	private TableColumn<Prescrizione, String> pazienteColumn;
	
	@FXML
	private TableColumn<Prescrizione, String> farmaciColumn;
	
	@FXML
	private TableColumn<Prescrizione, String> dataColumn;
	
	@FXML
	private TableColumn<Prescrizione, Button> buttonColumn;
	
	Medico medico;
	
	private ViewDispatcher instance;
	
	private List<Prescrizione> prescrizioni;
	
	private ObservableList<Prescrizione> prescrizioniData;
	
	private PrescrizioneServices prescrizioneService;
	
	public PrescrizioniMedicoController() {
	
		BusinessFactory factory = BusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneServices();
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { //crea la TableView
		
		numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
		pazienteColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prescrizione, String> param) {
						return new SimpleStringProperty(param.getValue().getPaziente().getCognome() + " "+param.getValue().getPaziente().getNome());
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
						return new SimpleStringProperty(param.getValue().getData().toString());
					}
				});
		buttonColumn.setStyle("-fx-alignment: CENTER;");
		buttonColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prescrizione, Button> param) {
						
						final Button modificaButton = new Button("Modifica");
						if(param.getValue().getStato().equals("Evasa"))
							modificaButton.setDisable(true);
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								instance.renderView("modificaPrescrizione", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});
	}
	
	@Override
	public void initializeData(Medico medico) { //riempie la TableView con i dati delle prescrizioni
		
		try {
			
			this.medico = medico;
			
			prescrizioni = prescrizioneService.findPrescrizioniFromMedico(medico);

			prescrizioniData = FXCollections.observableArrayList(prescrizioni);
			
			prescrizioniTableView.setItems(prescrizioniData);
		
		} catch (BusinessException e) {
			instance.renderError(e);
		}
	}
	
	@FXML
	public void creaPrescrizione(ActionEvent e) {
		instance.renderView("creaPrescrizione", medico);
	}

}

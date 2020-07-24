package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Amministratore;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class FarmaciAdminController implements Initializable, DataInitializable<Amministratore>{

	@FXML
	private ComboBox<String> visualizzaComboBox; 
	
	@FXML
	private TableView<Farmaco> tableView;
	
	@FXML
	private TableColumn<Farmaco, String> codiceColumn;
	
	@FXML
	private TableColumn<Farmaco, String> nomeColumn;
	
	@FXML
	private TableColumn<Farmaco, String> produttoreColumn;
	
	@FXML
	private TableColumn<Farmaco, String> disponibilitaColumn;
	
	@FXML
	private TableColumn<Farmaco, Button> buttonColumn;
	
	private ViewDispatcher instance;
	
	private Amministratore admin;
	
	private List<Farmaco> inventario;
	
	private ObservableList<Farmaco> inventarioData;
	
	private FarmacoServices farmacoService;
	
	public FarmaciAdminController() {
		BusinessFactory factory = BusinessFactory.getInstance();
		farmacoService = factory.getFarmacoServices();
		instance = ViewDispatcher.getInstance();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { //crea la TableView
		// TODO Auto-generated method stub
		visualizzaComboBox.getItems().addAll("Tutti", "In esaurimento");
		visualizzaComboBox.getSelectionModel().selectFirst();
		
		codiceColumn.setCellValueFactory(new PropertyValueFactory<>("codiceMinisteriale"));
		nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		produttoreColumn.setCellValueFactory(new PropertyValueFactory<>("casaFarmaceutica"));
		disponibilitaColumn.setCellValueFactory(new PropertyValueFactory<>("disponibilita"));

		buttonColumn.setStyle("-fx-alignment: CENTER;");
		buttonColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Farmaco, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Farmaco, Button> param) {
						final Button dettagliButton = new Button("Dettagli");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								instance.renderView("dettagliFarmacoAdmin", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});
	}
	
	@Override
	public void initializeData(Amministratore admin) { //riempie la TableView con i dati dei farmaci
		try {
			
			this.admin = admin; 
			
			inventario = farmacoService.findAllFarmaci();
			
			inventarioData = FXCollections.observableArrayList(inventario);
		
			tableView.setItems(inventarioData);
		} catch (BusinessException e) {
			instance.renderError(e);
		}
	}

	
	@FXML
	private void aggiungiFarmaco(ActionEvent e) {
		instance.renderView("inserisciFarmaco", admin);
	}
	
	@FXML
	private void filtraFarmaci(ActionEvent e) { //mostra solo i farmaci in esaurimento
		
		String selectedType = visualizzaComboBox.getSelectionModel().getSelectedItem(); 
		
		if(selectedType.equals("In esaurimento")) {
			
			inventarioData.removeAll(inventario);
			for(Farmaco f : inventario)
				if(f.getDisponibilita() <= f.getQuantitaMinima())
					inventarioData.add(f);
			tableView.setItems(inventarioData); 
		}
		else {
			initializeData(admin);
		}
	}

}

package it.univaq.disim.oop.pharmathome.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.pharmathome.business.MenuElement;
import it.univaq.disim.oop.pharmathome.domain.Amministratore;
import it.univaq.disim.oop.pharmathome.domain.Farmacista;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Utente;
import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import it.univaq.disim.oop.pharmathome.view.ViewException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LayoutController implements Initializable, DataInitializable<Utente>{
	
	private static final MenuElement MENU_HOME = new MenuElement("Home","home");
	
	private static final MenuElement[] MENU_ADMIN =  { new MenuElement("Gestione farmaci","farmaci")};
	
	private static final MenuElement[] MENU_FARMACISTA = { new MenuElement("Gestione prescrizioni","prescrizioniFarmacista")};
	
	private static final MenuElement[] MENU_MEDICO = { new MenuElement("Gestione prescrizioni","prescrizioniMedico")};
	
	private static final MenuElement[] MENU_PAZIENTE = { new MenuElement("Le mie prescrizioni","prescrizioniPaziente"),
														 new MenuElement("Ricerca farmaci","cercaFarmaco")};

	@FXML
	private Label profileNameLabel;
	
	@FXML
	private VBox leftVBox;
	
	private ViewDispatcher instance;
	
	private Utente user;

	public LayoutController() {
		instance = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	@Override
	public void initializeData(Utente user) { //costruisce la VBox laterale e la label in alto a sinistra con nome e cognome dell'utente
		
		this.user = user;
		
		leftVBox.getChildren().addAll(createButton(MENU_HOME));
		leftVBox.getChildren().add(new Separator());

		if (user instanceof Amministratore) {
			for (MenuElement menu : MENU_ADMIN) {
				leftVBox.getChildren().add(createButton(menu));
			}
		}
		if (user instanceof Farmacista) {
			for (MenuElement menu : MENU_FARMACISTA) {
				leftVBox.getChildren().add(createButton(menu));
			}
		}
		if (user instanceof Medico) {
			for (MenuElement menu : MENU_MEDICO) {
				leftVBox.getChildren().add(createButton(menu));
			}
		}
		if (user instanceof Paziente) {
			for (MenuElement menu : MENU_PAZIENTE) {
				leftVBox.getChildren().add(createButton(menu));
			}
		}
		
		profileNameLabel.setText(user.getNome() + " " + user.getCognome());
	}
	
	private Button createButton(MenuElement viewItem) { //rende clickabili le voci dentro la VBox
		
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
		button.setTextFill(Paint.valueOf("white"));
		button.setPrefHeight(10);
		button.setPrefWidth(180);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				instance.renderView(viewItem.getView(), user);
			}
		});
		return button;
	}
	
	@FXML
	private void logout(ActionEvent e) { //ritorna alla finestra di login
		try {
			instance.loginView(instance.getStage());
		} catch (ViewException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	
	
}

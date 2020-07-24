package it.univaq.disim.oop.pharmathome.view;

import java.io.IOException;
import it.univaq.disim.oop.pharmathome.controller.DataInitializable;
import it.univaq.disim.oop.pharmathome.domain.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewDispatcher {
		
	private static final String FXML_SUFFIX = ".fxml";

	private static final String RESOURCE = "/viste/";

	private static ViewDispatcher instance = new ViewDispatcher();
	
	private Stage stage;
	
	private BorderPane mainPane;
	
	private ViewDispatcher() {
	
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public static ViewDispatcher getInstance() {
		return instance;
	}
	
	public void loginView(Stage stage) throws ViewException{ 
		
		this.stage = stage;
		Parent login = loadView("login").getView();
		Scene scene = new Scene(login);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public void registrationView() throws ViewException{ 
		
		Parent registration = loadView("register").getView();
		Scene scene = new Scene(registration);
		stage.setScene(scene);
		stage.show();
	
	}
	
	public void loggedInView(Utente user) throws ViewException{
		
		View<Utente> layoutView = loadView("layout");
		DataInitializable<Utente> layoutControllerInitializer = layoutView.getController();
		layoutControllerInitializer.initializeData(user);
		mainPane = (BorderPane) layoutView.getView();
		renderView("home", user);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource(RESOURCE + "style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	
	}
	
	public <T> void renderView(String viewName, T data) { //modifica il pannello centrale della layoutView
		
		try {
			View<T> view = loadView(viewName);
			DataInitializable<T> controller = view.getController();
			controller.initializeData(data);
			mainPane.setCenter(view.getView());
		}
		catch(ViewException e) {
			renderError(e);
		}
	}
	
	
	public void renderError(Exception e) { //gestisce gli errori di renderView
		e.printStackTrace();
		System.exit(1);
	}
	
	
	private <T> View<T> loadView(String view) throws ViewException{  //carica l' fxml della vista e ritorna l'oggetto View
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE + view + FXML_SUFFIX));
			Parent parent = (Parent) loader.load();
			return new View<>(parent, loader.getController());
		}
		catch(IOException e) {
			e.printStackTrace();
			throw new ViewException(e);
		}
	}
	
}

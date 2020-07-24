package it.univaq.disim.oop.pharmathome;

import it.univaq.disim.oop.pharmathome.view.ViewDispatcher;
import it.univaq.disim.oop.pharmathome.view.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;

public class PharmAtHomeApplication extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		try {
				ViewDispatcher instance = ViewDispatcher.getInstance();
				instance.loginView(stage);
				stage.setResizable(false);
		}
		catch(ViewException e) {
			e.printStackTrace();
		}
	}
}
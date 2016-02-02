package fr.synthlab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


public class Main extends Application {
	private static final Logger logger = Logger.getLogger(Main.class);

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/fxml/MainWindow.fxml"));

		Scene scene = new Scene(root);
		stage.setTitle("Synthlab");
		stage.setScene(scene);
		stage.setOnShown(we -> logger.info("Main window opened."));
		stage.setOnCloseRequest(we -> {
				stage.close();
				logger.info("Main window closed.");
		});
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

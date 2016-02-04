package fr.synthlab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {
	private static final Logger APP_ROOT_LOGGER = Logger.getLogger("fr.synthlab");
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {

		// Reading logging.properties
		if (System.getProperty("java.util.logging.config.file") == null) {
			final InputStream inputStream = Main.class.getResourceAsStream("/logging.properties");
			try {
				LogManager.getLogManager().readConfiguration(inputStream);
			} catch (final IOException e) {
				Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
				Logger.getAnonymousLogger().severe(e.getMessage());
			}
		}


		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/fxml/MainWindow.fxml"));

		Scene scene = new Scene(root);
		// Mouse Listener for click event

		stage.setTitle("Synthlab");
		stage.setScene(scene);
		stage.setOnShown(we -> logger.fine("Main window opened."));
		stage.setOnCloseRequest(we -> {
				stage.close();
			logger.fine("Main window closed.");
		});
		stage.show();
		stage.setOnCloseRequest(t -> {
			Platform.exit();
			System.exit(0);
		});
	}

}

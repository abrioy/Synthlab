package fr.synthlab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * Created by miow on 2/1/16.
 */
public class Main extends Application {
	private static final Logger logger = Logger.getLogger(Main.class);

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/MainWindow.fxml"));

		Scene scene = new Scene(root);

		stage.setTitle("Sythlab");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

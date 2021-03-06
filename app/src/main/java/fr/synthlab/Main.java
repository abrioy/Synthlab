package fr.synthlab;

import fr.synthlab.view.controller.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * main class. Start program.
 * @see Application
 */
public class Main extends Application {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * start application.
     * @param args argument given on start
     */
    public static void main(final String[] args) {
        // Reading logging.properties
        if (System.getProperty("java.util.logging.config.file") == null) {
            final InputStream inputStream = Main.class
                    .getResourceAsStream("/logging.properties");
            try {
                LogManager.getLogManager().readConfiguration(inputStream);
            } catch (final IOException e) {
                Logger.getAnonymousLogger().severe(
                        "Could not load default logging.properties file");
                Logger.getAnonymousLogger().severe(e.getMessage());
            }
        }

        System.setProperty("javafx.embed.singleThread", "true");

        launch(args);
    }

    @Override
    public final void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/gui/fxml/MainWindow.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());


        stage.setTitle("Synthlab");
        stage.setScene(scene);
        stage.setOnShown(we -> LOGGER.fine("Main window opened."));
        stage.setOnCloseRequest(we -> {
            stage.close();
            LOGGER.fine("Main window closed.");
        });
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        MainWindowController controller = loader.getController();
        controller.setStageAndSetupListeners(stage);
        stage.setMaximized(true);
        stage.show();
    }
}

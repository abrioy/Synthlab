package fr.synthlab.view.module;

import fr.synthlab.model.module.Module;
import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.component.Plug;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * view module abstract class.
 */
public abstract class ViewModule extends Pane implements Serializable {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ViewModule.class.getName());

    /**
     * workbench.
     */
    private Workbench workbench;
    /**
     * model module.
     */
    private Module module;
    /**
     * panel.
     */
    private AnchorPane topPane;
    /**
     * module name.
     */
    private Label moduleName;
    /**
     * close button.
     */
    private Button closeButton;

    /**
     * constructor.
     * @param workbenchInit current workbench
     */
    public ViewModule(final Workbench workbenchInit) {
        super();

        workbench = workbenchInit;
        this.getStyleClass().add("module-frame");
        this.getStylesheets().add("/gui/fxml/style/Module.css");
        this.applyCss();

        topPane = new AnchorPane();
        topPane.setFocusTraversable(false);
        topPane.setMaxHeight(30.0d);
        topPane.relocate(10, 5);
        this.layoutBoundsProperty().addListener(
                (observable, oldValue, newValue) -> {
            // Making sure the pane is always the right size
            topPane.setPrefWidth(oldValue.getWidth() - 20.0d);
        });
        this.getChildren().add(topPane);

        moduleName = new Label();
        moduleName.setFocusTraversable(false);
        moduleName.setFont(new Font("Arial", 20));
        topPane.getChildren().add(moduleName);
        AnchorPane.setLeftAnchor(moduleName, 0.0d);

        closeButton = new Button();
        closeButton.setFocusTraversable(false);
        closeButton.getStyleClass().add("close-button");
        closeButton.setPrefSize(5, 5);
        topPane.getChildren().add(closeButton);
        AnchorPane.setRightAnchor(closeButton, 0.0d);
        closeButton.setOnMouseClicked(event -> {
            LOGGER.fine("Module \"" + module.getType()
                    + "\" is asking to be closed.");
            workbench.onModuleCloseRequest(this);
            event.consume();
        });
    }

    /**
     * loader for FXML.
     * @param fxmlPath fxml to load
     */
    protected final void loadFXML(final String fxmlPath) {
        FXMLLoader fxmlLoader
                = new FXMLLoader(getClass().getResource(fxmlPath));
        fxmlLoader.setController(this);

        try {
            Parent root = fxmlLoader.load();
            this.getChildren().add(root);
        } catch (IOException exception) {
            LOGGER.severe("Cannot load the specified FXML file: \""
                    + fxmlPath + "\".");
            throw new RuntimeException(exception);
        }

        getPlugs().forEach(child -> {
            child.setWorkbench(workbench);
            child.setGetPortCommand(() ->
                    module.getPort(child.nameProperty().getValue()));
        });
        topPane.toFront();
    }

    /**
     * @return model module
     */
    public final Module getModule() {
        return module;
    }

    /**
     * setter on model module.
     * @param newModule to set
     */
    public final void setModule(final Module newModule) {
        module = newModule;
        moduleName.setText(module.getType().getLongName());
    }

    /**
     * @return collection of all plug
     */
    public final Collection<Plug> getPlugs() {
        return this.lookupAll("Plug").stream()
                .filter(child -> child instanceof Plug)
                .map(child -> (Plug) child)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * find a plug by its name.
     * @param name of plug
     * @return plug found
     */
    public final Plug getPlugByName(final String name) {
        Collection<Plug> plugs = getPlugs();
        for (Plug plug : plugs) {
            if (plug.getName().equals(name)) {
                return plug;
            }
        }
        return null;
    }

    /**
     * write in o for save workbench.
     * @param o where is save
     * @throws IOException if save can't open
     */
    abstract void writeObject(ObjectOutputStream o)
            throws IOException;

    /**
     * reload object.
     * @param o where is reload
     * @throws IOException if save can't open
     * @throws ClassNotFoundException if a save class can't be found
     */
    public abstract void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException;
}

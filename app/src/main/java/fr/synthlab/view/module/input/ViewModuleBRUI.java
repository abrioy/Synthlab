package fr.synthlab.view.module.input;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleBRUI extends ViewModule implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(ViewModuleBRUI.class.getName());


    public ViewModuleBRUI(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleWhiteNoise.fxml");
        this.setId("pane");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    }

    @Override
    public void writeObject(final ObjectOutputStream o) throws IOException {
    }

    @Override
    public void readObject(final ObjectInputStream o) throws IOException, ClassNotFoundException {
    }
}

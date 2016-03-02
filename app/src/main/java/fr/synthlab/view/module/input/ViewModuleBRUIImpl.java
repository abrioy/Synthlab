package fr.synthlab.view.module.input;

import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.module.ViewModule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleBRUIImpl extends ViewModule implements ViewModuleBRUI {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleBRUIImpl.class.getName());


    public ViewModuleBRUIImpl(final Workbench workbench) {
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
    public void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
    }
}

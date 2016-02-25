package fr.synthlab.view.module.filter;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleVCA extends ViewModule implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(ViewModuleVCA.class.getName());

    @FXML
    private Plug in;
    @FXML
    private Plug out;
    @FXML
    private Plug am;

    @FXML
    private Knob ampli;

    private Runnable changeAmpliCommand;

    public ViewModuleVCA(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleVCA.fxml");
        this.setId("pane");
    }

    @Override
    public final void initialize(final URL url, final ResourceBundle resourceBundle) {
        ampli.valueProperty().addListener(event -> {
            updateAmply();
        });
    }

    private void updateAmply() {
        changeAmpliCommand.run();
    }

    public final void setChangeAmpliCommand(final Runnable newChangeAmpliCommand) {
        changeAmpliCommand = newChangeAmpliCommand;
        changeAmpliCommand.run();
    }

    public final double getAmpli() {
        return ampli.getValue();
    }

    @Override
    public final void writeObject(final ObjectOutputStream o) throws IOException {
        o.writeDouble(ampli.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o) throws IOException, ClassNotFoundException {
        ampli.setValue(o.readDouble());
    }
}

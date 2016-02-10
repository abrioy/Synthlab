package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleVCA extends ViewModule implements Initializable {
	private static final Logger logger = Logger.getLogger(ViewModuleVCA.class.getName());

	@FXML
	private Plug in;
	@FXML
	private Plug out;
	@FXML
    private Plug am;

    @FXML
    private Knob ampli;

    private Runnable changeAmpliCommand;

    public ViewModuleVCA(Workbench workbench) {
		super(workbench);
		this.loadFXML("/gui/fxml/module/ViewModuleVCA.fxml");
		this.setId("pane");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
        ampli.valueProperty().addListener(event -> {
            updateAmply();
        });
    }

    private void updateAmply() {
        changeAmpliCommand.run();
    }

    public void setChangeAmpliCommand(Runnable changeAmpliCommand) {
        this.changeAmpliCommand = changeAmpliCommand;
        changeAmpliCommand.run();
    }

    public double getAmpli() {
        return ampli.getValue();
    }


	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {
		o.writeDouble(ampli.getValue());
	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		ampli.setValue(o.readDouble());
	}
}

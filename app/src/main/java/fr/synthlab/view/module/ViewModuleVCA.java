package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

    private Command changeAmpliCommand;

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
        changeAmpliCommand.execute();
    }

    public void setChangeAmpliCommand(Command changeAmpliCommand) {
        this.changeAmpliCommand = changeAmpliCommand;
    }

    public double getAmpli() {
        return ampli.getValue();
    }
}

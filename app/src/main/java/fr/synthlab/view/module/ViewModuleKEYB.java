package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleKEYB extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleKEYB.class.getName());

    public ViewModuleKEYB(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleKEYB.fxml");
        this.setId("pane");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {

	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {

	}
}
